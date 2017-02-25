package com.whatrecipes.whatrecipes;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.whatrecipes.whatrecipes.view.ActivityLogIn;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by fatal on 25.2.2017 г..
 */
@RunWith(AndroidJUnit4.class)
public class ActivityLoginTests {

    @Rule
    public ActivityTestRule<ActivityLogIn> mActivityLoginTestRule =
            new ActivityTestRule<ActivityLogIn>(ActivityLogIn.class);

    @Test
    public void clickLogInButton_passesCorrectUserNameAndPassword() throws Exception{
//        onView(withId(R.id.button_log_in))
//                .perform(click());
//        onView(withId(R.id.material))
//                .check();
    }
}
