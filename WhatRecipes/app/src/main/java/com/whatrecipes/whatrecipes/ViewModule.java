package com.whatrecipes.whatrecipes;

import com.whatrecipes.whatrecipes.utils.CustomScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dnt on 14.2.2017 г..
 */
@Module
public class ViewModule {

    //private IView.RecipeStackView mRecipeStackView;
    private IView.AddNewRecipeView mAddNewRecipe;

//    public ViewModule(IView.RecipeStackView mRecipeStackView){
//        this.mRecipeStackView = mRecipeStackView;
//    }

    public ViewModule(IView.AddNewRecipeView mAddNewRecipe){
        this.mAddNewRecipe = mAddNewRecipe;
    }

//    @Provides
//    @CustomScope
//    IView.RecipeStackView providesRecipeStackView() {
//        return mRecipeStackView;
//    }

    @Provides
    @CustomScope
    IView.AddNewRecipeView providesAddNewRecipeView() {
        return mAddNewRecipe;
    }
}
