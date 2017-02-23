package com.whatrecipes.whatrecipes.presenters;

import android.support.v4.app.Fragment;

import com.google.firebase.database.FirebaseDatabase;
import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.data.Recipe;
import com.whatrecipes.whatrecipes.data.common.FirebaseConstants;
import com.whatrecipes.whatrecipes.data.firebase.FirebaseDatabaseInteractor;
import com.whatrecipes.whatrecipes.data.firebase.IFirebaseAuthenticationInteractor;
import com.whatrecipes.whatrecipes.data.firebase.IFirebaseDatabaseInteractor;
import com.whatrecipes.whatrecipes.utils.CameraUtils;
import com.whatrecipes.whatrecipes.utils.Validator;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by dnt on 13.2.2017 г..
 */

public class AddNewRecipePresenter implements IPresenter.AddRecipePresenter {
    private final IFirebaseAuthenticationInteractor firebaseAuth;
    public IFirebaseDatabaseInteractor firebaseDb;
    public IView.AddNewRecipeView mView;

    @Inject
    public AddNewRecipePresenter(IFirebaseDatabaseInteractor firebaseDb, IFirebaseAuthenticationInteractor firebaseAuth) {
        this.firebaseDb = firebaseDb;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public String getLoggedUserEmail(){
        return firebaseAuth.getLoggedInUserEmail();
    }

    @Override
    public void saveRecipeToFirebaseDb(String recipeTitle, String recipeSummary, Map<String, String> ingredients, Integer cookingTime, String encodedBitmap, String howToPrepare, Integer servings, List<String> tags, String author) {
        if(Validator.stringEmptyOrNull(recipeTitle,recipeSummary,encodedBitmap,howToPrepare,author)){
            throw new IllegalArgumentException("String cannot be null");
        }

        for (String tag: tags){
            if(Validator.stringEmptyOrNull(tag)) {
                throw new IllegalArgumentException("String cannot be null");
            }
        }

        Recipe recipe = new Recipe(recipeTitle, recipeSummary, ingredients, cookingTime, encodedBitmap, howToPrepare, servings, tags, author);

        firebaseDb.pushRecipe(recipe);
    }

    @Override
    public void setView(IView.AddNewRecipeView view) {
        this.mView = view;
    }
}
