package com.whatrecipes.whatrecipes.view;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.whatrecipes.whatrecipes.R;
import com.whatrecipes.whatrecipes.view.fragments.RegisterUserFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityRegister extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setSupportActionBar(mToolbar);

        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            initFragment();
        }
    }

    private void initFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.register_activity_frame_layout, new RegisterUserFragment())
                .commit();
    }
}
