package com.whatrecipes.whatrecipes.presenters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.whatrecipes.whatrecipes.IPresenter;
import com.whatrecipes.whatrecipes.IView;
import com.whatrecipes.whatrecipes.data.Recipe;
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
    private final FirebaseDatabase db;

    @Inject
    public RecipesStackPresenter(FirebaseDatabase db, IView.RecipeStackView mView) {
        this.db = db;
        this.mView = mView;
    }

    @Override
    public void loadRecipesStack() {
        DatabaseReference ref = db.getReference().child("recipes");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Recipe> recipesStack = new ArrayList<>();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                    Recipe recipe = dsp.getValue(Recipe.class);
                    String bitmapR = recipe.getEncodedImage();
                    recipe.setBitmap(RecipeViewUtils.getEncodedImage(bitmapR));
                    recipesStack.add(recipe);
                }



                mView.showRecipesStack(recipesStack);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    private ArrayList<Recipe> collectStackRecipes(Map<String, Object> recipes) {
        ArrayList<Recipe> recipesStack = new ArrayList<>();

        for (Map.Entry<String,Object> entry: recipes.entrySet()) {
//            recipesStack.add(entry.getValue());
        }

        return recipesStack;
    }
}
