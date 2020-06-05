package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.*;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.*;

/**
 * This fragment is inflated in each tab when the user
 * has click the report icon
 * Its scope is to display a visual representation of the transactions in a illustrative way
 * A bar chart and a pie char will display the values of each transaction in a different colour
 */
public class PieDataFragment extends Fragment {

    private static final String ARG_PAGE = "ARG_PAGE";
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    public PieDataFragment() {
    }

    // data passed between tabs then a fragment is switched
    public static PieDataFragment newInstance(int page, List<String> tabDate) {

        PieDataFragment fragment = new PieDataFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);

        args.putString("numMonth", tabDate.get(0));
        args.putString("month", tabDate.get(1));
        args.putString("year", tabDate.get(2));

        fragment.setArguments(args);

        return fragment;
    }

    // use to map the colours to the transactions
    private static Hashtable<String, Integer> known_colors = new Hashtable<>();

    // the list of transactions
    private List<TransactionDetails> names = new ArrayList<>();


    private void addName(TransactionDetails t) {
        names.add(t);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.pie_data_layout, container, false);

        // get data from the bundle
        Bundle args = this.getArguments();
        String year = args.getString("year");
        String numMonth = args.getString("numMonth").replaceAll("^0+(?!$)", "");

        // map the known colours to the chart
        known_colors.put("Sport", Color.parseColor("#6699ff"));
        known_colors.put("Food", Color.parseColor("#99ff66"));
        known_colors.put("Car repair", Color.parseColor("#9494b8"));

        // get the charts
        final PieChart graficuMen = view.findViewById(R.id.piechart);
        final BarChart liniileMen = view.findViewById(R.id.linechart);


        final Hashtable<String, Float> chartData = new Hashtable<>();
        // chartData = <category, total amount registered for category>

        final Set<Integer> ordered_color_set = new HashSet<>();
        // pentru a mentine aceleasi culori la fiecare rulare, trebuie puse ordonate dupa cum le citesc

        // get the refrence to the user and the DB
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        // prepare the DB refrence
        DocumentReference docRef = fStore.collection("users").document(userID);
        CollectionReference colref = docRef.collection(numMonth + '-' + year);

        final Float[] totalSpend = new Float[1];

        // get the data from the BD
        colref.get().addOnSuccessListener(getActivity(), new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots) {
                    System.out.println("EXTRAG");
                    TransactionDetails t = documentSnapshots.toObject(TransactionDetails.class);
                    names.add(t);
                    System.out.println("names = " + names);
                }

                totalSpend[0] = 0f;
                for (int i = 0; i < names.size(); ++i) {
                    TransactionDetails temp = names.get(i);
                    // am salvat platile cu - in fata, ca atunci cand adaugi un income sa vine cu plus
                    // le afisam in piechart doar pe cele cu minus, adica expenses

                    if (temp.getAmount() < 0) {
                        // am bagat minusuri la toate in fata ca sa iasa cu plus
                        totalSpend[0] += (float) -temp.getAmount();
                        if (!chartData.containsKey(temp.getCategory())) {
                            // nu am mai avut categ asta pana acum, o adaug
                            chartData.put(temp.getCategory(), (float) (-temp.getAmount()));
                            ordered_color_set.add(known_colors.get(temp.getCategory()));
                        } else {
                            // daca am mai avut categoria respectiva fac suma

                            double old_value = chartData.get(temp.getCategory());
                            double new_value = old_value - temp.getAmount();

                            // n-am folosit replace pt ca aparent trebuie API24 pentru aia si avem 21
                            chartData.remove(temp.getCategory());
                            chartData.put(temp.getCategory(), (float) new_value);
                        }
                    }
                }


                LinkedList<Integer> ordered_color_list = new LinkedList<>(ordered_color_set);
                // get the names of the categories to be displayed
                Set<String> keys = chartData.keySet();

                // display the piechart
                setPieChart(graficuMen, chartData, totalSpend, keys);

                // display the barchar
                setBarChart(keys, chartData, liniileMen);


            }
        });


        return view;
    }

    // a function which takes the data and displays it in a barhart
    private void setBarChart(Set<String> keys, Hashtable<String, Float> chart_data, BarChart liniileMen) {
        List<BarEntry> bar_entries = new ArrayList<>();

        // get each entry for the barchart and its associated amount
        float poz = 0f;
        for (String key : keys) {
            float value = chart_data.get(key);
            BarEntry entry = new BarEntry(poz, value);
            poz += 1f;
            bar_entries.add(entry);
        }
        // set the label for the plot
        BarDataSet set = new BarDataSet(bar_entries, "BarDataSet");
        BarData bar_data = new BarData(set);

        // set the data and the colours to the chart
        liniileMen.setData(bar_data);

        // chart configurations
        set.setColors(Color.parseColor("#6699ff"), Color.parseColor("#33cc00"), Color.parseColor("#9494b8"),
                Color.parseColor("#ff6600"), Color.parseColor("#006666"), Color.parseColor("#99cc00"));
        liniileMen.getDescription().setEnabled(false);
        liniileMen.getLegend().setEnabled(false);
        liniileMen.setTouchEnabled(false);

        liniileMen.invalidate();
    }
    // a function which takes the data and displays it in a piechart

    public void setPieChart(PieChart graficuMen, Hashtable<String, Float> chart_data, Float[] total_spend, Set<String> keys) {
        List<PieEntry> entries = new ArrayList<>();

        // get each entry for the piechart and its associated amount

        for (String key : keys) {
            float value = chart_data.get(key);
            PieEntry entry = new PieEntry(value, key);

            entries.add(entry);
        }

        // set the label for the plot
        PieDataSet pieSet = new PieDataSet(entries, "Your name here");

        pieSet.setColors(Color.parseColor("#6699ff"), Color.parseColor("#33cc00"), Color.parseColor("#9494b8"),
                Color.parseColor("#ff6600"), Color.parseColor("#006666"), Color.parseColor("#99cc00"));
        // pieSet.setColors(ordered_color_list);
        pieSet.setValueTextColor(Color.rgb(230, 230, 230));
        pieSet.setValueTextSize(20f);

        PieData data = new PieData(pieSet);

        // set the data and the colours to the chart

        graficuMen.setData(data);


        // chart configurations
        graficuMen.setCenterText(total_spend[0].intValue() + " lei");
        graficuMen.setCenterTextColor(Color.rgb(119, 136, 153));
        graficuMen.setCenterTextSize(20f);
        graficuMen.setTouchEnabled(false);
        graficuMen.getLegend().setEnabled(false);
        graficuMen.getDescription().setEnabled(false);

        graficuMen.animateXY(1000, 1000, Easing.EaseInOutCubic);

        graficuMen.invalidate();


    }


}
