package com.whatrecipes.whatrecipes.view.AI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsware.cwac.cam2.CameraActivity;
import com.commonsware.cwac.cam2.Facing;
import com.commonsware.cwac.cam2.ZoomStyle;
import com.whatrecipes.whatrecipes.AI.Classification.Classifier;
import com.whatrecipes.whatrecipes.AI.Classification.TensorFlowImageClassifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.whatrecipes.whatrecipes.utils.CameraUtils.REQUEST_MEDIA_USER;

import com.whatrecipes.whatrecipes.App;
import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.presenters.ImageClassifierPresenter;
import com.whatrecipes.whatrecipes.presenters.RecipesStackPresenter;

import javax.inject.Inject;

public class ActivityImageClassifier extends AppCompatActivity implements IView.ImageClassifierView {
    private static final int PERMISSIONS_REQUEST = 1;
    private static final int NUM_CLASSES = 1008;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128;
    private static final String INPUT_NAME = "Mul";
    private static final String OUTPUT_NAME = "final_result";
    public static final int INPUT_SIZE = 299;
    private static final String MODEL_FILE =
            "file:///android_asset/tensorflow_inception_graph.pb";
    private static final String LABEL_FILE =
            "file:///android_asset/imagenet_comp_graph_label_strings.txt";

    private TensorFlowImageClassifier mTensorFlowClassifier;

    private ImageView mImage;
    private TextView[] mResultViews;

    private Context ctx;

    private AtomicBoolean mReady = new AtomicBoolean(false);

    @Inject
    ImageClassifierPresenter presenter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ctx = getBaseContext();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_image_classifier);
        mImage = (ImageView) findViewById(R.id.imageView);
        mResultViews = new TextView[3];
        mResultViews[0] = (TextView) findViewById(R.id.result1);
        mResultViews[1] = (TextView) findViewById(R.id.result2);
        mResultViews[2] = (TextView) findViewById(R.id.result3);

        if (hasPermission()) {
            if (savedInstanceState == null) {
                init();
            }
        } else {
            requestPermission();
        }

        Button galleryButton = (Button)findViewById(R.id.take_gallery_picture);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mReady.get()) {
                    setReady(false);
                    Intent pickPhoto = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, REQUEST_MEDIA_USER);
                } else {
                    Toast.makeText(getBaseContext(), "Wait a bit!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button cameraButton = (Button)findViewById(R.id.take_camera_picture);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mReady.get()) {
                    setReady(false);
                    String filename = "cam2_" + Build.MANUFACTURER + "_" + Build.PRODUCT
                            + "_" + new SimpleDateFormat("yyyyMMdd'-'HHmmss", Locale.ENGLISH).format(new Date());
                    File testRoot = new File(getExternalFilesDir(null), filename);

                    Intent i = new CameraActivity.IntentBuilder(getBaseContext())
                            .requestPermissions()
                            .skipConfirm()
                            .facing(Facing.BACK)
                            .to(new File(testRoot, getString(R.string.screenshot)))
                            .debug()
                            .zoomStyle(ZoomStyle.SEEKBAR)
                            .updateMediaStore()
                            .build();
                    i.putExtra(getString(R.string.PhotoPath), testRoot.getAbsolutePath());

                    startActivityForResult(i, REQUEST_MEDIA_USER);
                } else {
                    Toast.makeText(getBaseContext(), "Wait a bit!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        App.get().component().inject(this);
        this.presenter.setView(this);
        this.presenter.getRecipeWithName("Chicken");
    }

    private void init() {
        try {
            this.mTensorFlowClassifier = (TensorFlowImageClassifier) TensorFlowImageClassifier.create(
                    getAssets(),
                    MODEL_FILE,
                    LABEL_FILE,
                    INPUT_SIZE,
                    IMAGE_MEAN,
                    IMAGE_STD,
                    INPUT_NAME,
                    OUTPUT_NAME
            );

        } catch (IOException e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        setReady(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MEDIA_USER && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }

            try {
                InputStream inputStream = ctx.getContentResolver().openInputStream(data.getData());
                Bitmap bmp = BitmapFactory.decodeStream(inputStream);
                this.classifyImage(bmp);

            } catch (FileNotFoundException e) {
                // no such file
            }
        }
    }

    private void setReady(boolean ready) {
        mReady.set(ready);
    }

    private void classifyImage(Bitmap bmp) {
        Bitmap rescaled = Bitmap.createScaledBitmap(
                bmp,
                INPUT_SIZE,
                INPUT_SIZE,
                true);
        mImage.setImageBitmap(rescaled);

        final List<Classifier.Recognition> results = mTensorFlowClassifier.recognizeImage(rescaled);

        setReady(true);

        for (int i = 0; i < mResultViews.length; i++) {
            if (results.size() > i) {
                Classifier.Recognition r = results.get(i);
                mResultViews[i].setText(r.toString());
            } else {
                mResultViews[i].setText(null);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (mTensorFlowClassifier != null) mTensorFlowClassifier.close();
        } catch (Throwable t) {
            // close quietly
        }
    }


    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String permissions[],
            @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    init();
                } else {
                    requestPermission();
                }
            }
        }
    }

    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        return checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(CAMERA) ||
                    shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Camera AND storage permission are required!",
                        Toast.LENGTH_LONG).show();
            }
            requestPermissions(new String[]{CAMERA, WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST);
        }
    }

    @Override
    public void setClassificationName(String name) {
        TextView classificationResultText = (TextView)findViewById(R.id.recipe_result);
        classificationResultText.setText(name);
    }
}
