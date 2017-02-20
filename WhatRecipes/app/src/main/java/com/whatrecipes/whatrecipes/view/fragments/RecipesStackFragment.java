package com.whatrecipes.whatrecipes.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.*;
import android.view.View;

import com.wenchao.cardstack.CardStack;
import com.whatrecipes.whatrecipes.App;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.data.Recipe;
import com.whatrecipes.whatrecipes.data.RecipeAdapter;
import com.whatrecipes.whatrecipes.presenters.RecipesStackPresenter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dnt on 13.2.2017 г..
 */

public class RecipesStackFragment extends Fragment implements IView.RecipeStackView {

    @BindView(R.id.container)
    public CardStack mCardStack;

    private RecipeAdapter mCardAdapter;

    @Inject
    RecipesStackPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_stack,container,false);

        App.get().component().inject(this);
        presenter.setView(this);

        ButterKnife.bind(this,view);
        mCardAdapter = new RecipeAdapter(getContext(),R.layout.recipe_item_card_view);
        mCardStack.setContentResource(R.layout.recipe_item_card_view);
        mCardStack.setAdapter(mCardAdapter);
        presenter.loadRecipesStack();

        return view;
    }


    @Deprecated
    @Override
    public void showRecipesStack(ArrayList<Recipe> recipes) {

        mCardAdapter = new RecipeAdapter(getContext(),R.layout.recipe_item_card_view);
        mCardStack.setContentResource(R.layout.recipe_item_card_view);

        for(Recipe recipe: recipes){
            mCardAdapter.add(recipe);
        }

        mCardStack.setAdapter(mCardAdapter);
    }

    @Override
    public void addRecipeToAdapter(Recipe recipe) {
        mCardAdapter.add(recipe);
    }
}
