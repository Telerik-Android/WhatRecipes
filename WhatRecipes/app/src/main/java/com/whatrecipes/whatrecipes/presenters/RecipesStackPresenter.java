package com.whatrecipes.whatrecipes.presenters;

import com.google.firebase.database.FirebaseDatabase;
import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.IView;

import javax.inject.Inject;

/**
 * Created by dnt on 13.2.2017 г..
 */

public class RecipesStackPresenter implements IPresenter.RecipeStackPresenter {


    private IView.RecipeStackView mView;
    private final FirebaseDatabase db;

    @Inject
    public RecipesStackPresenter(FirebaseDatabase db){
        this.db = db;
        //this.mView = mView;
    }

    @Override
    public void loadRecipesStack() {
        // load from firebase or local database
    }

    public void setmView(IView.RecipeStackView mView) {
        this.mView = mView;
    }
}
