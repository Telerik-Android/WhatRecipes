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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
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

/**
 * Created by dnt on 13.2.2017 г..
 */

public class RecipeDetailsFragment extends Fragment implements IView.RecipeDetailsView {
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
        Recipe recipe = gson.fromJson(getActivity().getIntent().getStringExtra("Recipe"), Recipe.class);
        this.showRecipe(recipe);

        return view;
    }

    @Override
    public void showRecipe(Recipe recipe) {
        String text = recipe.getName();
        tvTitle.setText(text);

        String imageUrl = recipe.getImageUrl();
        Picasso.with(getContext()).load(recipe.getImageUrl()).into(recipeImage);


        String author = recipe.getAuthor();
        tvAuthor.setText(author);

        Picasso.with(getContext()).load(recipe.getAuthorImageUrl()).transform(new CircleTransform()).into(ivAuthorProfileImage);

        String recipeSummary =recipe.getRecipeSummary();
        if (recipeSummary.length() > 250) {
            recipeSummary = recipeSummary.substring(0, 200);
        }
        tvRecipeSummary.setText(recipeSummary);

        String cookingTime = Integer.toString(recipe.getCookingTime());
        etCookingTime.setText(cookingTime);

        Integer servings = recipe.getServings();
        tvRecipeServings.setText(servings.toString());

        Integer loves = recipe.getLoves();
        if (loves == null)
            loves = 0;

        tvRecipeLoves.setText(loves.toString());

        List<String> ingredientsName = recipe.getIngredientsName();
        List<String> ingredientsQuantity = recipe.getIngredientsQuantity();

        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(getContext(),ingredientsName,ingredientsQuantity);
        rvIngredients.setAdapter(ingredientsAdapter);

        ///TODO
        List<String> tags = recipe.getTags();
    }
}
