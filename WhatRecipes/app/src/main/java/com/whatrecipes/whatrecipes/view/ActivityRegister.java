package com.whatrecipes.whatrecipes.view;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.utils.ActivityUtils;
import com.whatrecipes.whatrecipes.view.fragments.RegisterUserFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (savedInstanceState == null) {
            initFragment();
        }
    }

    private void initFragment() {
        ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(),new RegisterUserFragment(),R.id.register_activity_frame_layout);
    }
}
