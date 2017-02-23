package com.whatrecipes.whatrecipes.data.firebase;

import android.app.Activity;

/**
 * Created by fatal on 2/20/2017.
 */

public interface IFirebaseStorageInteractor {
    void uploadImageToStorage(Activity activity, byte[] imageBytes, RequestListener<String> listener);
}
