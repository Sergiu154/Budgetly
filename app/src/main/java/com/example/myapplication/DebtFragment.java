package com.example.myapplication;

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
public class DebtFragment extends Fragment {

    public DebtFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.debt_fragment_layout, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));


        ArrayList<Category> categories = new ArrayList<>();
        ArrayList<Element> element = new ArrayList<>();
        element.add(new Element("Debt", R.drawable.debt));
        element.add(new Element("Debt Collection", R.drawable.debtcollect));

        Category categ = new Category("Debt", element, R.drawable.debt);
        categories.add(categ);

        ArrayList<Element> elements = new ArrayList<>();
        elements.add(new Element("Loan", R.drawable.loan));
        elements.add(new Element("Repayment", R.drawable.repayment));
        categ = new Category("Other", elements, R.drawable.other);
        categories.add(categ);

        ElementAdapter adapter = new ElementAdapter(categories);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
