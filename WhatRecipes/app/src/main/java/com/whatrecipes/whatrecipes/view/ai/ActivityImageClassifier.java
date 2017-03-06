package com.whatrecipes.whatrecipes.view.ai;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.utils.ActivityUtils;

public class ActivityImageClassifier extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_classifier);

        if (savedInstanceState == null) {
            initFragment();
        }
    }

    private void initFragment() {
        ActivityUtils.replaceFragmentToActivity(
                getSupportFragmentManager(),
                new ImageClassifierFragment(),
                R.id.image_classifier_activity_frame);
    }
}
