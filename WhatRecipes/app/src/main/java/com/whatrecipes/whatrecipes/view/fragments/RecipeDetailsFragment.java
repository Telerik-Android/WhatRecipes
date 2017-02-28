package com.whatrecipes.whatrecipes.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.whatrecipes.whatrecipes.App;
import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.data.Recipe;
import com.whatrecipes.whatrecipes.presenters.LogInPresenter;
import com.whatrecipes.whatrecipes.presenters.RecipeDetailsPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dnt on 13.2.2017 г..
 */

public class RecipeDetailsFragment extends Fragment implements IView.RecipeDetailsView{
    @Inject
    RecipeDetailsPresenter presenter;

    @BindView(R.id.recipeName)
    TextView recipeNameText;

    @BindView(R.id.recipeAuthor)
    TextView recipeAuthorText;

    @BindView(R.id.recipeCookingTime)
    TextView recipeCookingTimeText;

    @BindView(R.id.recipeSteps)
    TextView recipeStepsText;

    @BindView(R.id.recipeSummary)
    TextView recipeSummaryText;

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
        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(getActivity().getIntent().getStringExtra("Recipe"), Recipe.class);
        this.showRecipe(recipe);

        return view;
    }

    @Override
    public void showRecipe(Recipe recipe) {
        String name = recipe.getName();
        String author = recipe.getAuthor();
        int cookingTime = recipe.getCookingTime();
        String steps = recipe.getStepsToPrepare();
        String summary = recipe.getRecipeSummary();

        if (name != null && !name.isEmpty()) {
            this.recipeNameText.setText(name);
        }

        if (author != null && !author.isEmpty()) {
            this.recipeAuthorText.setText(String.format("By: %s", author));
        }

        if (steps != null && !steps.isEmpty()) {
            this.recipeStepsText.setText(String.format("Steps:\n %s", steps));
        }

        if (summary != null && !summary.isEmpty()) {
            this.recipeSummaryText.setText(String.format("Summary:\n %s", summary));
        }

        this.recipeCookingTimeText.setText(String.format("Time to cook: %s", cookingTime));
    }
}
