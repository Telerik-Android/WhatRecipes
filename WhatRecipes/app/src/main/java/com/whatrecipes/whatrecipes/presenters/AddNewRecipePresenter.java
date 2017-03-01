package com.whatrecipes.whatrecipes.presenters;

import android.app.Activity;

import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.data.Recipe;
import com.whatrecipes.whatrecipes.data.firebase.IFirebaseAuthenticationInteractor;
import com.whatrecipes.whatrecipes.data.firebase.IFirebaseDatabaseInteractor;
import com.whatrecipes.whatrecipes.data.firebase.IFirebaseStorageInteractor;
import com.whatrecipes.whatrecipes.data.firebase.listeners.RequestListener;
import com.whatrecipes.whatrecipes.utils.Validator;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by dnt on 13.2.2017 г..
 */

public class AddNewRecipePresenter implements IPresenter.AddRecipePresenter, RequestListener<String> {
    private final IFirebaseAuthenticationInteractor firebaseAuth;
    private final IFirebaseStorageInteractor firebaseStore;
    public IFirebaseDatabaseInteractor firebaseDb;
    public IView.AddNewRecipeView mView;
    private String imageUrl;

    @Inject
    public AddNewRecipePresenter(IFirebaseStorageInteractor firebaseStore,
                                 IFirebaseDatabaseInteractor firebaseDb,
                                 IFirebaseAuthenticationInteractor firebaseAuth) {
        this.firebaseDb = firebaseDb;
        this.firebaseAuth = firebaseAuth;
        this.firebaseStore = firebaseStore;
    }

    @Override
    public String getLoggedUserEmail() {
        return firebaseAuth.getLoggedInUserEmail();
    }

    @Override
    public String getLoggedUserImageUrl() {
        return firebaseAuth.getLoggedInUserImageURL();
    }

    @Override
    public void uploadImageToStorage(Activity activity, byte[] imageByteArray) {
        if (imageByteArray != null) {
            mView.showProgressBar();
            firebaseStore.uploadRecipeImageToStorage(activity,imageByteArray, this);
        }
    }

    @Override
    public void saveRecipeToFirebaseDb(String recipeTitle, String recipeSummary, List<String> ingredientsName, List<String> ingredientsQuantity, Integer cookingTime, String imageUrl, String howToPrepare, Integer servings, List<String> tags, String author, String authorImageUrl) {
        if (Validator.stringEmptyOrNull(recipeTitle, recipeSummary, this.imageUrl, howToPrepare, author)) {
            throw new IllegalArgumentException("String cannot be null");
        }

        for (String tag : tags) {
            if (Validator.stringEmptyOrNull(tag)) {
                throw new IllegalArgumentException("String cannot be null");
            }
        }

        Recipe recipe = new Recipe(recipeTitle, recipeSummary, ingredientsName, ingredientsQuantity, cookingTime, this.imageUrl, howToPrepare, servings, tags, author, authorImageUrl);

        firebaseDb.pushRecipe(recipe);
    }

    @Override
    public void setView(IView.AddNewRecipeView view) {
        this.mView = view;
    }

    @Override
    public void onSuccessfulRequest(String callback) {
        this.imageUrl = callback;
        mView.hideProgressBar();
        mView.showOnSuccessfulUploadToast();
    }

    @Override
    public void onFailedRequest() {

        mView.hideProgressBar();
        mView.showFailedUploadToast();
    }
}
