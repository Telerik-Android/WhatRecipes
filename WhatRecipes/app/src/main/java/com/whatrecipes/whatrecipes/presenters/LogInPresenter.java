package com.whatrecipes.whatrecipes.presenters;

import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.data.firebase.FirebaseAuthenticationInteractor;
import com.whatrecipes.whatrecipes.data.firebase.ResponseListener;
import com.whatrecipes.whatrecipes.utils.Validator;

import javax.inject.Inject;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by fatal on 2/21/2017.
 */

public class LogInPresenter implements IPresenter.LogInPresenter {

    private final FirebaseAuthenticationInteractor firebaseAuth;
    private IView.LogInUserView mView;

    @Inject
    public LogInPresenter(FirebaseAuthenticationInteractor firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void setView(IView.LogInUserView view) {
        this.mView = view;
    }


    @Override
    public void logInUser(String email, String password) {
        if (!Validator.stringEmptyOrNull(email, password)) {
            mView.showProgressBar();
            firebaseAuth.logTheUserIn(email, password, bindLoginListener());
        } else {
            mView.showAllFieldsMustBeFilledMessage();
        }
    }

    private ResponseListener bindLoginListener() {
        return new ResponseListener() {
            @Override
            public void onSuccessfulAuthentication() {
                mView.hideProgressBar();
                mView.showSuccessfulLogInMessage();
                mView.finishActivity(RESULT_OK);

            }

            @Override
            public void onFailedAuthentication() {
                mView.hideProgressBar();
                mView.showInvalidLogInMessage();
                mView.finishActivity(RESULT_CANCELED);

            }
        };
    }

}
