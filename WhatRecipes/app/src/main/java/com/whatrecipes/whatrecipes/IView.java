package com.whatrecipes.whatrecipes;

import com.whatrecipes.whatrecipes.data.RecipeAdapter;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public interface IView {

    interface StackView{
        void showRecipesStack(RecipeAdapter recipes);
    }

    interface AddNewRecipeView {
        void showAddRecipeForm();
    }
}
