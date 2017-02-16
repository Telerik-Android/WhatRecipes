package com.whatrecipes.whatrecipes.data;

import com.google.firebase.database.FirebaseDatabase;
import com.whatrecipes.whatrecipes.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by fatal on 2/16/2017.
 */

@Singleton
@Component(modules = {AppModule.class,FirebaseModule.class})
public interface FirebaseComponent {
    FirebaseDatabase firebaseDatabase();
}
