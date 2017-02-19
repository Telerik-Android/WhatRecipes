package com.whatrecipes.whatrecipes;

import com.whatrecipes.whatrecipes.data.Recipe;

public interface IPresenter {

    interface RecipeStackPresenter {
        void loadRecipesStack();
    }

    interface AddRecipePresenter {

        void saveRecipeToFirebaseDb(Recipe recipe);
    }
}
