package com.whatrecipes.whatrecipes.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.utils.ActivityUtils;
import com.whatrecipes.whatrecipes.view.fragments.RecipeDetailsFragment;

/**
 * Created by fatal on 28.2.2017 г..
 */

public class ActivityRecipeDetails extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        if (savedInstanceState == null) {
            initFragment();
        }
    }

    private void initFragment() {
        ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), new RecipeDetailsFragment(), R.id.recipe_details_activity_frame);
    }
}
