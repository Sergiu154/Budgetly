package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeFragment extends Fragment {

    public IncomeFragment() {
        // Required empty public constructor
        // test
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.income_fragment_layout, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        ArrayList<Category> categories = new ArrayList<>();
        final ArrayList<Element> element = new ArrayList<>();
        element.add(new Element("Award", R.drawable.award));
        element.add(new Element("Interest Money", R.drawable.interest));
        element.add(new Element("Salary", R.drawable.salary));
        element.add(new Element("Gifts", R.drawable.gift));
        element.add(new Element("Selling", R.drawable.selling));
        element.add(new Element("Others", R.drawable.other));

        Category categ = new Category("Income", element, R.drawable.income);
        categories.add(categ);

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, String category, int src) {
                Context context = view.getContext();
                Intent intent = new Intent(context, AddTransactionActivity.class);
                intent.putExtra("image_url", src);
                intent.putExtra("image_name",category);
                context.startActivity(intent);
            }
        };
        ElementAdapter adapter = new ElementAdapter(categories,listener);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
