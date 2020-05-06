package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PAGE = "ARG_PAGE";

    // TODO: Rename and change types of parameters
    private int mPage;

    public TransactionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TransactionFragment.
     */
    public static TransactionFragment newInstance(int page, String[] tabDate) {
        TransactionFragment fragment = new TransactionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString("month", tabDate[0]);
        args.putString("year", tabDate[1]);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);

        // here we will get the transactions of the current month
        // and pass that list to the recyclerView adapter
        List<TransactionDetails> names = new ArrayList<>();
        Bundle args = this.getArguments();
        // get the date passed to the bundle at newInstance
        String month = args.getString("month");
        String year = args.getString("year");
        names.add(new TransactionDetails(12, "Monday", month, year, "Sport", R.drawable.icons8_restaurant_100, 124.4f));
        names.add(new TransactionDetails(12, "Monday", month, year, "Sport", R.drawable.icons8_restaurant_100, 124.4f));
        names.add(new TransactionDetails(12, "Monday", month, year, "Sport", R.drawable.icons8_restaurant_100, 124.4f));
        names.add(new TransactionDetails(12, "Monday", month, year, "Sport", R.drawable.icons8_restaurant_100, 124.4f));
        names.add(new TransactionDetails(12, "Monday", month, year, "Sport", R.drawable.icons8_restaurant_100, 124.4f));
        names.add(new TransactionDetails(12, "Monday", month, year, "Sport", R.drawable.icons8_restaurant_100, 124.4f));
        names.add(new TransactionDetails(12, "Monday", month, year, "Sport", R.drawable.icons8_restaurant_100, 124.4f));
        names.add(new TransactionDetails(12, "Monday", month, year, "Sport", R.drawable.icons8_restaurant_100, 124.4f));
        names.add(new TransactionDetails(12, "Monday", month, year, "Sport", R.drawable.icons8_restaurant_100, 124.4f));
        names.add(new TransactionDetails(12, "Monday", month, year, "Sport", R.drawable.icons8_restaurant_100, 124.4f));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        RecylerViewAdapter myAdapter = new RecylerViewAdapter(names, this.getActivity());
        recyclerView.setAdapter(myAdapter);


        return view;

    }
}
