package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;

public class PieDataFragment extends Fragment {


    public PieDataFragment(){


    }
    public static PieDataFragment newInstance() {

        Bundle args = new Bundle();

        PieDataFragment fragment = new PieDataFragment();
        fragment.setArguments(args);
        return fragment;
    }

    PieChart graficuMen;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pie_data_layout, container, false);


        graficuMen = view.findViewById(R.id.linechart);

        List<PieEntry> entries = new ArrayList<>();
        PieEntry entry1 = new PieEntry(90, "red");
        PieEntry entry2 = new PieEntry(80, "blue");

        entry1.setLabel("ROSHU");
        entry2.setLabel("ALBASTRU");


        entries.add(entry1);
        entries.add(entry2);



        // cf broooooooooo


        PieDataSet pieSet = new PieDataSet(entries, "Your name here");
        pieSet.setColors(Color.rgb(38, 49, 69), Color.rgb(38, 66, 34));
        pieSet.setValueTextColor(Color.rgb(230, 230, 230));
        pieSet.setValueTextSize(20f);

        PieData data = new PieData(pieSet);

        // data.setValueTextColor(Color.rgb(179,198,228));

        graficuMen.setData(data);

        graficuMen.setBackgroundColor(Color.rgb(222, 234, 248));
        graficuMen.setCenterText("Pie Chart Test");
        graficuMen.setCenterTextColor(Color.rgb(246, 55, 109));
        graficuMen.setCenterTextSize(20f);
        graficuMen.setDrawCenterText(false);


        graficuMen.animateXY(1000, 1000, Easing.EaseInOutCubic);

        graficuMen.invalidate();


        return view;
    }
}
