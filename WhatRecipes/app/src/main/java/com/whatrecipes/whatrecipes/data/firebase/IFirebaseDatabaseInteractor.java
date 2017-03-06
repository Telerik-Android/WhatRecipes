package com.whatrecipes.whatrecipes.data.firebase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;
import com.whatrecipes.whatrecipes.data.Recipe;
import com.whatrecipes.whatrecipes.data.firebase.listeners.RequestListener;

import java.util.List;

/**
 * Created by fatal on 2/19/2017.
 */
public interface IFirebaseDatabaseInteractor {
    void requestFromValidLink(RequestListener<Recipe> listener);

    void requestFromInvalidLink(RequestListener<Recipe> listener);

    void pushRecipe(Recipe recipe);

    void getAllRecipes(ChildEventListener listener);

    void getRecipeNameBeginsWith(ValueEventListener listener, String name);

    void updateRecipe(String uid, String field, String Value);

    void updateRecipe(String uid, String field, List<String> Value);
}
