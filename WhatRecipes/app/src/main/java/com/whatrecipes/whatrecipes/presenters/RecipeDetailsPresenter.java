package com.whatrecipes.whatrecipes.presenters;

import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.data.firebase.IFirebaseAuthenticationInteractor;
import com.whatrecipes.whatrecipes.data.firebase.IFirebaseDatabaseInteractor;

import javax.inject.Inject;

/**
 * Created by fatal on 28.2.2017 г..
 */

public class RecipeDetailsPresenter implements IPresenter.RecipeDetailsPresenter{
    private final IFirebaseAuthenticationInteractor firebaseAuth;
    private final IFirebaseDatabaseInteractor firebaseDb;
    private IView.RecipeDetailsView mView;

    @Inject
    public RecipeDetailsPresenter(IFirebaseDatabaseInteractor firebaseDb, IFirebaseAuthenticationInteractor firebaseAuth){
        this.firebaseDb = firebaseDb;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void loadRecipe(String id) {

    }

    @Override
    public void setView(IView.RecipeDetailsView view) {
        this.mView = view;
    }
}
