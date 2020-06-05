package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A fragment which corresponds to one of the tabs from SelectCategory activity
 */
public class ExpenseFragment extends Fragment {

    public ExpenseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.expense_fragment_layout, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        // prepare the categories and their images

        ArrayList<Category> categories = new ArrayList<>();
        ArrayList<Element> element = new ArrayList<>();
        element.add(new Element("Restaurants", R.drawable.restaurant));
        element.add(new Element("Café", R.drawable.cafe));

        Category categ = new Category("Food & Beverage", element, R.drawable.food);
        categories.add(categ);


        createCategories(categories);

        // when a category is selected, the data is transferred to the add category page
        // where the user continues to add data to her/his transaction
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, String category, int src) {
                Context context = view.getContext();
                Intent intent;

                // if the flag whichActivity is set to 1 then the requests came
                // from the edit transaction activity
                // otherwise the request is from AddTransaction
                int res = getActivity().getIntent().getExtras().getInt("whichActivity");
                if (res == 1)
                    intent = new Intent(context, EditTransaction.class);
                else intent = new Intent(context, AddTransactionActivity.class);

                // get the data back to the activity which generated the request

                intent.putExtra("image_url", src);
                intent.putExtra("image_name", category);
                intent.putExtra("isQueried", true);
                context.startActivity(intent);
            }
        };
        // create and set the adapter

        ElementAdapter adapter = new ElementAdapter(categories, listener);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void createCategories(ArrayList<Category> categories) {

        String[] categorii = {"Phone", "Water", "Electricity",
                "Gas", "Television", "Internet",
                "Rentals", "Fees & Charges", "Insurances",
                "Bills & Utilities", "Taxi", "Parking Fees",
                "Petrol", "Maintenance", "Transportation",
                "Clothing", "Footwear", "Accessories",
                "Electronics", "Shopping", "Movies",
                "Games", "Travel", "Entertainment",
                "Sports", "Doctor", "Pharmacy",
                "Personal Care", "Health & Fitness",
                "Marriage", "Funeral", "Charity",
                "Gifts & Donations", "Children & Babies",
                "Home Improvement", "Home Services", "Pets",
                "Family", "Books", "Education", "Investment",
                "Business", "Withdrawal", "Others", "Other"};

        int[] imagini = {R.drawable.phone, R.drawable.water, R.drawable.electricity,
                R.drawable.gas, R.drawable.tv, R.drawable.internet,
                R.drawable.rentals, R.drawable.fees, R.drawable.insurance,
                R.drawable.bills, R.drawable.taxi, R.drawable.parking,
                R.drawable.petrol, R.drawable.maintenance, R.drawable.transport,
                R.drawable.clothing, R.drawable.footwear, R.drawable.accessories,
                R.drawable.electronics, R.drawable.shopping, R.drawable.movies,
                R.drawable.games, R.drawable.travel, R.drawable.entertainment,
                R.drawable.sports, R.drawable.doctor, R.drawable.pharmacy,
                R.drawable.care, R.drawable.health, R.drawable.marriage,
                R.drawable.funeral, R.drawable.charity, R.drawable.gifts,
                R.drawable.children, R.drawable.home, R.drawable.services,
                R.drawable.family, R.drawable.books, R.drawable.courses,
                R.drawable.education, R.drawable.investment, R.drawable.business,
                R.drawable.withdrawal, R.drawable.other, R.drawable.other
        };

        ArrayList<Element> element;
        Category categ;
        element = new ArrayList<>();
        int i = 0;
        int len = imagini.length;

        for (i = 0; i < 9; i++)
            element.add(new Element(categorii[i], imagini[i]));
        categories.add(new Category(categorii[i], element, imagini[i]));

        i += 1;

        element = new ArrayList<>();
        for (i = 10; i < 14; i++)
            element.add(new Element(categorii[i], imagini[i]));
        categories.add(new Category(categorii[i], element, imagini[i]));
        i += 1;


        element = new ArrayList<>();
        for (i = 15; i < 19; i++)
            element.add(new Element(categorii[i], imagini[i]));
        categories.add(new Category(categorii[i], element, imagini[i]));
        i += 1;


        element = new ArrayList<>();
        for (i = 20; i < 23; i++)
            element.add(new Element(categorii[i], imagini[i]));
        categories.add(new Category(categorii[i], element, imagini[i]));
        i += 1;


        element = new ArrayList<>();

        for (i = 24; i < 28; i++)
            element.add(new Element(categorii[i], imagini[i]));
        categories.add(new Category(categorii[i], element, imagini[i]));
        i += 1;


        element = new ArrayList<>();

        for (i = 29; i < 32; i++)
            element.add(new Element(categorii[i], imagini[i]));
        categories.add(new Category(categorii[i], element, imagini[i]));
        i += 1;

        element = new ArrayList<>();

        for (i = 33; i < 37; i++)
            element.add(new Element(categorii[i], imagini[i]));
        categories.add(new Category(categorii[i], element, imagini[i]));
        i += 1;


        element = new ArrayList<>();

        for (i = 38; i < 40; i++)
            element.add(new Element(categorii[i], imagini[i]));
        categories.add(new Category(categorii[i], element, imagini[i]));
        i += 1;


        element = new ArrayList<>();

        for (i = 41; i < 45; i++)
            element.add(new Element(categorii[i], imagini[i]));
        categories.add(new Category(categorii[i - 1], element, imagini[i - 1]));

    }
}
