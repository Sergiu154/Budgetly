package com.example.myapplication;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.*;


@RunWith(AndroidJUnit4.class)
public class LoginScreenTest {

    @Rule
    public ActivityTestRule<LoginActivity> mLoginActivityTestRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void makeLoginAction() throws Exception {
        final String emailTest = "test2@test.com";
        final String passTest = "test12";

        // scriu datele de login

        onView(withId(R.id.emailLogin))
                .perform(ViewActions.click())
                .perform(ViewActions.typeTextIntoFocusedView(emailTest));

        onView(withId(R.id.passwordLogin))
                .perform(ViewActions.click())
                .perform(ViewActions.typeTextIntoFocusedView(passTest));

        // inchid tastatura
        closeSoftKeyboard();

        // dau login
        onView(withId(R.id.registerButton)).perform(ViewActions.click());
    }
}
