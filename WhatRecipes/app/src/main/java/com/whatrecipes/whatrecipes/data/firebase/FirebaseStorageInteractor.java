package com.whatrecipes.whatrecipes.data.firebase;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.whatrecipes.whatrecipes.view.ActivityRegister;

import java.util.UUID;

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

    @Override
    public void uploadImageToStorage(final Activity activity, byte[] imageBytes, final RequestListener<String> listener) {
        String path = "profileImages/" + UUID.randomUUID()+".jpg";
        //if memory leak, attach upload task to activity scope
        UploadTask uploadTask = firebaseStorage.getReference(path).putBytes(imageBytes); //reference should be unique each time so the file isn't overwritten
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if(taskSnapshot.getDownloadUrl()!=null){
                    listener.onSuccessfulRequest(taskSnapshot.getDownloadUrl().toString());
                }
            }
        });
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFailedRequest();
            }
        });
    }
}
