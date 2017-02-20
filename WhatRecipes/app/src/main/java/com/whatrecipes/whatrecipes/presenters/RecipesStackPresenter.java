package com.whatrecipes.whatrecipes.presenters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.data.Recipe;
import com.whatrecipes.whatrecipes.data.firebase.FirebaseDatabaseInteractor;
import com.whatrecipes.whatrecipes.data.firebase.IFirebaseDatabaseInteractor;
import com.whatrecipes.whatrecipes.utils.RecipeViewUtils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by dnt on 13.2.2017 г..
 */

public class RecipesStackPresenter implements IPresenter.RecipeStackPresenter {
    private IView.RecipeStackView mView;
    private final IFirebaseDatabaseInteractor db;

    @Inject
    public RecipesStackPresenter(IFirebaseDatabaseInteractor db) {
        this.db = db;
    }

    @Override
    public void loadRecipesStack() {
        db.getAllRecipes(bindRecipeListener());

    }

    private ChildEventListener bindRecipeListener() {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    Recipe model = dataSnapshot.getValue(Recipe.class);
                    if (model != null) {
                        String bitmapR = model.getEncodedImage();
                        model.setBitmap(RecipeViewUtils.getEncodedImage(bitmapR));
                        mView.addRecipeToAdapter(model);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }


    private ArrayList<Recipe> collectStackRecipes(Map<String, Object> recipes) {
        ArrayList<Recipe> recipesStack = new ArrayList<>();

        for (Map.Entry<String,Object> entry: recipes.entrySet()) {
//            recipesStack.add(entry.getValue());
        }

        return recipesStack;
    }

    @Override
    public void setView(IView.RecipeStackView view) {
        this.mView = view;
    }
}
