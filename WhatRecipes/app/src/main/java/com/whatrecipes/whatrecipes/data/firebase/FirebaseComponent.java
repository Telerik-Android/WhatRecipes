package com.whatrecipes.whatrecipes.data.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.whatrecipes.whatrecipes.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by fatal on 2/16/2017.
 */
//
@Singleton
@Component(modules = {AppModule.class,FirebaseModule.class})
public interface FirebaseComponent {
    FirebaseDatabase firebaseDatabase();
    FirebaseAuth firebaseAuth();
}
