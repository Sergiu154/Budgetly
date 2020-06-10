package com.example.myapplication;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

/**
 * This pager adapter consists of 3 tabs which will enable the user
 * to select a specific type of transaction: Debt,Expense or Income
 */
public class SelectCategoryActivity extends AppCompatActivity {

    /**
     * the viewPager is used to switch between tabs
     * pageAdapter will populated the tabs with the appropriate data
     */
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem tab1, tab2, tab3;
    public PageAdapter pageradapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_category_layout);

        // get the reference to the tabs and the viewPager
        tabLayout = findViewById(R.id.tablayout);
        tab1 = findViewById(R.id.Tab1);
        tab2 = findViewById(R.id.Tab2);
        tab3 = findViewById(R.id.Tab3);
        viewPager = findViewById(R.id.viewpager);

        // prepare the adapter and set it to the viewPager
        pageradapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageradapter);

        // create the layout of 3 tabs and notify the adapter when a swipe to another tab is made
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    pageradapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 1)
                    pageradapter.notifyDataSetChanged();
                else if (tab.getPosition() == 2)
                    pageradapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        // set the listener to the adapter so that when the user swipes to the left or right
        // the tabs is changed using the default animation probided by the viewPager
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}
