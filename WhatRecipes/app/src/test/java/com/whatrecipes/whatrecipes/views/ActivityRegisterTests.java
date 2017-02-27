package com.whatrecipes.whatrecipes.views;

import android.test.ActivityInstrumentationTestCase2;

import com.whatrecipes.whatrecipes.App;
import com.whatrecipes.whatrecipes.AppComponent;
import com.whatrecipes.whatrecipes.AppModule;
import com.whatrecipes.whatrecipes.view.ActivityRegister;
import com.whatrecipes.whatrecipes.view.MainActivity;

import org.junit.Rule;

import it.cosenonjaviste.daggermock.DaggerMockRule;

/**
 * Created by fatal on 26.2.2017 г..
 */

public class ActivityRegisterTests extends ActivityInstrumentationTestCase2<ActivityRegister> {
    public ActivityRegisterTests() {
        super(ActivityRegister.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void test_correct_data(){

    }
}
