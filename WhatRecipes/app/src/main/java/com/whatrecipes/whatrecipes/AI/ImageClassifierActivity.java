package com.whatrecipes.whatrecipes.AI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.whatrecipes.whatrecipes.AI.Classification.Classifier;
import com.whatrecipes.whatrecipes.AI.Classification.TensorFlowImageClassifier;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.whatrecipes.whatrecipes.utils.CameraUtils.REQUEST_MEDIA_USER;

import com.whatrecipes.whatrecipes.R;

public class ImageClassifierActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST = 1;

    private TensorFlowImageClassifier mTensorFlowClassifier;

    private ImageView mImage;
    private TextView[] mResultViews;

    private Context ctx;

    private AtomicBoolean mReady = new AtomicBoolean(false);

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

        Button cameraButton = (Button)findViewById(R.id.take_picture);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mReady.get()) {
                    setReady(false);
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, REQUEST_MEDIA_USER);
                } else {
                    Toast.makeText(getBaseContext(), "Wait a bit!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        mTensorFlowClassifier = new TensorFlowImageClassifier(ImageClassifierActivity.this);

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

    private void classifyImage(Bitmap image) {
        Bitmap rescaled = Bitmap.createScaledBitmap(
                image,
                TensorFlowImageClassifier.INPUT_SIZE,
                TensorFlowImageClassifier.INPUT_SIZE,
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
}
