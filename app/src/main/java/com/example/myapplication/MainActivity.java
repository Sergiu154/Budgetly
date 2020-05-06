package com.example.myapplication;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;


public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize the tabs with the months of the year up to the current month
//        initTabs();

        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MainTransactionFragment.newInstance(false)).commit();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {

                        case R.id.transaction_nav:
                            selectedFragment = MainTransactionFragment.newInstance(false);
                            break;
                        case R.id.report_nav:
                            selectedFragment = MainTransactionFragment.newInstance(true);
                            break;

                        case R.id.account_nav:
                            selectedFragment = AccountFragment.newInstance();
                            break;

                        case R.id.add_transaction_nav:
                            Intent intent = new Intent(MainActivity.this, AddTransactionActivity.class);
                            startActivity(intent);
                            break;
                    }
                    if(selectedFragment != null)
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };

    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    public void showPop(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.top_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.adjust_balance_top:
                Toast.makeText(this, "Adjust balance", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.view_by_category:
                Toast.makeText(this, "Category", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return true;
        }

    }


}
