package com.whatrecipes.whatrecipes.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.utils.ActivityUtils;
import com.whatrecipes.whatrecipes.view.fragments.AddUserProfileImageFragment;
import com.whatrecipes.whatrecipes.view.fragments.LogInFragment;

/**
 * Created by fatal on 24.2.2017 г..
 */

public class ActivityAddUserImage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_image);

        if (savedInstanceState == null) {
            initFragment();
        }
    }

    private void initFragment() {
        ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), new AddUserProfileImageFragment(), R.id.add_user_image_activity_frame_layout);
    }
}
