package com.whatrecipes.whatrecipes.view;

import com.whatrecipes.whatrecipes.IView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fatal on 2/16/2017.
 */

@Module
public class MainScreenModule {
    public IView.RecipeStackView mRecipeStackView;
    public IView.AddNewRecipeView mAddNewRecipeView;

    public MainScreenModule(IView.AddNewRecipeView mAddNewRecipeView){
        this.mAddNewRecipeView = mAddNewRecipeView;
    }

    public MainScreenModule(IView.RecipeStackView mRecipeStackView){
        this.mRecipeStackView = mRecipeStackView;
    }

    @Provides
    public IView.AddNewRecipeView providesAddNewRecipeView(){return mAddNewRecipeView;}

    @Provides
    public IView.RecipeStackView providesRecipeStackView(){return mRecipeStackView;}
}