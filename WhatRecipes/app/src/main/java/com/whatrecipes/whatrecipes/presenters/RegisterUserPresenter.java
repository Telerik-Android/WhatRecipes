package com.whatrecipes.whatrecipes.presenters;

import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.data.firebase.FirebaseAuthenticationInteractor;
import com.whatrecipes.whatrecipes.data.firebase.ResponseListener;
import com.whatrecipes.whatrecipes.utils.Validator;

import javax.inject.Inject;

/**
 * Created by fatal on 2/21/2017.
 */

public class RegisterUserPresenter implements IPresenter.RegisterUserPresenter {
    private final FirebaseAuthenticationInteractor firebaseAuth;
    private IView.RegisterUserView mView;

    @Inject
    public RegisterUserPresenter(FirebaseAuthenticationInteractor firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void setView(IView.RegisterUserView view) {
        this.mView = view;
    }

    @Override
    public void registerUser(String email, String password) {
        if (!Validator.stringEmptyOrNull(email, password)) {
            mView.showProgressBar();
            firebaseAuth.registerUser(email, password, bindUserRegisterListener());
        } else {
            mView.showAllFieldsMustBeFilledMessage();
        }
    }

    private ResponseListener bindUserRegisterListener() {
        return new ResponseListener() {
            @Override
            public void onSuccessfulAuthentication() {
                mView.hideProgressBar();
                mView.showSuccessfulRegisterMessage();
                mView.finishActivity();
            }

            @Override
            public void onFailedAuthentication() {
                mView.hideProgressBar();
                mView.showInvalidRegisterMessage();
            }
        };
    }
}
