package com.whatrecipes.whatrecipes;

import com.whatrecipes.whatrecipes.data.firebase.FirebaseAuthenticationInteractor;
import com.whatrecipes.whatrecipes.presenters.AddUserProfileImagePresenter;
import com.whatrecipes.whatrecipes.presenters.PresenterModule;
import com.whatrecipes.whatrecipes.view.AI.ActivityImageClassifier;
import com.whatrecipes.whatrecipes.view.ActivityLogIn;
import com.whatrecipes.whatrecipes.view.ActivityRecipeDetails;
import com.whatrecipes.whatrecipes.view.ActivityRegister;
import com.whatrecipes.whatrecipes.view.MainActivity;
import com.whatrecipes.whatrecipes.view.fragments.AddNewRecipeFragment;
import com.whatrecipes.whatrecipes.view.fragments.AddUserProfileImageFragment;
import com.whatrecipes.whatrecipes.view.fragments.DrawerFragment;
import com.whatrecipes.whatrecipes.view.fragments.LogInFragment;
import com.whatrecipes.whatrecipes.view.fragments.RecipeDetailsFragment;
import com.whatrecipes.whatrecipes.view.fragments.RecipesStackFragment;
import com.whatrecipes.whatrecipes.view.fragments.RegisterUserFragment;

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

    void inject(DrawerFragment fragment);

    void inject(ActivityRegister activity);

    void inject(RegisterUserFragment fragment);

    void inject(ActivityLogIn activity);

    void inject(LogInFragment fragment);

    void inject(AddUserProfileImageFragment fragment);

    void inject(RecipeDetailsFragment fragment);

    void inject(ActivityRecipeDetails activity);

    void inject(ActivityImageClassifier activity);
}
