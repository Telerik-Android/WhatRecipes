package com.whatrecipes.whatrecipes.presenters;

import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.data.firebase.IFirebaseAuthenticationInteractor;
import com.whatrecipes.whatrecipes.data.firebase.IFirebaseStorageInteractor;
import com.whatrecipes.whatrecipes.data.firebase.listeners.ResponseListener;
import com.whatrecipes.whatrecipes.utils.Validator;

import javax.inject.Inject;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by fatal on 2/21/2017.
 */

public class RegisterUserPresenter implements IPresenter.RegisterUserPresenter, ResponseListener {
    private final IFirebaseAuthenticationInteractor firebaseAuth;
    private final IFirebaseStorageInteractor firebaseStore;
    private IView.RegisterUserView mView;

    @Inject
    public RegisterUserPresenter(IFirebaseAuthenticationInteractor firebaseAuth, IFirebaseStorageInteractor firebaseStore) {
        this.firebaseAuth = firebaseAuth;
        this.firebaseStore = firebaseStore;
    }

    @Override
    public void setView(IView.RegisterUserView view) {
        this.mView = view;
    }

    @Override
    public void registerUser(String email, String password) {
        if (!Validator.stringEmptyOrNull(email, password)) {
            mView.showProgressBar();
            firebaseAuth.registerUser(email, password,this);
            //set user profile image url
        } else {
            mView.showAllFieldsMustBeFilledMessage();
        }
    }

    @Override
    public void onSuccessListener() {
        mView.hideProgressBar();
        mView.showSuccessfulRegisterMessage();
        firebaseAuth.logTheUserOut();
        mView.finishActivity(RESULT_OK);
    }

    @Override
    public void onFailedListener() {
        mView.hideProgressBar();
        mView.showInvalidRegisterMessage();
        mView.finishActivity(RESULT_CANCELED);
    }
}
