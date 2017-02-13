package com.whatrecipes.whatrecipes.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.*;
import android.view.View;

import com.wenchao.cardstack.CardStack;
import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.data.RecipeAdapter;

import java.util.EventListener;

/**
 * Created by dnt on 13.2.2017 г..
 */

public class RecipesStackFragment extends Fragment {
    private CardStack mCardStack;
    private RecipeAdapter mCardAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_stack,container,false);

        mCardStack = (CardStack)view.findViewById(R.id.container);

        mCardStack.setContentResource(R.layout.recipe_item_card_view);

        mCardAdapter = new RecipeAdapter(getContext(),R.layout.recipe_item_card_view);
        mCardAdapter.add("test1");
        mCardAdapter.add("test2");
        mCardAdapter.add("test3");
        mCardAdapter.add("test4");
        mCardAdapter.add("test5");

        mCardStack.setAdapter(mCardAdapter);


        return view;
    }
}
