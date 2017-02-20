package com.whatrecipes.whatrecipes.data.firebase;

import com.google.firebase.database.ChildEventListener;
import com.whatrecipes.whatrecipes.data.Recipe;

/**
 * Created by fatal on 2/19/2017.
 */
public interface IFirebaseDatabaseInteractor {
    void requestFromValidLink(RequestListener<Recipe> listener);

    void requestFromInvalidLink(RequestListener<Recipe> listener);

    void pushRecipe(Recipe recipe);

    void getAllRecipes(ChildEventListener listener);
}
