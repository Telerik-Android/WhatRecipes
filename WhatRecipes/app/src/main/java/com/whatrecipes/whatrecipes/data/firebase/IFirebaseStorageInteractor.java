package com.whatrecipes.whatrecipes.data.firebase;

/**
 * Created by fatal on 2/20/2017.
 */

public interface IFirebaseStorageInteractor {
    void uploadImageToStorage(byte[] imageBytes, RequestListener<String> listener);
}
