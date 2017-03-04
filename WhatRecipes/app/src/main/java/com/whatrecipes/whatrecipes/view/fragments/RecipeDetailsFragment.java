package com.whatrecipes.whatrecipes.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.whatrecipes.whatrecipes.App;
import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.data.IngredientsAdapter;
import com.whatrecipes.whatrecipes.data.Recipe;
import com.whatrecipes.whatrecipes.presenters.LogInPresenter;
import com.whatrecipes.whatrecipes.presenters.RecipeDetailsPresenter;
import com.whatrecipes.whatrecipes.utils.CircleTransform;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dnt on 13.2.2017 г..
 */

public class RecipeDetailsFragment extends Fragment implements IView.RecipeDetailsView {
    private String uid;
    private Recipe recipe;

    @Inject
    RecipeDetailsPresenter presenter;

    @BindView(R.id.recipeImageView)
    ImageView recipeImage;

    @BindView(R.id.EditTextRecipeTextTitle)
    TextView tvTitle;

    @BindView(R.id.TextViewRecipeCookingTime)
    TextView etCookingTime;

    @BindView(R.id.textViewAuthor)
    TextView tvAuthor;

    @BindView(R.id.textViewRecipeSummary)
    TextView tvRecipeSummary;

    @BindView(R.id.textViewAuthorProfileImage)
    ImageView ivAuthorProfileImage;

    @BindView(R.id.TextViewRecipeServings)
    TextView tvRecipeServings;

    @BindView(R.id.TextViewRecipeLoves)
    TextView tvRecipeLoves;

    @BindView(R.id.recycler_view_ingredients_list)
    RecyclerView rvIngredients;

    @BindView(R.id.textViewRecipeInstructions)
    TextView tvHoToPrepare;

    @BindView(R.id.viewSwitcher)
    ViewSwitcher viewSwitcher;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.viewSwitcherButton2)
    Button buttonSwitch;


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        App.get().component().inject(this);

        presenter.setView(this);

        ButterKnife.bind(this, view);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvIngredients.setLayoutManager(mLayoutManager);

        Gson gson = new Gson();
        this.recipe = gson.fromJson(getActivity().getIntent().getStringExtra("Recipe"), Recipe.class);
        this.showRecipe(recipe);

        //setup animation

        Animation in = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right);

        // set the animation type to ViewSwitcher
        viewSwitcher.setInAnimation(in);
        viewSwitcher.setOutAnimation(out);


        return view;
    }

    @OnClick(R.id.TextViewRecipeLoves)
    public void onLoveClicked() {
        presenter.updateLoves(this.recipe);
    }

    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public void showRecipe(Recipe recipe) {
        this.uid = recipe.getUid();

        String text = recipe.getName();
        tvTitle.setText(text);

        Picasso.with(getContext()).setLoggingEnabled(true);
        String imageUrl = recipe.getImageUrl();

        Picasso.with(getContext()).load(recipe.getImageUrl()).into(recipeImage);
        recipeImage.setScaleType(ImageView.ScaleType.FIT_XY);
        // Glide.with(getContext()).load(recipe.getImageUrl()).into(recipeImage);

        String author = recipe.getAuthor();
        tvAuthor.setText(author);

        mProgressBar.setVisibility(View.VISIBLE);
        Picasso.with(getContext())
                .load(recipe.getAuthorImageUrl())
                .transform(new CircleTransform())
                .into(ivAuthorProfileImage, new Callback() {

                    @Override
                    public void onSuccess() {
                        // TODO Auto-generated method stub
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        // TODO Auto-generated method stub

                    }
                });

        String recipeSummary = recipe.getRecipeSummary();
        if (recipeSummary.length() > 250) {
            recipeSummary = recipeSummary.substring(0, 200);
        }
        tvRecipeSummary.setText(recipeSummary);

        String cookingTime = Integer.toString(recipe.getCookingTime());
        etCookingTime.setText(cookingTime);

        Integer servings = recipe.getServings();
        tvRecipeServings.setText(servings.toString());

        Integer loves = 0;
        try {

            loves = recipe.getLovedBy().size();
        } catch (NullPointerException ex) {
            // handle exception
        }


        tvRecipeLoves.setText(loves.toString());

        String howToPrep = recipe.getStepsToPrepare();

        tvHoToPrepare.setText(howToPrep);


        List<String> ingredientsName = recipe.getIngredientsName();
        List<String> ingredientsQuantity = recipe.getIngredientsQuantity();

        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(getContext(), ingredientsName, ingredientsQuantity);
        rvIngredients.setAdapter(ingredientsAdapter);

        ///TODO
        List<String> tags = recipe.getTags();
    }

    @OnClick(R.id.viewSwitcherButton2)
    public void onClickViewSwitchButton() {
        if (buttonSwitch.getText() == "View instructions")
            this.buttonSwitch.setText("View ingredients");
        else
            this.buttonSwitch.setText("View instructions");
        this.viewSwitcher.showNext();
    }

    @Override
    public void showYouMustBeLogged() {
        Toast.makeText(getContext(), "You must be logged to do that", Toast.LENGTH_SHORT).show();
    }
}
