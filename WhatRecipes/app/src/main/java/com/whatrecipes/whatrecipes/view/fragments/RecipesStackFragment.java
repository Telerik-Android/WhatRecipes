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
import com.whatrecipes.whatrecipes.data.RecipeAdapter;
import com.whatrecipes.whatrecipes.presenters.RecipesStackPresenter;

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

        App.get().getAppComponent().inject(this);

        ButterKnife.bind(this,view);

        mCardStack.setContentResource(R.layout.recipe_item_card_view);
        mCardAdapter = new RecipeAdapter(getContext(),R.layout.recipe_item_card_view);
        mCardAdapter.add("test1");
        mCardAdapter.add("test2");
        mCardAdapter.add("test3");
        mCardAdapter.add("test4");
        mCardAdapter.add("test5");
        presenter.loadRecipesStack();
        mCardStack.setAdapter(mCardAdapter);

        return view;
    }

    @Override
    public void showRecipesStack(RecipeAdapter recipes) {
        //mCardAdapter.setAdapter(recipes);
    }
}
