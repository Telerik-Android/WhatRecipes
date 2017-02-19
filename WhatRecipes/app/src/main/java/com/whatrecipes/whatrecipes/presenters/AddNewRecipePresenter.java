package com.whatrecipes.whatrecipes.presenters;

import android.support.v4.app.Fragment;

import com.google.firebase.database.FirebaseDatabase;
import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.data.Recipe;
import com.whatrecipes.whatrecipes.utils.CameraUtils;

import javax.inject.Inject;

/**
 * Created by dnt on 13.2.2017 г..
 */

public class AddNewRecipePresenter implements IPresenter.AddRecipePresenter {
    private FirebaseDatabase firebaseDb;
    IView.AddNewRecipeView mView;

    @Inject
    public AddNewRecipePresenter(FirebaseDatabase firebaseDb, IView.AddNewRecipeView mView) {
        this.firebaseDb = firebaseDb;
        this.mView = mView;
    }


    @Override
    public void saveRecipeToFirebaseDb(Recipe recipe) {
        firebaseDb.getReference("recipes").push().setValue(recipe);
    }
}
