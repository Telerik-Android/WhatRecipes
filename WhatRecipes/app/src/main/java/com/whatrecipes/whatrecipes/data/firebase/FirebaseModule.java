package com.whatrecipes.whatrecipes.data.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
    public FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    public FirebaseDatabaseInteractor provideFirebaseDatabaseInteractor(FirebaseDatabase firebaseDatabase) {
        return new FirebaseDatabaseInteractorImpl(firebaseDatabase);
    }

    @Provides
    public FirebaseAuthenticationInteractor provideFirebaseAuthenticationInteractor(FirebaseAuth firebaseAuth) {
        return new FirebaseAuthenticationInteractorImpl(firebaseAuth);
    }
}
