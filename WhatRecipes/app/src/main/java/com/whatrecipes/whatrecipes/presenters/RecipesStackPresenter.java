package com.whatrecipes.whatrecipes.presenters;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.data.Recipe;
import com.whatrecipes.whatrecipes.data.firebase.IFirebaseDatabaseInteractor;
import com.whatrecipes.whatrecipes.utils.RecipeViewUtils;

import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by dnt on 13.2.2017 г..
 */

public class RecipesStackPresenter implements IPresenter.RecipeStackPresenter, ChildEventListener {
    private IView.RecipeStackView mView;
    private final IFirebaseDatabaseInteractor db;

    @Inject
    public RecipesStackPresenter(IFirebaseDatabaseInteractor db) {
        this.db = db;
    }

    @Override
    public void loadRecipesStack() {
        db.getAllRecipes(this);

    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        if (dataSnapshot != null) {
            String uid = dataSnapshot.getKey();
            Recipe model = dataSnapshot.getValue(Recipe.class);

            try {
                model.getLovedBy().size();
            } catch (NullPointerException ex) {
                model.initLovedByArrayBecauseFuckFirebase();
            }

            model.setUid(uid);
            if (model != null) {
                mView.addRecipeToAdapter(model);
            }
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        if (dataSnapshot != null) {
            Recipe model = dataSnapshot.getValue(Recipe.class);
            if (model != null) {
                mView.addRecipeToAdapter(model);
            }
        }
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }


    private ArrayList<Recipe> collectStackRecipes(Map<String, Object> recipes) {
        ArrayList<Recipe> recipesStack = new ArrayList<>();

        for (Map.Entry<String, Object> entry : recipes.entrySet()) {
//            recipesStack.add(entry.getValue());
        }

        return recipesStack;
    }

    @Override
    public void setView(IView.RecipeStackView view) {
        this.mView = view;
    }
}
