package com.example.myapplication;

import android.os.Bundle;
import android.text.BoringLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

public class MainTransactionFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    View view;
    private Boolean isChart;
//
//    public MainTransactionFragment(Boolean isChart) {
//
//        this.isChart = isChart;
//    }
    public  MainTransactionFragment(){


    }

    public static MainTransactionFragment newInstance(Boolean isChart) {

        Bundle args = new Bundle();
        MainTransactionFragment fragment = null;
        args.putBoolean("isChart",isChart);
        fragment = new MainTransactionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_transaction_layout, container, false);
        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.sliding_tabs);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initTabs();

    }

    public void initTabs() {


        // list of tab titles passed to the TransactionFragmentAdapter
        List<String> tabTitles = new ArrayList<>();

        // get the current date month and year
        DateFormat df = new SimpleDateFormat("MM");
        DateFormat dfy = new SimpleDateFormat("YYYY");

        String currMonth = df.format(Calendar.getInstance().getTime());
        String currYear = dfy.format(Calendar.getInstance().getTime());
        int maxDate = Integer.parseInt(currMonth);

        // add all the months of the year up to the current month in the format 'MM/YYYY'
        for (int i = 1; i <= maxDate; i++) {
            String month;

            if (i < 10)
                month = "0" + i;
            else
                month = String.valueOf(i);

            tabTitles.add(month + "/" + currYear);
        }
        Bundle bundle = getArguments();
        Boolean isChart = bundle.getBoolean("isChart");

        // set the adapter for the viewPager
        viewPager.setAdapter(new TransactionFragmentAdapter(getChildFragmentManager(), getContext(), tabTitles, isChart));

        // get the tabLayout and bind it with the viewPager
        tabLayout.setupWithViewPager(viewPager);

    }

}
