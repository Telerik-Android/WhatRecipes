package com.whatrecipes.whatrecipes.presenters;

import android.app.Activity;

import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.data.firebase.IFirebaseAuthenticationInteractor;
import com.whatrecipes.whatrecipes.data.firebase.IFirebaseStorageInteractor;
import com.whatrecipes.whatrecipes.data.firebase.listeners.RequestListener;

import javax.inject.Inject;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by fatal on 23.2.2017 г..
 */

public class AddUserProfileImagePresenter implements IPresenter.AddUserProfileImagePresenter,RequestListener<String>{

    private final IFirebaseStorageInteractor firebaseStore;
    private final IFirebaseAuthenticationInteractor firebaseAuth;
    private IView.AddUserProfileImage mView;


    @Inject
    public AddUserProfileImagePresenter(IFirebaseAuthenticationInteractor firebaseAuth, IFirebaseStorageInteractor firebaseStore){
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
            firebaseStore.uploadUserProfileImageToStorage(activity,imageByteArray, this);
        }
    }

    @Override
    public void onSuccessfulRequest(String callback) {
        firebaseAuth.changeUserImageUrl(callback);
        mView.hideProgressBar();
        mView.showOnSuccessfulUploadToast();
        mView.finishActivity(RESULT_OK);
    }

    @Override
    public void onFailedRequest() {
        mView.hideProgressBar();
        mView.showFailedUploadToast();
        mView.finishActivity(RESULT_CANCELED);
    }

}
