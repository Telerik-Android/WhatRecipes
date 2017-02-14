package com.whatrecipes.whatrecipes.presenters;

import com.google.firebase.database.FirebaseDatabase;
import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.IView;

import javax.inject.Inject;

/**
 * Created by dnt on 13.2.2017 г..
 */

public class RecipesStackPresenter implements IPresenter.RecipeStackPresenter {


    @Inject
    public RecipesStackPresenter(FirebaseDatabase db){

    }

    @Override
    public void loadRecipesStack() {
        // load from firebase or local database
    }
}
