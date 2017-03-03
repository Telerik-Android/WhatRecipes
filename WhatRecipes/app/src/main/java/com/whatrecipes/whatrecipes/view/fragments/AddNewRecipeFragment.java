package com.whatrecipes.whatrecipes.view.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.whatrecipes.whatrecipes.App;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.presenters.AddNewRecipePresenter;
import com.whatrecipes.whatrecipes.utils.ActivityUtils;
import com.whatrecipes.whatrecipes.utils.CameraUtils;
import com.whatrecipes.whatrecipes.utils.ImageHelper;
import com.whatrecipes.whatrecipes.utils.RecipeViewUtils;
import com.whatrecipes.whatrecipes.utils.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dnt on 13.2.2017 г..
 */

public class AddNewRecipeFragment extends Fragment implements IView.AddNewRecipeView {
    public static final int TAG_INGREDIENT_NAME = 2001;
    public static final int TAG_INGREDIENT_QUANITY = 2002;

    private Bitmap RecipeThumbnail;
    private ArrayList<LinearLayout> ingredientsList;
    private String encodedBitmap;

    @BindView(R.id.camera_button)
    Button cameraButton;

    @BindView(R.id.iVRecipeImage)
    public ImageView imageView;

    @BindView(R.id.add_ingredient_field_button)
    Button addIngredientFieldButton;

    @BindView(R.id.submit_recipe)
    Button submitButton;

    @BindView(R.id.cancel_recipe)
    Button cancelButton;

    List<String> ingredientsName;
    List<String> ingredientsQuantity;
    @BindView(R.id.EditTextRecipeTextTitle)
    EditText edrecipeTitle;

    @BindView(R.id.EditTextRecipeTextSummary)
    EditText edrecipeSummary;

    @BindView(R.id.EditTextRecipeServings)
    EditText edservings;

    @BindView(R.id.EditTextRecipeCookingTime)
    EditText edcookingTime;

    @BindView(R.id.EditTextRecipeHowToPrepare)
    EditText edhowToPrepare;

    @BindView(R.id.EditTextRecipeTags)
    EditText edtagsToSplit;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Inject
    AddNewRecipePresenter presenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_recipe, container, false);


        this.ingredientsList = new ArrayList<>();
        App.get().component().inject(this);
        presenter.setView(this);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.submit_recipe)
    public void handleSubmitButtonClick() {
        //reads,validates and submits data
        if (validateRecipeForm() && parseRecipeForm()) {
            ActivityUtils.replaceFragmentToActivity(getFragmentManager(), new RecipesStackFragment(), R.id.cardStackFragment);
        }

    }

    public boolean validateRecipeForm(){
        if (!Validator.validateRequiredEditTextFields("Field is required", edrecipeTitle, edcookingTime, edservings, edcookingTime, edhowToPrepare, edtagsToSplit)) {
            return false;
        }

        if(!Validator.validateRequiredEditTextFields("Recipe summary must be below 200 chars and not empty",edrecipeSummary)){
            return false;
        }

        if (this.RecipeThumbnail == null) {
            Toast.makeText(getActivity(), "Image is required", Toast.LENGTH_LONG).show();
            return false;
        }


        return true;
    }


    @Override
    public boolean parseRecipeForm() {
        String recipeTitle = edrecipeTitle.getText().toString();
        String recipeSummary = edrecipeSummary.getText().toString();
        Integer cookingTime = Integer.valueOf(edcookingTime.getText().toString());
        String howToPrepare = edhowToPrepare.getText().toString();
        String tagsToSplit = edtagsToSplit.getText().toString();
        Integer servings = Integer.valueOf(edservings.getText().toString());
        tagsToSplit.replaceAll(";", " ");
        tagsToSplit.replaceAll(",", " ");
        String[] tags = tagsToSplit.split(" ");
        ViewGroup parent = (ViewGroup) getView().getParent();

        List<View> ingredientsNameV = RecipeViewUtils.findViewWithTagRecursively(parent, TAG_INGREDIENT_NAME);
        List<View> ingredientsQuantityV = RecipeViewUtils.findViewWithTagRecursively(parent, TAG_INGREDIENT_QUANITY);
        ingredientsName = RecipeViewUtils.parseIngredientsByViews(ingredientsNameV);
        ingredientsQuantity =  RecipeViewUtils.parseIngredientsByViews(ingredientsQuantityV);


        encodedBitmap = RecipeViewUtils.setEncodedImage(this.RecipeThumbnail);
        String author = "anonymous";


        if (!Validator.stringEmptyOrNull(presenter.getLoggedUserEmail())) {
            author = presenter.getLoggedUserEmail();
        }

        String authorImageUrl = "https://firebasestorage.googleapis.com/v0/b/whatrecipes.appspot.com/o/user_not_registered.jpg?alt=media&token=26007317-d9f6-43db-82e7-e8767a8ccede";

        if(!Validator.stringEmptyOrNull( presenter.getLoggedUserImageUrl())){
            authorImageUrl = presenter.getLoggedUserImageUrl();
        }


        presenter.saveRecipeToFirebaseDb(recipeTitle, recipeSummary, ingredientsName, ingredientsQuantity, cookingTime, encodedBitmap, howToPrepare, servings, Arrays.asList(tags), author,authorImageUrl);


        return true;
    }

    @OnClick(R.id.cancel_recipe)
    public void handleCancelButtonClick() {
        ActivityUtils.replaceFragmentToActivity(getFragmentManager(), new RecipesStackFragment(), R.id.cardStackFragment);
    }

    @OnClick(R.id.camera_button)
    public void takeCameraPhoto() {
        CameraUtils.takeRecipeCameraPhoto(this);
    }

    @OnClick(R.id.add_ingredient_field_button)
    public void handleAddNewIngredientForm() {

        //TODO refactor
        View view = getView();
        LinearLayout newLayout = this.addIngridientFormView();
        this.ingredientsList.add(newLayout);
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.ingredient_framelayout);
        ll.addView(newLayout);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        // TODO refactor this!
        if (resultCode != 0) {
            if(requestCode==1338){
                Uri rootPath = data.getData();
                this.RecipeThumbnail = BitmapFactory.decodeFile(rootPath.getPath());

                //Preview photo
                imageView.setImageBitmap(RecipeThumbnail);
                presenter.uploadImageToStorage(getActivity(), ImageHelper.getImageByteArray(RecipeThumbnail));
            }
        }

    }


    @Override
    public LinearLayout addIngridientFormView() {
        //TODO refactor
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams2.weight = 1;
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams3.weight = 3;

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(layoutParams);

        LinearLayout layoutChild1 = new LinearLayout(getContext());
        layoutChild1.setOrientation(LinearLayout.VERTICAL);
        layoutChild1.setLayoutParams(layoutParams2);

        LinearLayout layoutChild2 = new LinearLayout(getContext());
        layoutChild2.setOrientation(LinearLayout.VERTICAL);
        layoutChild2.setLayoutParams(layoutParams3);

        TextInputLayout tv1 = new TextInputLayout(getContext());

        EditText ingredientEditText = new EditText(getContext());
        ingredientEditText.setTag(TAG_INGREDIENT_NAME);
        ingredientEditText.setLayoutParams(layoutParams);
        ingredientEditText.setHint("Ingredient");
        tv1.addView(ingredientEditText);

        TextInputLayout tv2 = new TextInputLayout(getContext());

        EditText ingredientEditTextQuantity = new EditText(getContext());
        ingredientEditTextQuantity.setTag(TAG_INGREDIENT_QUANITY);
        ingredientEditTextQuantity.setLayoutParams(layoutParams);
        ingredientEditTextQuantity.setHint("Quantity");
        tv2.addView(ingredientEditTextQuantity);

        layoutChild1.addView(tv1);
        layoutChild2.addView(tv2);

        layout.addView(layoutChild1);
        layout.addView(layoutChild2);

        return layout;
    }


    @Override
    public void showOnSuccessfulUploadToast() {

    }

    @Override
    public void showFailedUploadToast() {

    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }
}
