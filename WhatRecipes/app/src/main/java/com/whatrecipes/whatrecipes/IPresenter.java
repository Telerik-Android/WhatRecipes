package com.whatrecipes.whatrecipes;

import android.app.Activity;

import com.whatrecipes.whatrecipes.data.Recipe;
import com.whatrecipes.whatrecipes.presenters.Presenter;

import java.util.List;
import java.util.Map;

public interface IPresenter {

    interface ImageClassifierPresenter extends Presenter<IView.ImageClassifierView> {
        void getRecipeWithName(String name);
    }

    interface RecipeStackPresenter extends Presenter<IView.RecipeStackView> {
        void loadRecipesStack();
    }

    interface RecipeDetailsPresenter extends Presenter<IView.RecipeDetailsView>{
        void loadRecipe(String id);
        void updateLoves(Recipe recipe);
    }

    interface AddRecipePresenter extends Presenter<IView.AddNewRecipeView> {
        String getLoggedUserEmail();

        String getLoggedUserImageUrl();

        void uploadImageToStorage(Activity activity, byte[] imageByteArray);

        void saveRecipeToFirebaseDb(
                String recipeTitle,
                String recipeSummary,
                List<String> ingridientsName,
                List<String> ingridientsQuantity,
                Integer cookingTime,
                String encodedBitmap,
                String howToPrepare,
                Integer servings,
                List<String> tags,
                String author,
                String authorImageUrl
        );
    }

    interface RegisterUserPresenter extends Presenter<IView.RegisterUserView> {
        void registerUser(String email, String password);

    }

    interface LogInPresenter extends Presenter<IView.LogInUserView> {
        void logInUser(String email, String password);
    }

    interface AddUserProfileImagePresenter extends Presenter<IView.AddUserProfileImage> {
        void uploadImageToStorage(Activity activity, byte[] imageByteArray);
    }
}
