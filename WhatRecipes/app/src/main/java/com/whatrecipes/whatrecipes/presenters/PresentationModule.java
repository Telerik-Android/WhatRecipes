package com.whatrecipes.whatrecipes.presenters;

import com.google.firebase.database.FirebaseDatabase;
import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.data.FirebaseModule;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dnt on 12.2.2017 г..
 */

@Module(includes = FirebaseModule.class)
public class PresentationModule {

    @Provides
    public RecipesStackPresenter provideRecipeStackPresenter(FirebaseDatabase db) {
        return new RecipesStackPresenter(db);
    }

    @Provides
    public AddNewRecipePresenter provideAddNewRecipePresenter(FirebaseDatabase db){
        return new AddNewRecipePresenter(db);
    }
}