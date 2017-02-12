package com.whatrecipes.whatrecipes.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.utils.ActivityUtils;

public class MainActivity extends AppCompatActivity {
    DrawerFragment drawerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerFragment = new DrawerFragment();

        ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(),drawerFragment,R.id.drawerFragment);

    }
}
