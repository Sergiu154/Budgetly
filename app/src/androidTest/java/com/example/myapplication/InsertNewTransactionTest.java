package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;

import static android.graphics.ColorSpace.match;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.contrib.PickerActions.*;

public class InsertNewTransactionTest {

    @Rule
    public ActivityTestRule<AddTransactionActivity> mAddTransactTestRule =
            new ActivityTestRule<>(AddTransactionActivity.class);

    @Test
    public void insertTransactionTest() throws Exception {

        // apas pe buton de selectie categorie
        onView(withId(R.id.transaction_select_category))
                .perform(ViewActions.click());


        // aici se deschide activitatea de selectie
        // click pe income
        onView(withText("INCOME"))
                .perform(ViewActions.click());

        onView(withText("Income"))
                .perform(ViewActions.click());

        // s-a deschis lista cu optiuni pt income,
        // aleg optiunea de salariu
        onView(withText("Salary"))
                .perform(ViewActions.click());

        // click ca sa deschid calendar
        onView(withId(R.id.transaction_date))
                .perform(ViewActions.click());

        // selectez data
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020, 6, 10));
        onView(withId(android.R.id.button1)).perform(ViewActions.click());


        // pun cati bani
        Integer fakePrice = 500;
        onView(withId(R.id.transaction_price))
                .perform(ViewActions.click())
                .perform(ViewActions.typeTextIntoFocusedView(String.valueOf(fakePrice)));

        // scriu descriere
        String fakeDescription = "test income transaction 1";
        onView(withId(R.id.transaction_notes))
                .perform(ViewActions.click())
                .perform(ViewActions.typeTextIntoFocusedView(fakeDescription));

        // inchid tastatura
        closeSoftKeyboard();

        // inregistrez tranzactia
        onView(withId(R.id.transaction_add_button))
                .perform(ViewActions.click());
    }


}


