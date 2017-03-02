package com.whatrecipes.whatrecipes.presenters;

import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.data.Recipe;
import com.whatrecipes.whatrecipes.data.firebase.IFirebaseAuthenticationInteractor;
import com.whatrecipes.whatrecipes.data.firebase.IFirebaseDatabaseInteractor;
import com.whatrecipes.whatrecipes.utils.Validator;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by fatal on 28.2.2017 г..
 */

public class RecipeDetailsPresenter implements IPresenter.RecipeDetailsPresenter {
    private final IFirebaseAuthenticationInteractor firebaseAuth;
    private final IFirebaseDatabaseInteractor firebaseDb;
    private IView.RecipeDetailsView mView;

    @Inject
    public RecipeDetailsPresenter(IFirebaseDatabaseInteractor firebaseDb, IFirebaseAuthenticationInteractor firebaseAuth) {
        this.firebaseDb = firebaseDb;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void loadRecipe(String id) {

    }

    @Override
    public void updateLoves(Recipe recipe) {
        if (!Validator.stringEmptyOrNull(firebaseAuth.getLoggedInUserEmail())) {
            String loggedUserUID = firebaseAuth.getLoggedInUserUID();

            if (!recipe.getLovedBy().contains(loggedUserUID)) {
                recipe.getLovedBy().add(loggedUserUID);

                firebaseDb.updateRecipe(recipe.getUid(), "lovedBy", recipe.getLovedBy());
                mView.showRecipe(recipe);
            }
            else{
                recipe.getLovedBy().remove(loggedUserUID);
                firebaseDb.updateRecipe(recipe.getUid(), "lovedBy", recipe.getLovedBy());
                mView.showRecipe(recipe);
            }
        } else {
            mView.showYouMustBeLogged();
        }

    }

    @Override
    public void setView(IView.RecipeDetailsView view) {
        this.mView = view;
    }
}
