package com.whatrecipes.whatrecipes;

import android.view.View;
import android.widget.LinearLayout;

import com.whatrecipes.whatrecipes.data.Recipe;
import com.whatrecipes.whatrecipes.data.RecipeAdapter;

import java.util.ArrayList;

import butterknife.OnClick;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public interface IView {

    interface ImageClassifierView {
        void setClassificationName(String name);

        void openRecipeDetails(Recipe recipe);
    }

    interface RecipeStackView {
        void showRecipesStack(ArrayList<Recipe> recipes);

        void addRecipeToAdapter(Recipe recipe);
    }

    interface RecipeDetailsView{
        void showRecipe(Recipe recipe);

        void showYouMustBeLogged();
    }

    interface AddNewRecipeView {
        void handleAddNewIngredientForm();

        LinearLayout addIngridientFormView();

        void handleCancelButtonClick();

        void handleSubmitButtonClick();

        boolean parseRecipeForm();

        boolean validateRecipeForm();

        void showOnSuccessfulUploadToast();

        void showFailedUploadToast();

        void showProgressBar();

        void hideProgressBar();

    }

    interface RegisterUserView {
        void registerUser(String email, String password);

        void handleRegisterButtonClick();

        void handleCancelButtonClick();

        void showProgressBar();

        void hideProgressBar();

        void showInvalidRegisterMessage();

        void showSuccessfulRegisterMessage();

        void showAllFieldsMustBeFilledMessage();

        void finishActivity(int result);
    }

    interface LogInUserView {
        void LogInUser();

        void handleLogInButtonClick();

        void handleCancelButtonClick();

        void showProgressBar();

        void hideProgressBar();

        void showInvalidLogInMessage();

        void showSuccessfulLogInMessage();

        void showAllFieldsMustBeFilledMessage();

        void finishActivity(int result);

        boolean validate();
    }

    interface AddUserProfileImage {

        void showProgressBar();

        void hideProgressBar();

        void showOnSuccessfulUploadToast();

        void showFailedUploadToast();

        void finishActivity(int result);

    }
}
