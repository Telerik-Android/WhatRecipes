package com.whatrecipes.whatrecipes.data.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.whatrecipes.whatrecipes.utils.CustomScope;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
@CustomScope
@Module
public class FirebaseModule {

    @Singleton
    @Provides
    public FirebaseDatabase provideFirebaseDatabase() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        return FirebaseDatabase.getInstance();
    }

    @Singleton
    @Provides
    public FirebaseStorage provideFirebaseStorage() {
        return FirebaseStorage.getInstance();
    }

    @Singleton
    @Provides
    public FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    public IFirebaseDatabaseInteractor provideFirebaseDatabaseInteractor(FirebaseDatabase firebaseDatabase) {
        return new FirebaseDatabaseInteractor(firebaseDatabase);
    }

    @Provides
    public IFirebaseAuthenticationInteractor provideFirebaseAuthenticationInteractor(FirebaseAuth firebaseAuth) {
        return new FirebaseAuthenticationInteractor(firebaseAuth);
    }

    @Provides
    public IFirebaseStorageInteractor provideFirebaseStorageInteractor(FirebaseStorage firebaseStorage) {
        return new FirebaseStorageInteractor(firebaseStorage);
    }
}
