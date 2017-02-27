package com.whatrecipes.whatrecipes;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.whatrecipes.whatrecipes.utils.ActivityUtils;
import com.whatrecipes.whatrecipes.view.ActivityLogIn;
import com.whatrecipes.whatrecipes.view.fragments.LogInFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by fatal on 26.2.2017 г..
 */
//@RunWith(AndroidJUnit4.class)
//public class FragmentTests {
//    private LogInFragment fragment;
//
//    @Rule
//    public ActivityTestRule<ActivityLogIn> activity = new ActivityTestRule<>(ActivityLogIn.class,false,true);
//
//    @Before
//    public void setup() {
//        Espresso.registerIdlingResources()
//    }
//
//    @Test
//    public void aTest() {
//        String empty = "Hello, world!";
//        TextView textView = (TextView)fragment.getView().findViewById(R.id.test);
//        assertTrue("Empty stuff",(textView.getText().equals(empty)));
//    }
//
//    @Test
//    public void bTest() {
//        String empty = "Hello, world!";
//        TextView textView = (TextView)fragment.getView().findViewById(R.id.test);
//        assertTrue("Empty stuff",(textView.getText().equals(empty)));
//    }
//}
