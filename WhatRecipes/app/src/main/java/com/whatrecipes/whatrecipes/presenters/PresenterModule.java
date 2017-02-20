package com.whatrecipes.whatrecipes.presenters;

import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.data.firebase.FirebaseDatabaseInteractor;
import com.whatrecipes.whatrecipes.data.firebase.FirebaseModule;
import com.whatrecipes.whatrecipes.view.fragments.RecipesStackFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fatal on 2/20/2017.
 */
@Module(includes = FirebaseModule.class)
public class PresenterModule {

    @Provides
    public IPresenter.AddRecipePresenter providesAddNewRecipePresenter(FirebaseDatabaseInteractor firebaseDatabaseInteractor){
        return new AddNewRecipePresenter(firebaseDatabaseInteractor);
    }

    @Provides
    public IPresenter.RecipeStackPresenter providesRecipeStackView(FirebaseDatabaseInteractor firebaseDatabaseInteractor){
        return new RecipesStackPresenter(firebaseDatabaseInteractor);
    }
}

