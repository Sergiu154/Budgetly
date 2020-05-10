package com.example.myapplication;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

import java.util.Set;

import android.graphics.Color;
import android.os.Bundle;

import com.anychart.core.gauge.pointers.Bar;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;

public class PieDataFragment extends Fragment {


    public PieDataFragment() {
    }


    public static PieDataFragment newInstance() {

        Bundle args = new Bundle();

        PieDataFragment fragment = new PieDataFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private static Hashtable<String, Integer> known_colors = new Hashtable<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pie_data_layout, container, false);



        known_colors.put("Sport", Color.parseColor("#6699ff"));
        known_colors.put("Food", Color.parseColor("#99ff66"));
        known_colors.put("Car repair", Color.parseColor("#9494b8"));


        PieChart graficuMen = view.findViewById(R.id.piechart);
        BarChart liniileMen = view.findViewById(R.id.linechart);


        List<TransactionDetails> names = new ArrayList<>();
        Bundle args = this.getArguments();
        String month = args.getString("month");
        String year = args.getString("year");

        names.add(new TransactionDetails(12, "Monday", month, year, "Sport", R.drawable.icons8_restaurant_100, 124.4f));
        names.add(new TransactionDetails(12, "Monday", month, year, "Food", R.drawable.icons8_restaurant_100, 100.4f));
        names.add(new TransactionDetails(12, "Monday", month, year, "Sport", R.drawable.icons8_restaurant_100, 10.4f));
        names.add(new TransactionDetails(12, "Monday", month, year, "Sport", R.drawable.icons8_restaurant_100, 124.4f));
        names.add(new TransactionDetails(12, "Monday", month, year, "Sport", R.drawable.icons8_restaurant_100, 124.4f));
        names.add(new TransactionDetails(12, "Monday", month, year, "Sport", R.drawable.icons8_restaurant_100, 124.4f));
        names.add(new TransactionDetails(12, "Monday", month, year, "Car Repair", R.drawable.icons8_restaurant_100, 500f));
        names.add(new TransactionDetails(12, "Monday", month, year, "Sport", R.drawable.icons8_restaurant_100, 124.4f));
        names.add(new TransactionDetails(12, "Monday", month, year, "Food", R.drawable.icons8_restaurant_100, 50f));
        names.add(new TransactionDetails(12, "Monday", month, year, "Sport", R.drawable.icons8_restaurant_100, 124.4f));

        Hashtable<String, Float> chart_data = new Hashtable<>();
        // chart_data = <category, total amount registered for category>

        Set<Integer> ordered_color_set = new HashSet<>();
        // pentru a mentine aceleasi culori la fiecare rulare, trebuie puse ordonate dupa cum le citesc

        Float total_spend = 0f;

        for (int i = 0; i < names.size(); ++i) {
            TransactionDetails temp = names.get(i);
            total_spend += (float) temp.getAmount();
            if(!chart_data.containsKey(temp.getCategory())) {
                // nu am mai avut categ asta pana acum, o adaug
                chart_data.put(temp.getCategory(), (float) temp.getAmount());
                ordered_color_set.add(known_colors.get(temp.getCategory()));
            }
            else {
                // daca am mai avut categoria respectiva fac suma

                double old_value = chart_data.get(temp.getCategory());
                double new_value = old_value + temp.getAmount();

                // n-am folosit replace pt ca aparent trebuie API24 pentru aia si avem 21
                chart_data.remove(temp.getCategory());
                chart_data.put(temp.getCategory(), (float)new_value);
            }
        }


        LinkedList<Integer> ordered_color_list = new LinkedList<>(ordered_color_set);


        // AICI ESTE PT PIE_CHART

        List<PieEntry> entries = new ArrayList<>();
        Set<String> keys = chart_data.keySet();
        for(String key : keys) {
            float value = chart_data.get(key);
            PieEntry entry = new PieEntry(value, key);

            entries.add(entry);
        }

        PieDataSet pieSet = new PieDataSet(entries, "Your name here");

        pieSet.setColors(Color.parseColor("#6699ff"), Color.parseColor("#33cc00"), Color.parseColor("#9494b8"));
        // pieSet.setColors(ordered_color_list);
        pieSet.setValueTextColor(Color.rgb(230, 230, 230));
        pieSet.setValueTextSize(20f);

        PieData data = new PieData(pieSet);
        graficuMen.setData(data);

        // graficuMen.setBackgroundColor(Color.parseColor("#e0e0eb"));
        graficuMen.setCenterText("Cheltuieli totale\n" + total_spend.intValue() + " lei");
        graficuMen.setCenterTextColor(Color.rgb(246, 55, 109));
        graficuMen.setCenterTextSize(20f);
        graficuMen.setTouchEnabled(false);
        graficuMen.getLegend().setEnabled(false);
        graficuMen.getDescription().setEnabled(false);

        graficuMen.animateXY(1000, 1000, Easing.EaseInOutCubic);

        graficuMen.invalidate();



        // AICI ESTE PT BAR_CHART

        List<BarEntry> bar_entries = new ArrayList<>();

        float poz = 0f;
        for(String key : keys) {
            float value = chart_data.get(key);
            BarEntry entry = new BarEntry(value, poz);
            poz += 1f;
            bar_entries.add(entry);
        }
        BarDataSet set = new BarDataSet(bar_entries, "BarDataSet");
        BarData bar_data = new BarData(set);

        bar_data.setBarWidth(0.9f);
        liniileMen.setData(bar_data);
        liniileMen.setFitBars(true);
        liniileMen.invalidate();


        return view;
    }
}
