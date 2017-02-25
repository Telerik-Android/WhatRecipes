package com.whatrecipes.whatrecipes.presenters;

import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.data.firebase.FirebaseAuthenticationInteractor;
import com.whatrecipes.whatrecipes.data.firebase.FirebaseDatabaseInteractor;
import com.whatrecipes.whatrecipes.data.firebase.IFirebaseAuthenticationInteractor;
import com.whatrecipes.whatrecipes.data.firebase.IFirebaseStorageInteractor;
import com.whatrecipes.whatrecipes.presenters.RegisterUserPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by fatal on 25.2.2017 г..
 */

public class RegisterUserPresenterTests {
    private final String validEmail = "asd@asd.asd";
    private final String emptyEmail = "";
    private final String nullEmail = null;

    private final String validPassword = "asdasd";
    private final String emptyPassword = "";
    private final String nullPassword = null;


    private IView.RegisterUserView mView;
    private IFirebaseAuthenticationInteractor firebaseAuth;
    private IFirebaseStorageInteractor firebaseStore;

    private RegisterUserPresenter presenter;

    @Before
    public void Init() {
        MockitoAnnotations.initMocks(this);
        this.mView = mock(IView.RegisterUserView.class);
        this.firebaseAuth = mock(IFirebaseAuthenticationInteractor.class);
        this.firebaseStore = mock(IFirebaseStorageInteractor.class);

        this.presenter = new RegisterUserPresenter(firebaseAuth, firebaseStore);
        this.presenter.setView(mView);
    }

    @Test
    public void registerUser_WithEmptyEmail_shouldCallMethod_mView_showAllFieldsMustBeFilledMessage() {

        this.presenter.registerUser(this.emptyEmail, this.validPassword);

        verify(mView, times(1)).showAllFieldsMustBeFilledMessage();
    }

    @Test
    public void registerUser_WithNullEmail_shouldCallMethod_mView_showAllFieldsMustBeFilledMessage() {

        this.presenter.registerUser(this.nullEmail, this.validPassword);

        verify(mView, times(1)).showAllFieldsMustBeFilledMessage();
    }

    @Test
    public void registerUser_WithEmptyPassword_shouldCallMethod_mView_showAllFieldsMustBeFilledMessage() {

        this.presenter.registerUser(this.validEmail, this.emptyPassword);

        verify(mView, times(1)).showAllFieldsMustBeFilledMessage();
    }

    @Test
    public void registerUser_WithNullPassword_shouldCallMethod_mView_showAllFieldsMustBeFilledMessage() {

        this.presenter.registerUser(this.validEmail, this.nullPassword);

        verify(mView, times(1)).showAllFieldsMustBeFilledMessage();
    }

    @Test
    public void registerUser_WithCorrectData_shouldCallMethod(){
        this.presenter.registerUser(this.validEmail,this.validPassword);
        verify(mView, times(1)).showProgressBar();
        verify(firebaseAuth, times(1)).registerUser(this.validEmail, this.validPassword, presenter);
    }


    @Test
    public void bindUserRegisterListener_onSuccessfulAuthentication_shouldPerform_properCalls() throws Exception {
        this.presenter.onSuccessfulAuthentication();
        verify(mView).hideProgressBar();
        verify(mView).showSuccessfulRegisterMessage();
        verify(firebaseAuth).logTheUserOut();
        verify(mView).finishActivity(RESULT_OK);
        verifyNoMoreInteractions(mView, firebaseAuth);
    }

    @Test
    public void bindUserRegisterListener_onFailedAuthentication_shouldPerform_properCalls() throws Exception {
        presenter.onFailedAuthentication();
        verify(mView).hideProgressBar();
        verify(mView).showInvalidRegisterMessage();
        verify(mView).finishActivity(RESULT_CANCELED);
        verifyNoMoreInteractions(mView, firebaseAuth);
    }
}
