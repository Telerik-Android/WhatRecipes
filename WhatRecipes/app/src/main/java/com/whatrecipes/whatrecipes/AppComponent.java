package com.whatrecipes.whatrecipes;

import com.whatrecipes.whatrecipes.presenters.PresentationModule;
import com.whatrecipes.whatrecipes.view.MainActivity;
import com.whatrecipes.whatrecipes.view.fragments.AddNewRecipeFragment;
import com.whatrecipes.whatrecipes.view.fragments.RecipesStackFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by dnt on 12.2.2017 г..
 */
@Singleton
@Component(modules = {AppModule.class, PresentationModule.class})
public interface AppComponent {
    void inject(MainActivity activity);
    void inject(RecipesStackFragment fragment);
    void inject(AddNewRecipeFragment fragment);
}
