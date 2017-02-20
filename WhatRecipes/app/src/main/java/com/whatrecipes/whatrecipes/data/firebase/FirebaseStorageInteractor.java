package com.whatrecipes.whatrecipes.data.firebase;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import javax.inject.Inject;

/**
 * Created by fatal on 2/20/2017.
 */

public class FirebaseStorageInteractor implements IFirebaseStorageInteractor {
    private final FirebaseStorage firebaseStorage;

    @Inject
    public FirebaseStorageInteractor(FirebaseStorage firebaseStorage){
        this.firebaseStorage = firebaseStorage;
    }


    //TODO THIS TEST METHOD, NEEDS TO BE REFACTORED
    @Override
    public void uploadImageToStorage(byte[] imageBytes,final RequestListener<String> listener) {
        UploadTask uploadTask = firebaseStorage.getReference("test").putBytes(imageBytes); //reference should be unique each time so the file isn't overwritten
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if(taskSnapshot.getDownloadUrl()!=null){
                    listener.onSuccessfulRequest(taskSnapshot.getDownloadUrl().toString());
                }
            }
        });
    }
}
