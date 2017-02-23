package com.whatrecipes.whatrecipes.presenters;

import android.app.Activity;

import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.data.firebase.FirebaseAuthenticationInteractor;
import com.whatrecipes.whatrecipes.data.firebase.FirebaseStorageInteractor;
import com.whatrecipes.whatrecipes.data.firebase.RequestListener;

import javax.inject.Inject;

/**
 * Created by fatal on 23.2.2017 г..
 */

public class AddUserProfileImagePresenter implements IPresenter.AddUserProfileImagePresenter{

    private final FirebaseStorageInteractor firebaseStore;
    private final FirebaseAuthenticationInteractor firebaseAuth;
    private IView.AddUserProfileImage mView;

    public String tempUserImageUrl;

    @Inject
    public AddUserProfileImagePresenter(FirebaseAuthenticationInteractor firebaseAuth, FirebaseStorageInteractor firebaseStore){
        this.firebaseAuth = firebaseAuth;
        this.firebaseStore = firebaseStore;
    }


    @Override
    public void setView(IView.AddUserProfileImage view) {
        this.mView = view;
    }


    @Override
    public void uploadImageToStorage(Activity activity, byte[] imageByteArray) {
        if (imageByteArray != null) {
            mView.showProgressBar();
            firebaseStore.uploadImageToStorage(activity,imageByteArray, bindImageUploadResponseListener());
        }
    }

    protected RequestListener<String> bindImageUploadResponseListener() {
        return new RequestListener<String>() {

            @Override
            public void onSuccessfulRequest(String callback) {
                tempUserImageUrl=callback;
                saveImageToCurrentUser();
                mView.hideProgressBar();
                mView.showOnSuccessfulUploadToast();
            }

            @Override
            public void onFailedRequest() {
                mView.showProgressBar();
                mView.showFailedUploadToast();
            }
        };
    }

    public void saveImageToCurrentUser() {
        firebaseAuth.changeUserImageUrl(tempUserImageUrl);
    }
}
