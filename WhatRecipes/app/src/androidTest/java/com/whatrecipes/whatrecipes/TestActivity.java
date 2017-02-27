package com.whatrecipes.whatrecipes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.test.espresso.core.deps.guava.annotations.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by fatal on 26.2.2017 г..
 */
@VisibleForTesting
public class TestActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout view = new LinearLayout(this);
        view.setId(R.id.container);

        setContentView(view);
    }
}
