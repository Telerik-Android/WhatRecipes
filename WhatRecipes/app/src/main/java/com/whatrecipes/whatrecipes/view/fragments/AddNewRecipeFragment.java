package com.whatrecipes.whatrecipes.view.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.whatrecipes.whatrecipes.App;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.data.Recipe;
import com.whatrecipes.whatrecipes.presenters.AddNewRecipePresenter;
import com.whatrecipes.whatrecipes.utils.ActivityUtils;
import com.whatrecipes.whatrecipes.utils.CameraUtils;
import com.whatrecipes.whatrecipes.utils.RecipeViewUtils;
import com.whatrecipes.whatrecipes.utils.Validator;
import com.whatrecipes.whatrecipes.view.DaggerMainScreenComponent;
import com.whatrecipes.whatrecipes.view.MainScreenModule;

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

    private View view;

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

    @Inject
    AddNewRecipePresenter presenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_new_recipe, container, false);


        this.ingredientsList = new ArrayList<>();

        DaggerMainScreenComponent.builder()
                .firebaseComponent(((App) getActivity().getApplicationContext()).getFirebaseComponent())
                .mainScreenModule(new MainScreenModule(this))
                .build()
                .inject(this);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.submit_recipe)
    public void handleSubmitButtonClick() {

        //parse recipe from form
        Recipe recipeToSubmit = parseRecipeForm();

        //if input is incorrect stop event
        if(recipeToSubmit==null)
        {
            return;
        }
        //save to database
        presenter.saveRecipeToFirebaseDb(recipeToSubmit);

        //change screen
        ActivityUtils.replaceFragmentToActivity(getFragmentManager(), new RecipesStackFragment(), R.id.cardStackFragment);
    }

    public Recipe parseRecipeForm() {
        View v = this.getView();

        EditText edrecipeTitle = (EditText) v.findViewById(R.id.EditTextRecipeTextTitle);
        if(!Validator.validateRequiredEditTextField(edrecipeTitle,getString(R.string.fieldRequired)))
        { return null;}
        String recipeTitle = edrecipeTitle.getText().toString();


        EditText edrecipeSummary = (EditText) v.findViewById(R.id.EditTextRecipeTextSummary);
        if(!Validator.validateRequiredEditTextField(edrecipeSummary,getString(R.string.fieldRequired)))
        { return null;}
        String recipeSummary = edrecipeSummary.getText().toString();

        ViewGroup parent = (ViewGroup) getView().getParent();
        List<View> ingredientsName = RecipeViewUtils.findViewWithTagRecursively(parent, TAG_INGREDIENT_NAME);
        List<View> ingredientsQuantity = RecipeViewUtils.findViewWithTagRecursively(parent, TAG_INGREDIENT_QUANITY);
        Map<String, String> ingredients = RecipeViewUtils.parseIngredientsByViews(ingredientsName, ingredientsQuantity);

        EditText edservings = (EditText) v.findViewById(R.id.EditTextRecipeServings);
        if(!Validator.validateRequiredEditTextField(edservings,getString(R.string.fieldRequired)))
        { return null;}
        Integer servings = Integer.valueOf(edservings.getText().toString());


        EditText edcookingTime = (EditText) v.findViewById(R.id.EditTextRecipeCookingTime);
        if(!Validator.validateRequiredEditTextField(edcookingTime,getString(R.string.fieldRequired)))
        { return null;}
        Integer cookingTime = Integer.valueOf(edcookingTime.getText().toString());

        EditText edhowToPrepare = (EditText) v.findViewById(R.id.EditTextRecipeHowToPrepare);
        if(!Validator.validateRequiredEditTextField(edhowToPrepare,getString(R.string.fieldRequired)))
        { return null;}
        String howToPrepare = edhowToPrepare.getText().toString();

        EditText edtagsToSplit = (EditText) v.findViewById(R.id.EditTextRecipeTags);
        if(!Validator.validateRequiredEditTextField(edtagsToSplit,getString(R.string.fieldRequired)))
        { return null;}
        String tagsToSplit = edtagsToSplit.getText().toString();

        tagsToSplit.replaceAll(";", " ");
        tagsToSplit.replaceAll(",", " ");
        String[] tags = tagsToSplit.split(" ");
        if(this.RecipeThumbnail==null){
            Toast.makeText(getActivity(), "Image is required", Toast.LENGTH_LONG).show();
            return null;
        }
        String encodedBitmap = RecipeViewUtils.setEncodedImage(this.RecipeThumbnail);

        String author = "Not implemented";



        //TODO Create factory and replace instantiation here
        Recipe recipeToSubmit = new Recipe(recipeTitle, recipeSummary, ingredients, cookingTime, encodedBitmap, howToPrepare, servings, Arrays.asList(tags), author);

        return recipeToSubmit;
    }

    @OnClick(R.id.cancel_recipe)
    public void handleCancelButtonClick() {
        ActivityUtils.replaceFragmentToActivity(getFragmentManager(), new RecipesStackFragment(), R.id.cardStackFragment);
    }

    @OnClick(R.id.camera_button)
    public void takeCameraPhoto() {
        CameraUtils.takeCameraPhoto(this);
    }

    @OnClick(R.id.add_ingredient_field_button)
    public void handleAddNewIngredientForm() {
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
        if(resultCode==0){
            return;
        }
        Uri rootPath = data.getData();
        this.RecipeThumbnail = BitmapFactory.decodeFile(rootPath.getPath());

        //Preview photo
        imageView.setImageBitmap(RecipeThumbnail);
    }

    @Override
    public void showAddRecipeForm() {

    }

    @Override
    public LinearLayout addIngridientFormView() {
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

        TextView tv1 = new TextView(getContext());
        tv1.setText("Ingredient:");

        EditText ingredientEditText = new EditText(getContext());
        ingredientEditText.setTag(TAG_INGREDIENT_NAME);
        ingredientEditText.setLayoutParams(layoutParams);
        ingredientEditText.setHint("Ingredient");

        TextView tv2 = new TextView(getContext());
        tv2.setText("Quantity:");

        EditText ingredientEditTextQuantity = new EditText(getContext());
        ingredientEditTextQuantity.setTag(TAG_INGREDIENT_QUANITY);
        ingredientEditTextQuantity.setLayoutParams(layoutParams);
        ingredientEditTextQuantity.setHint("Quantity");

        layoutChild1.addView(tv1);
        layoutChild1.addView(ingredientEditText);
        layoutChild2.addView(tv2);
        layoutChild2.addView(ingredientEditTextQuantity);

        layout.addView(layoutChild1);
        layout.addView(layoutChild2);

        return layout;
    }

}
