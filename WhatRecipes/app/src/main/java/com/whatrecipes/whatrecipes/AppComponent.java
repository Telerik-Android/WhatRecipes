package com.whatrecipes.whatrecipes;

import com.whatrecipes.whatrecipes.presenters.PresenterModule;
import com.whatrecipes.whatrecipes.view.MainActivity;
import com.whatrecipes.whatrecipes.view.fragments.AddNewRecipeFragment;
import com.whatrecipes.whatrecipes.view.fragments.RecipesStackFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by dnt on 12.2.2017 г..
 */
@Singleton
@Component(modules = {AppModule.class, PresenterModule.class})
public interface AppComponent {
    void inject(App app);

    void inject(MainActivity activity);

    void inject(AddNewRecipeFragment fragment);

    void inject(RecipesStackFragment fragment);
}
