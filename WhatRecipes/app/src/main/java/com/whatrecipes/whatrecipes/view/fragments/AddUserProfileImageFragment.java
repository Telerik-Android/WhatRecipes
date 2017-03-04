package com.whatrecipes.whatrecipes.view.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.whatrecipes.whatrecipes.App;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.presenters.AddUserProfileImagePresenter;
import com.whatrecipes.whatrecipes.utils.ActivityUtils;
import com.whatrecipes.whatrecipes.utils.CameraUtils;
import com.whatrecipes.whatrecipes.utils.ImageHelper;
import com.whatrecipes.whatrecipes.view.IPhoto;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.whatrecipes.whatrecipes.utils.CameraUtils.REQUEST_MEDIA_USER;
import static com.whatrecipes.whatrecipes.utils.CameraUtils.REQUEST_PORTRAIT_USER;

/**
 * Created by fatal on 23.2.2017 г..
 */

public class AddUserProfileImageFragment extends Fragment implements IView.AddUserProfileImage, IPhoto {

    @BindView(R.id.imageViewProfile)
    ImageView imageView;

    @BindView(R.id.camera_button)
    Button btnCamera;

    @BindView(R.id.gallery_button)
    Button btnGallery;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Inject
    AddUserProfileImagePresenter presenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_user_profile_image, container, false);

        App.get().component().inject(this);
        presenter.setView(this);

        ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void finishActivity(int result) {
        getActivity().setResult(result);
        getActivity().finish();
    }

    @OnClick(R.id.camera_button)
    @Override
    public void startTakeAPhotoActivity() {
        btnGallery.setEnabled(false);
        CameraUtils.takeUserProfileCameraPhoto(this);
    }

    @Override
    @OnClick(R.id.gallery_button)
    public void startTakeFromGalleryPhotoActivity() {
        btnCamera.setEnabled(false);
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, REQUEST_MEDIA_USER);
    }

    @Override
    @OnClick(R.id.cancel_button)
    public void handleCancelButtonClick() {
        getActivity().finish();
    }


    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showOnSuccessfulUploadToast() {

    }

    @Override
    public void showFailedUploadToast() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 0) {
            switch (requestCode) {
                case REQUEST_PORTRAIT_USER:
                    if (resultCode == RESULT_OK) {
                        Uri rootPath = data.getData();
                        Bitmap bitmap = BitmapFactory.decodeFile(rootPath.getPath());

                        //Preview photo
                        imageView.setImageBitmap(bitmap);
                        presenter.uploadImageToStorage(getActivity(), ImageHelper.getImageByteArray(bitmap));

                    }
                    break;

                case REQUEST_MEDIA_USER:
                    if (resultCode == RESULT_OK) {
                        Uri rootPath2 = data.getData();
                        Bitmap bitmap2 = null;
                        try {
                            bitmap2 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), rootPath2);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        imageView.setImageBitmap(bitmap2);
                        presenter.uploadImageToStorage(getActivity(), ImageHelper.getImageByteArray(bitmap2));
                    }
                    break;
            }
        }

    }

    public void pickImageFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);
    }
}
