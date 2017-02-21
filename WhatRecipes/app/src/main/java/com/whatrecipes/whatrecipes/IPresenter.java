package com.whatrecipes.whatrecipes;

import com.whatrecipes.whatrecipes.data.Recipe;
import com.whatrecipes.whatrecipes.presenters.Presenter;

import java.util.List;
import java.util.Map;

public interface IPresenter {

    interface RecipeStackPresenter  extends Presenter<IView.RecipeStackView> {
        void loadRecipesStack();
    }

    interface AddRecipePresenter  extends Presenter<IView.AddNewRecipeView>{

        void saveRecipeToFirebaseDb(
                String recipeTitle,
                String recipeSummary,
                Map<String, String> ingredients,
                Integer cookingTime,
                String encodedBitmap,
                String howToPrepare,
                Integer servings,
                List<String> tags,
                String author
        );
    }

    interface RegisterUserPresenter extends Presenter<IView.RegisterUserView>{
        void registerUser(String email, String password);
    }

    interface LogInPresenter extends Presenter<IView.LogInUserView>{
        void logInUser(String email, String password);
    }
}
