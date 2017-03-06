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

import com.google.gson.Gson;
import com.whatrecipes.whatrecipes.App;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.data.Recipe;
import com.whatrecipes.whatrecipes.presenters.ImageClassifierPresenter;
import com.whatrecipes.whatrecipes.utils.CameraUtils;
import com.whatrecipes.whatrecipes.view.ActivityRecipeDetails;

import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.whatrecipes.whatrecipes.utils.CameraUtils.REQUEST_MEDIA_USER;

public class ImageClassifierFragment extends Fragment implements IView.ImageClassifierView {
    private static final int PERMISSIONS_REQUEST = 1;

    @BindView(R.id.classifier_image_view)
    public ImageView mImage;

    @BindView(R.id.result)
    public TextView result;

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
                this.presenter.initClassifier(getActivity().getAssets(), getContext());
            }
        } else {
            requestPermission();
        }

        return view;
    }

    @Override
    public void setClassifiedImageData(Bitmap bmp, String resultString) {
        mImage.setImageBitmap(bmp);
        this.result.setText(resultString);
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
                this.presenter.classifyImage(bmp);

            } catch (FileNotFoundException e) {
                // no such file
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        this.presenter.initClassifier(getActivity().getAssets(), getContext());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.presenter.unloadClassifier();
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
                    this.presenter.initClassifier(getActivity().getAssets(), getContext());
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
    public void openRecipeDetails(Recipe recipe) {
        Intent intent = new Intent(this.getContext(), ActivityRecipeDetails.class);
        Gson gson = new Gson();
        String recipeJson = gson.toJson(recipe);
        intent.putExtra("Recipe", recipeJson);
        this.getContext().startActivity(intent);
    }

    @OnClick(R.id.take_camera_picture)
    public void onClickCameraButton() {
        if (this.presenter.getReady()) {
            this.presenter.setReady(false);
            CameraUtils.takePhotoForAi(this);
        } else {
            Toast.makeText(getContext(), "Wait a bit!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.take_gallery_picture)
    public void onClickGalleryButton() {
        if (this.presenter.getReady()) {
            this.presenter.setReady(false);
            Intent pickPhoto = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto, REQUEST_MEDIA_USER);
        } else {
            Toast.makeText(getContext(), "Wait a bit!", Toast.LENGTH_SHORT).show();
        }
    }
}
