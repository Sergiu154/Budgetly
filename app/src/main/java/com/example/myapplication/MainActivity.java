package com.example.myapplication;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.icu.text.Collator;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.NavigationMenuItemView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * Core activity of the app
 * Contains a bottom menu which gets you to different activities
 */
public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private int lastTab;

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.transaction_nav);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize the tabs with the months of the year up to the current month

        // get the bottom navigation and set a listener to it
        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        // by default, the main transaction fragment will be loaded when the app is opened
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MainTransactionFragment.newInstance(false, lastTab)).commit();


    }

    public void passData(int numTab) {
        this.lastTab = numTab;
    }

    public void setReport(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MainTransactionFragment.newInstance(true, lastTab)).commit();

    }

    // the bottom navigation listener which will take the user to activities such as
    // account,report,transactions and add transactions
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {


                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {

                        // get the user to the transaction history
                        case R.id.transaction_nav:
                            selectedFragment = MainTransactionFragment.newInstance(false, lastTab);
                            break;
                        // the piehcart and barchart report
                        case R.id.report_nav:
                            selectedFragment = MainTransactionFragment.newInstance(true, lastTab);
                            break;
                        // account page
                        case R.id.account_nav:
                            selectedFragment = AccountFragment.newInstance();
                            break;
                        // add a transaction
                        case R.id.add_transaction_nav:
                            Intent intent = new Intent(MainActivity.this, AddTransactionActivity.class);
                            startActivity(intent);
                            break;
                    }
                    // inflate the fragment to the view
                    if (selectedFragment != null)
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };

    // create the options of the bottom menu and inflate it
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    // inflate pop-up menu in a upper-right corner
    // a menu which gives the user the possibility to log out from the app
    public void showPop(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.top_menu);
        popupMenu.show();
    }

    // listener for each option of the pop-up menu
    // adjust balance,sort by category or log out from the app
    // prints a text in a toast when each action is triggered
    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.adjust_balance_top:
                Toast.makeText(this, "Adjust balance", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.view_by_category:
                Toast.makeText(this, "Category", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.logout_main:
                // signs out the user the redirects him/her to the login page
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                return true;
            default:
                return true;
        }

    }

}
