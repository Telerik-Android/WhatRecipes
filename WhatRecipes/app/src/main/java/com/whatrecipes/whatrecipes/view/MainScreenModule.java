package com.whatrecipes.whatrecipes.view;

import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.data.firebase.FirebaseDatabaseInteractor;
import com.whatrecipes.whatrecipes.data.firebase.FirebaseModule;
import com.whatrecipes.whatrecipes.data.firebase.IFirebaseDatabaseInteractor;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fatal on 2/16/2017.
 */
//
//@Module(includes = FirebaseModule.class)
//public class MainScreenModule {
//    public IView.RecipeStackView mRecipeStackView;
//    public IView.AddNewRecipeView mAddNewRecipeView;
//    public IFirebaseDatabaseInteractor mFirebaseInteractor;
//
//    public MainScreenModule(IFirebaseDatabaseInteractor mFirebaseInteractor) {
//        this.mAddNewRecipeView = mAddNewRecipeView;
//        this.mFirebaseInteractor = mFirebaseInteractor;
//    }
//
//    public MainScreenModule() {
//        this.mRecipeStackView = mRecipeStackView;
//    }
//
//
//    @Provides
//    public IView.AddNewRecipeView providesAddNewRecipeView() {
//        return mAddNewRecipeView;
//    }
//
//    @Provides
//    public IView.RecipeStackView providesRecipeStackView() {
//        return mRecipeStackView;
//    }
//
//}