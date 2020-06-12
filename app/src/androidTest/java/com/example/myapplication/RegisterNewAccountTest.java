package com.example.myapplication;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;


import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Assert.*;
import org.junit.runners.JUnit4;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import androidx.test.espresso.action.ViewActions.*;


public class RegisterNewAccountTest {

    @Rule
    public ActivityTestRule<RegisterActivity> mRegisterActivityTestRule =
            new ActivityTestRule<>(RegisterActivity.class);

    @Test
    public void clickRegisterButton() throws Exception {
        String fakeEmail = "generatedTest4@test.com";
        String fakePass = "fakePass1";

        // introduc mail pt cont
        onView(withId(R.id.emailLogin)).perform(ViewActions.click())
                .perform(ViewActions.typeText(fakeEmail));

        // introduc parola falsa
        onView(withId(R.id.passwordLogin)).perform(ViewActions.click())
                .perform(ViewActions.typeText(fakePass));

        closeSoftKeyboard();

        // finalizez crearea contului
        onView(withId(R.id.registerButton)).perform(ViewActions.click());

        //verific daca a fost creat contul, incerc sa ma conectez cu datele introduse

    }

}
