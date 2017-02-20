package com.whatrecipes.whatrecipes;

import android.widget.LinearLayout;

import com.whatrecipes.whatrecipes.data.Recipe;
import com.whatrecipes.whatrecipes.data.RecipeAdapter;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public interface IView {

    interface RecipeStackView {
        void showRecipesStack(ArrayList<Recipe> recipes);
        void addRecipeToAdapter(Recipe recipe);
    }

    interface AddNewRecipeView {
        void handleAddNewIngredientForm();
        LinearLayout addIngridientFormView();
        void takeCameraPhoto();
        void handleCancelButtonClick();
        boolean parseRecipeForm();
    }
}
