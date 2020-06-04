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
        final ArrayList<Element> element = new ArrayList<>();
        final ArrayList<Element> element1 = new ArrayList<>();
        element.add(new Element("Debt", R.drawable.debt));
        element.add(new Element("Debt Collection", R.drawable.debtcollect));

        element1.add(new Element("Debt", R.drawable.debt));
        element1.add(new Element("Debt Collection", R.drawable.debtcollect));
        Category categ = new Category("Debt", element, R.drawable.debt);
        categories.add(categ);

        final ArrayList<Element> elements = new ArrayList<>();
        elements.add(new Element("Loan", R.drawable.loan));
        elements.add(new Element("Repayment", R.drawable.repayment));
        element1.add(new Element("Loan", R.drawable.loan));
        element1.add(new Element("Repayment", R.drawable.repayment));
        categ = new Category("Other", elements, R.drawable.other);
        categories.add(categ);

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, String category, int src) {
                Context context = view.getContext();
                Intent intent;
                int res = getActivity().getIntent().getExtras().getInt("whichActivity");
                if (res == 1)
                    intent = new Intent(context,EditTransaction.class);
                else intent = new Intent(context, AddTransactionActivity.class);



                intent.putExtra("image_url", src);
                intent.putExtra("image_name", category);
                intent.putExtra("isQueried",true);
                context.startActivity(intent);
            }
        };
        ElementAdapter adapter = new ElementAdapter(categories,listener);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
