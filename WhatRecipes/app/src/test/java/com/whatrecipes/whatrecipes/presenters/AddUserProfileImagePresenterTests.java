package com.whatrecipes.whatrecipes.presenters;

import android.app.Activity;

import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.data.firebase.IFirebaseAuthenticationInteractor;
import com.whatrecipes.whatrecipes.data.firebase.IFirebaseStorageInteractor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Created by fatal on 25.2.2017 г..
 */

public class AddUserProfileImagePresenterTests {
    private IView.AddUserProfileImage mView;
    private IFirebaseAuthenticationInteractor firebaseAuth;
    private IFirebaseStorageInteractor firebaseStore;
    private AddUserProfileImagePresenter presenter;
    private Activity activity;
    private final String validImageUrl = "https://firebasestorage.googleapis.com/v0/b/whatrecipes.appspot.com/o/profileImages%2F1ea1a0c1-76fd-4d5f-9a16-7c54ada0fb5d.jpg?alt=media&token=acbc057f-bcec-40b2-b637-645ddbe57804";

    private final byte[] nullImageByteArray = null;

    @Before
    public void Init() {
        MockitoAnnotations.initMocks(this);
        this.activity = mock(Activity.class);
        this.mView = mock(IView.AddUserProfileImage.class);
        this.firebaseAuth = mock(IFirebaseAuthenticationInteractor.class);
        this.firebaseStore = mock(IFirebaseStorageInteractor.class);


        this.presenter = new AddUserProfileImagePresenter(firebaseAuth, firebaseStore);
        this.presenter.setView(mView);
    }

    @Test
    public void uploadImageToStorage_withNullImageByteArray_shouldNotCallMethod_firebaseStore_uploadImageToStorage(){
        this.presenter.uploadImageToStorage(this.activity,nullImageByteArray);
        verifyNoMoreInteractions(mView, firebaseStore);
    }

//    @Test
//    public void uploadImageToStorage_withInvalidImageByteArray_shouldNotCallMethod_firebaseStore_uploadImageToStorage(){
//        //not implemented test method
//        //no functionality implemented for validating byte array of image
//    }

    @Test
    public void uploadImageToStorage_withValidImageByteArray_shouldCallMethod_firebaseStore_uploadImageToStorage(){
        //not implemented test method
        //no functionality implemented for validating byte array of image
        final byte[] validImageByteArray = null;
        this.presenter.uploadImageToStorage(this.activity,validImageByteArray);
        verify(mView).showProgressBar();
        verify(firebaseStore,times(1)).uploadImageToStorage(this.activity,validImageByteArray,this.presenter);
    }

    @Test
    public void addUserProfileImageLisener_onSuccessfulRequest_shouldSave_CurrentImageUrl() throws Exception {
        this.presenter.onSuccessfulRequest(validImageUrl);
        verify(firebaseAuth,times(1)).changeUserImageUrl(validImageUrl);
        verify(mView).hideProgressBar();
        verify(mView).showOnSuccessfulUploadToast();
        verify(mView).finishActivity(RESULT_OK);
        verifyNoMoreInteractions(mView, firebaseAuth);
    }

    @Test
    public void addUserProfileImageLisener_onFailedRequest_shouldPerform_properCalls() throws Exception {
        this.presenter.onFailedRequest();
        verify(mView).hideProgressBar();
        verify(mView).showFailedUploadToast();
        verify(mView).finishActivity(RESULT_CANCELED);
        verifyNoMoreInteractions(mView, firebaseAuth);
    }
}
