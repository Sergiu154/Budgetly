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
        ArrayList<Element> element;
        Category categ;
        element = new ArrayList<>();
        element.add(new Element("Phone", R.drawable.phone));
        element.add(new Element("Water", R.drawable.water));
        element.add(new Element("Electricity", R.drawable.electricity));
        element.add(new Element("Gas", R.drawable.gas));
        element.add(new Element("Television", R.drawable.tv));
        element.add(new Element("Internet", R.drawable.internet));
        element.add(new Element("Rentals", R.drawable.rentals));
        element.add(new Element("Fees & Charges", R.drawable.fees));
        element.add(new Element("Insurances", R.drawable.insurance));
        categ = new Category("Bills & Utilities", element, R.drawable.bills);
        categories.add(categ);

        element = new ArrayList<>();
        element.add(new Element("Taxi", R.drawable.taxi));
        element.add(new Element("Parking Fees", R.drawable.parking));
        element.add(new Element("Petrol", R.drawable.petrol));
        element.add(new Element("Maintenance", R.drawable.maintenance));
        categ = new Category("Transportation", element, R.drawable.transport);
        categories.add(categ);

        element = new ArrayList<>();
        element.add(new Element("Clothing", R.drawable.clothing));
        element.add(new Element("Footwear", R.drawable.footwear));
        element.add(new Element("Accessories", R.drawable.accessories));
        element.add(new Element("Electronics", R.drawable.electronics));
        categ = new Category("Shopping", element, R.drawable.shopping);
        categories.add(categ);

        element = new ArrayList<>();
        element.add(new Element("Movies", R.drawable.movies));
        element.add(new Element("Games", R.drawable.games));
        element.add(new Element("Travel", R.drawable.travel));
        categ = new Category("Entertainment", element, R.drawable.entertainment);
        categories.add(categ);

        element = new ArrayList<>();
        element.add(new Element("Sports", R.drawable.sports));
        element.add(new Element("Doctor", R.drawable.doctor));
        element.add(new Element("Pharmacy", R.drawable.pharmacy));
        element.add(new Element("Personal Care", R.drawable.care));
        categ = new Category("Health & Fitness", element, R.drawable.health);
        categories.add(categ);

        element = new ArrayList<>();
        element.add(new Element("Marriage", R.drawable.marriage));
        element.add(new Element("Funeral", R.drawable.funeral));
        element.add(new Element("Charity", R.drawable.charity));

        categ = new Category("Gifts & Donations", element, R.drawable.gifts);
        categories.add(categ);

        element = new ArrayList<>();
        element.add(new Element("Children & Babies", R.drawable.children));
        element.add(new Element("Home Improvement", R.drawable.home));
        element.add(new Element("Home Services", R.drawable.services));
        element.add(new Element("Pets", R.drawable.pets));

        categ = new Category("Family", element, R.drawable.family);
        categories.add(categ);

        element = new ArrayList<>();
        element.add(new Element("Books", R.drawable.books));
        element.add(new Element("Courses", R.drawable.courses));

        categ = new Category("Education", element, R.drawable.education);
        categories.add(categ);

        element = new ArrayList<>();
        element.add(new Element("Investment", R.drawable.investment));
        element.add(new Element("Business", R.drawable.business));
        element.add(new Element("Withdrawal", R.drawable.withdrawal));
        element.add(new Element("Others", R.drawable.other));

        categ = new Category("Other", element, R.drawable.other);
        categories.add(categ);
    }
}
