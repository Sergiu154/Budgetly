package com.example.myapplication;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.*;

public class ColorModesTest {

    @Rule
    public ActivityTestRule<MainActivity> mColorsChange =
        new ActivityTestRule<>(MainActivity.class);

    // mai mult un test de rezistenta, schimba tema de 100 de ori
    @Test
    public void changeColorsManyTimes() {
        for(int i = 0; i < 100; ++i) {
            // merg pe account fragment folosind butoanele de navigatie
            onView(withId(R.id.account_nav))
                    .perform(ViewActions.click());

            boolean opt = Math.random() > 0.5;

            if(opt) {
                // selectez light mode
                onView(withId(R.id.lightModeButton))
                        .perform(ViewActions.click());
            }
            else {
                // selectez dark mode
                onView(withId(R.id.darkModeButton))
                        .perform(ViewActions.click());
            }

            // dupa schimbarea temei ma duce pe ecranul principal si reiau actiunea
        }

    }


}
