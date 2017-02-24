package com.whatrecipes.whatrecipes;

import android.view.View;
import android.widget.LinearLayout;

import com.whatrecipes.whatrecipes.data.Recipe;
import com.whatrecipes.whatrecipes.data.RecipeAdapter;

import java.util.ArrayList;

import butterknife.OnClick;

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

        void handleSubmitButtonClick();

        boolean parseRecipeForm();
    }

    interface RegisterUserView {
        void registerUser();

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
        void finishActivity(int result);

        void startTakeAPhotoActivity();

        void loadImageFromResources();

        void handleCancelButtonClick();

        void showOnSuccessfulUploadToast();

        void showFailedUploadToast();

        void showProgressBar();

        void hideProgressBar();

    }
}
