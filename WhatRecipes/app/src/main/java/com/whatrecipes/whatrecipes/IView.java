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

        void finishActivity();

        void startTakeAPhotoActivity();

        void loadImageFromResources();

        void showOnSuccessfulUploadToast();

        void showFailedUploadToast();
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

        void finishActivity();
    }
}
