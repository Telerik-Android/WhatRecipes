package com.whatrecipes.whatrecipes.presenters;

import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.data.firebase.FirebaseAuthenticationInteractor;
import com.whatrecipes.whatrecipes.data.firebase.IFirebaseAuthenticationInteractor;
import com.whatrecipes.whatrecipes.data.firebase.listeners.ResponseListener;
import com.whatrecipes.whatrecipes.utils.Validator;

import javax.inject.Inject;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by fatal on 2/21/2017.
 */

public class LogInPresenter implements IPresenter.LogInPresenter, ResponseListener {

    private final IFirebaseAuthenticationInteractor firebaseAuth;
    private IView.LogInUserView mView;

    @Inject
    public LogInPresenter(IFirebaseAuthenticationInteractor firebaseAuth) {
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
            firebaseAuth.logTheUserIn(email, password,this);
        } else {
            mView.showAllFieldsMustBeFilledMessage();
        }
    }

    @Override
    public void onSuccessListener() {
        mView.hideProgressBar();
        mView.showSuccessfulLogInMessage();
        mView.finishActivity(RESULT_OK);

    }

    @Override
    public void onFailedListener() {
        mView.hideProgressBar();
        mView.showInvalidLogInMessage();
        mView.finishActivity(RESULT_CANCELED);

    }
}
