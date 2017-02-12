package com.whatrecipes.whatrecipes.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.utils.ActivityUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),new BlankFragment(),R.id.myFragment);


    }
}
