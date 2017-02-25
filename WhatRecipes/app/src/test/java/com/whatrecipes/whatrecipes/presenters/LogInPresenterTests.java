package com.whatrecipes.whatrecipes.presenters;

import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.data.firebase.IFirebaseAuthenticationInteractor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by fatal on 25.2.2017 г..
 */

public class LogInPresenterTests {
    private final String validEmail = "asd@asd.asd";
    private final String emptyEmail = "";
    private final String nullEmail = null;

    private final String validPassword = "asdasd";
    private final String emptyPassword = "";
    private final String nullPassword = null;


    private IView.LogInUserView mView;
    private IFirebaseAuthenticationInteractor firebaseAuth;

    private LogInPresenter presenter;

    @Before
    public void Init() {
        MockitoAnnotations.initMocks(this);
        this.mView = mock(IView.LogInUserView.class);
        this.firebaseAuth = mock(IFirebaseAuthenticationInteractor.class);

        this.presenter = new LogInPresenter(firebaseAuth);
        this.presenter.setView(mView);
    }

    @Test
    public void logInUser_WithEmptyEmail_shouldCallMethod_mView_showAllFieldsMustBeFilledMessage() {

        this.presenter.logInUser(this.emptyEmail, this.validPassword);

        verify(mView, times(1)).showAllFieldsMustBeFilledMessage();
    }

    @Test
    public void logInUser_WithNullEmail_shouldCallMethod_mView_showAllFieldsMustBeFilledMessage() {

        this.presenter.logInUser(this.nullEmail, this.validPassword);

        verify(mView, times(1)).showAllFieldsMustBeFilledMessage();
    }

    @Test
    public void logInUser_WithEmptyPassword_shouldCallMethod_mView_showAllFieldsMustBeFilledMessage() {

        this.presenter.logInUser(this.validEmail, this.emptyPassword);

        verify(mView, times(1)).showAllFieldsMustBeFilledMessage();
    }

    @Test
    public void logInUser_WithNullPassword_shouldCallMethod_mView_showAllFieldsMustBeFilledMessage() {

        this.presenter.logInUser(this.validEmail, this.nullPassword);

        verify(mView, times(1)).showAllFieldsMustBeFilledMessage();
    }

    @Test
    public void logInUser_WithCorrectData_shouldCallMethod_firebaseAuth_logTheUserIn(){
        this.presenter.logInUser(this.validEmail,this.validPassword);
        verify(mView, times(1)).showProgressBar();
        verify(firebaseAuth, times(1)).logTheUserIn(this.validEmail, this.validPassword, presenter);
    }


    @Test
    public void logInUserListener_onSuccessfulAuthentication_shouldPerform_properCalls() throws Exception {
        this.presenter.onSuccessfulAuthentication();
        verify(mView).hideProgressBar();
        verify(mView).showSuccessfulLogInMessage();
        verify(mView).finishActivity(RESULT_OK);
        verifyNoMoreInteractions(mView, firebaseAuth);
    }

    @Test
    public void logInUserListener_onFailedAuthentication_shouldPerform_properCalls() throws Exception {
        presenter.onFailedAuthentication();
        verify(mView).hideProgressBar();
        verify(mView).showInvalidLogInMessage();
        verify(mView).finishActivity(RESULT_CANCELED);
        verifyNoMoreInteractions(mView, firebaseAuth);
    }
}
