package com.example.myapplication;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;

public class ReportLogoutTest {

    @Rule
    public ActivityTestRule<MainActivity> mLogin =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void ReportLogout() {
        // ajuns in ecranul principal, ma duc la rapoarte
        onView(withId(R.id.report_nav))
                .perform(ViewActions.click());

        // merg la luna curenta
        onView(withSubstring("05/2020"))
                .perform(ViewActions.click());
        onView(withSubstring("06/2020"))
                .perform(ViewActions.click());

        // merg la pagina contului
        onView(withId(R.id.account_nav))
                .perform(ViewActions.click());

        // dau logout
        onView(withId(R.id.signOutBut))
                .perform(ViewActions.click());
    }

}
