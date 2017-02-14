package com.whatrecipes.whatrecipes.presenters;

import android.support.v4.app.Fragment;

import com.google.firebase.database.FirebaseDatabase;
import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.data.Recipe;
import com.whatrecipes.whatrecipes.utils.CameraUtils;

import javax.inject.Inject;

/**
 * Created by dnt on 13.2.2017 г..
 */

public class AddNewRecipePresenter implements IPresenter.AddRecipePresenter {
    private FirebaseDatabase firebaseDb;

    @Inject
    public AddNewRecipePresenter(FirebaseDatabase firebaseDb) {
        this.firebaseDb = firebaseDb;
    }

    @Override
    public void loadAddRecipeForm() {

    }


    @Override
    public void saveRecipeToFirebaseDb(Recipe recipe) {
        firebaseDb.getReference("recipes").push().setValue(recipe);
    }
}
