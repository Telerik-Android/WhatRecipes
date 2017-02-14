package com.whatrecipes.whatrecipes;

import com.whatrecipes.whatrecipes.data.Recipe;
import com.whatrecipes.whatrecipes.presenters.Presenter;

public interface IPresenter {

    interface RecipeStackPresenter extends Presenter<IView.RecipeStackView>{
        void loadRecipesStack();
    }

    interface AddRecipePresenter extends Presenter<IView.AddNewRecipeView>{
        void loadAddRecipeForm();
        void saveRecipeToFirebaseDb(Recipe recipe);
    }
}
