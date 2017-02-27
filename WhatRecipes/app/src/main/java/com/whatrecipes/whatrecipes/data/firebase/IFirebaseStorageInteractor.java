package com.whatrecipes.whatrecipes.data.firebase;

import android.app.Activity;

import com.whatrecipes.whatrecipes.data.firebase.listeners.RequestListener;

/**
 * Created by fatal on 2/20/2017.
 */

public interface IFirebaseStorageInteractor {
    void uploadUserProfileImageToStorage(Activity activity, byte[] imageBytes, RequestListener<String> listener);

    void uploadRecipeImageToStorage(Activity activity, byte[] imageBytes, RequestListener<String> listener);
}
