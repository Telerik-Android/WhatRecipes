package com.whatrecipes.whatrecipes.data.ai;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Trace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Vector;

import org.tensorflow.Graph;
import org.tensorflow.Operation;
import org.tensorflow.Output;
import org.tensorflow.Shape;
import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

public class TensorFlowImageClassifier implements Classifier {
    // Only return this many results with at least this confidence.
    private static final int MAX_RESULTS = 3;
    private static final float THRESHOLD = 0.1f;

    // Config values.
    private String inputName;
    private String outputName;
    private int inputSize;
    private int imageMean;
    private float imageStd;

    // Pre-allocated buffers.
    private Vector<String> labels = new Vector<String>();
    private int[] intValues;
    private float[] floatValues;
    private float[] outputs;
    private String[] outputNames;

    private TensorFlowInferenceInterface inferenceInterface;

    public TensorFlowImageClassifier(AssetManager assetManager,
                                     String modelFilename,
                                     String labelFilename,
                                     int inputSize,
                                     int imageMean,
                                     float imageStd,
                                     String inputName,
                                     String outputName) throws IOException {
        this.inputName = inputName;
        this.outputName = outputName;

        // Read the label names into memory.
        // TODO(andrewharp): make this handle non-assets.
        String actualFilename = labelFilename.split("file:///android_asset/")[1];
        BufferedReader br = null;
        br = new BufferedReader(new InputStreamReader(assetManager.open(actualFilename)));
        String line;
        while ((line = br.readLine()) != null) {
            this.labels.add(line);
        }
        br.close();

        this.inferenceInterface = new TensorFlowInferenceInterface();
        if (this.inferenceInterface.initializeTensorFlow(assetManager, modelFilename) != 0) {
            throw new RuntimeException("TF initialization failed");
        }
        // The shape of the output is [N, NUM_CLASSES], where N is the batch size.
//        int numClasses =
//                (int) c.inferenceInterface.graph().operation(outputName).output(0).shape().size(1);
        Graph gr = this.inferenceInterface.graph();
        Operation opr = gr.operation(outputName);
        Output out = opr.output(0);
        Shape sh = out.shape();
        int numClasses = (int)sh.size(1);

        // Ideally, inputSize could have been retrieved from the shape of the input operation.  Alas,
        // the placeholder node for input in the graphdef typically used does not specify a shape, so it
        // must be passed in as a parameter.
        this.inputSize = inputSize;
        this.imageMean = imageMean;
        this.imageStd = imageStd;

        // Pre-allocate buffers.
        this.outputNames = new String[] {outputName};
        this.intValues = new int[inputSize * inputSize];
        this.floatValues = new float[inputSize * inputSize * 3];
        this.outputs = new float[numClasses];
    }

    @Override
    public List<Recognition> recognizeImage(Bitmap bitmap) {
        // Preprocess the image data from 0-255 int to normalized float based
        // on the provided parameters.
        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        for (int i = 0; i < intValues.length; ++i) {
            final int val = intValues[i];
            floatValues[i * 3 + 0] = (((val >> 16) & 0xFF) - imageMean) / imageStd;
            floatValues[i * 3 + 1] = (((val >> 8) & 0xFF) - imageMean) / imageStd;
            floatValues[i * 3 + 2] = ((val & 0xFF) - imageMean) / imageStd;
        }
        Trace.endSection();

        // Copy the input data into TensorFlow.
        Trace.beginSection("fillNodeFloat");
        inferenceInterface.fillNodeFloat(
                inputName, new int[] {1, inputSize, inputSize, 3}, floatValues);
        Trace.endSection();

        // Run the inference call.
        Trace.beginSection("runInference");
        inferenceInterface.runInference(outputNames);
        Trace.endSection();

        // Copy the output Tensor back into the output array.
        Trace.beginSection("readNodeFloat");
        inferenceInterface.readNodeFloat(outputName, outputs);
        Trace.endSection();

        // Find the best classifications.
        PriorityQueue<Recognition> pq =
                new PriorityQueue<Recognition>(
                        3,
                        new Comparator<Recognition>() {
                            @Override
                            public int compare(Recognition lhs, Recognition rhs) {
                                // Intentionally reversed to put high confidence at the head of the queue.
                                return Float.compare(rhs.getConfidence(), lhs.getConfidence());
                            }
                        });
        for (int i = 0; i < outputs.length; ++i) {
            if (outputs[i] > THRESHOLD) {
                pq.add(
                        new Recognition(
                                "" + i, labels.size() > i ? labels.get(i) : "unknown", outputs[i], null));
            }
        }
        final ArrayList<Recognition> recognitions = new ArrayList<Recognition>();
        int recognitionsSize = Math.min(pq.size(), MAX_RESULTS);
        for (int i = 0; i < recognitionsSize; ++i) {
            recognitions.add(pq.poll());
        }
        Trace.endSection(); // "recognizeImage"
        return recognitions;
    }

    @Override
    public void enableStatLogging(boolean debug) {
        inferenceInterface.enableStatLogging(debug);
    }

    @Override
    public String getStatString() {
        return inferenceInterface.getStatString();
    }

    @Override
    public void close() {
        inferenceInterface.close();
    }
}
