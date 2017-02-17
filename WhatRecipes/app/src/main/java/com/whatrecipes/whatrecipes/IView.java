package com.whatrecipes.whatrecipes;

import android.widget.LinearLayout;

import com.whatrecipes.whatrecipes.data.Recipe;
import com.whatrecipes.whatrecipes.data.RecipeAdapter;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public interface IView {

    interface RecipeStackView {
        void showRecipesStack(ArrayList<Recipe> recipes);
    }

    interface AddNewRecipeView {
        void showAddRecipeForm();
        LinearLayout addIngridientFormView();
    }
}
