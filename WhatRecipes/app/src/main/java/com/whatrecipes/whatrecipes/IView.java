package com.whatrecipes.whatrecipes;

import android.widget.LinearLayout;

import com.whatrecipes.whatrecipes.data.RecipeAdapter;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public interface IView {

    interface RecipeStackView {
        void showRecipesStack(RecipeAdapter recipes);
    }

    interface AddNewRecipeView {
        void showAddRecipeForm();
        LinearLayout addIngridientFormView();
    }
}
