package com.whatrecipes.whatrecipes.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.whatrecipes.whatrecipes.App;
import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.data.Recipe;
import com.whatrecipes.whatrecipes.presenters.LogInPresenter;
import com.whatrecipes.whatrecipes.presenters.RecipeDetailsPresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by dnt on 13.2.2017 г..
 */

public class RecipeDetailsFragment extends Fragment implements IView.RecipeDetailsView{
    @Inject
    RecipeDetailsPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        App.get().component().inject(this);

        presenter.setView(this);

        ButterKnife.bind(this, view);
        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(getActivity().getIntent().getStringExtra("Recipe"),Recipe.class);
        Integer b = 5;
        return view;
    }

    @Override
    public void showRecipe(Recipe recipe) {

    }
}
