package com.whatrecipes.whatrecipes.view.ai;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsware.cwac.cam2.CameraActivity;
import com.commonsware.cwac.cam2.Facing;
import com.commonsware.cwac.cam2.ZoomStyle;
import com.google.gson.Gson;
import com.whatrecipes.whatrecipes.App;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.data.ai.Classifier;
import com.whatrecipes.whatrecipes.data.ai.TensorFlowImageClassifier;
import com.whatrecipes.whatrecipes.data.Recipe;
import com.whatrecipes.whatrecipes.presenters.ImageClassifierPresenter;
import com.whatrecipes.whatrecipes.view.ActivityRecipeDetails;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.whatrecipes.whatrecipes.utils.CameraUtils.REQUEST_MEDIA_USER;

public class ImageClassifierFragment extends Fragment implements IView.ImageClassifierView {
    private static final int PERMISSIONS_REQUEST = 1;
    private static final int NUM_CLASSES = 1008;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128;
    private static final String INPUT_NAME = "Mul";
    private static final String OUTPUT_NAME = "final_result";
    public static final int INPUT_SIZE = 299;
    private static final String MODEL_FILE =
            "file:///android_asset/banitsa.pb";
    private static final String LABEL_FILE =
            "file:///android_asset/banitsa.txt";

    private TensorFlowImageClassifier mTensorFlowClassifier;
    private AtomicBoolean mReady = new AtomicBoolean(false);

    @BindView(R.id.classifier_image_view)
    public ImageView mImage;

    @BindView(R.id.result)
    public TextView result;

    @BindView(R.id.recipe_result)
    public TextView classificationResultText;

    @BindView(R.id.take_gallery_picture)
    public Button galleryButton;

    @BindView(R.id.take_camera_picture)
    public Button cameraButton;


    @Inject
    ImageClassifierPresenter presenter;

    public ImageClassifierFragment() {
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image_classifier, container, false);

        App.get().component().inject(this);

        this.presenter.setView(this);

        ButterKnife.bind(this, view);

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (hasPermission()) {
            if (savedInstanceState == null) {
                this.init();
            }
        } else {
            requestPermission();
        }

        return view;
    }

    private void setReady(boolean ready) {
        mReady.set(ready);
    }

    private void init() {
        try {
            if (this.mTensorFlowClassifier == null) {
                this.mTensorFlowClassifier = (TensorFlowImageClassifier) TensorFlowImageClassifier.create(
                        getActivity().getAssets(),
                        MODEL_FILE,
                        LABEL_FILE,
                        INPUT_SIZE,
                        IMAGE_MEAN,
                        IMAGE_STD,
                        INPUT_NAME,
                        OUTPUT_NAME
                );
            }

        } catch (IOException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        setReady(true);
    }

    private void classifyImage(Bitmap bmp) {
        Bitmap rescaled = Bitmap.createScaledBitmap(
                bmp,
                INPUT_SIZE,
                INPUT_SIZE,
                true);
        mImage.setImageBitmap(rescaled);

        final List<Classifier.Recognition> results = mTensorFlowClassifier.recognizeImage(rescaled);
        if (!results.isEmpty()) {
            this.result.setText(results.get(0).toString());
        }

        setReady(true);
        this.presenter.getRecipeWithName(results.get(0).getTitle());
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
                InputStream inputStream = getContext().getContentResolver().openInputStream(data.getData());
                Bitmap bmp = BitmapFactory.decodeStream(inputStream);
                this.classifyImage(bmp);

            } catch (FileNotFoundException e) {
                // no such file
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        init();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.setReady(false);
        try {
            if (mTensorFlowClassifier != null) {
                mTensorFlowClassifier.close();
            }
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
        return getActivity().checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED
                && getActivity().checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(CAMERA) ||
                    shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(
                        getContext(),
                        "Camera AND storage permission are required!",
                        Toast.LENGTH_LONG).show();
            }
            requestPermissions(new String[]{CAMERA, WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST);
        }
    }

    @Override
    public void setClassificationName(String name) {
        classificationResultText.setText(name);
    }

    @Override
    public void openRecipeDetails(Recipe recipe) {
        Intent intent = new Intent(this.getContext(), ActivityRecipeDetails.class);
        Gson gson = new Gson();
        String recipeJson = gson.toJson(recipe);
        intent.putExtra("Recipe", recipeJson);
        this.getContext().startActivity(intent);
    }

    @OnClick(R.id.take_camera_picture)
    public void onClickCameraButton() {
        if (mReady.get()) {
            setReady(false);
            String filename = "cam2_" + Build.MANUFACTURER + "_" + Build.PRODUCT
                    + "_" + new SimpleDateFormat("yyyyMMdd'-'HHmmss", Locale.ENGLISH).format(new Date());
            File testRoot = new File(getActivity().getExternalFilesDir(null), filename);

            Intent i = new CameraActivity.IntentBuilder(getContext())
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
            Toast.makeText(getContext(), "Wait a bit!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.take_gallery_picture)
    public void onClickGalleryButton() {
        if (mReady.get()) {
            setReady(false);
            Intent pickPhoto = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto, REQUEST_MEDIA_USER);
        } else {
            Toast.makeText(getContext(), "Wait a bit!", Toast.LENGTH_SHORT).show();
        }
    }
}
