package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class TransactionFragment extends Fragment {

    // parameters which will be passed to a bundle
    // the bundle will preserve some fragment's data when switching tabs
    private static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;


    private double totalSum;

    // reference to the database and authentication instance
    private CollectionReference db = FirebaseFirestore.getInstance().collection("users");
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    // a list of transactions which will the populated with data from our DB
    private final List<TransactionDetails> names = new ArrayList<>();

    // an adapter which maps the data from the "names" list to a recyclerView list in the app
    private RecylerViewAdapter recylerViewAdapter;
    private String numMonth;


    public TransactionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TransactionFragment.
     */
    public static TransactionFragment newInstance(int page, List<String> tabDate) {
        TransactionFragment fragment = new TransactionFragment();


        //the bundle which will save the data between switching tabs
        //save the page numbers and tab's date

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString("numMonth", tabDate.get(0));
        args.putString("month", tabDate.get(1));
        args.putString("year", tabDate.get(2));
        fragment.setArguments(args);
        return fragment;
    }

    // when a fragment is recreated onCreated method is called
    // and the params of the bundle are retrieved
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
        }
    }


    /**
     * Android will not recreate the fragments all the time,it will
     * just recreate the view so each time the tab is visible to the user
     * we are loading the data from the DB and display it using the recyclerView
     */

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        Bundle args = this.getArguments();

        // get the date passed to the bundle at newInstance
        String month = args.getString("month");
        String year = args.getString("year");
        numMonth = args.getString("numMonth").replaceFirst("^0+(?!$)", "");


        // query the database traversing the JSON tree using the current userid
        // and the month+year combination to reach the transactions of the selected month

        db = db.document(firebaseAuth.getCurrentUser().getUid()).collection(numMonth + '-' + year);
        db.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                // if the event has failed
                if (e != null) {
                    Log.w("YourTag", "Listen failed.", e);
                    return;
                }
                // iterate the resulted data and add it to a list
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    if (doc.exists()) {
                        TransactionDetails message = doc.toObject(TransactionDetails.class);
                        names.add(message);
                    }
                    // sort transactions by date
                    Collections.sort(names);
                    // notify the adapter to update the components of its list and display it to the user
                    recylerViewAdapter.notifyDataSetChanged();
                }
            }
        });


    }

    /**
     * Each time a fragment is destroyed and recreated onCreateView method is called
     * As usual, retrieve the data from the DB and update the interface
     */

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_transaction, container, false);

        // get some textviews to adjust the current inflow and outflow
        final TextView inflow = view.findViewById(R.id.inflow_id);
        final TextView outflow = view.findViewById(R.id.outflow_id);
        final TextView finalSum = view.findViewById(R.id.finalSum);


        // here we will get the transactions of the current month
        // and pass that list to the recyclerView adapter
        Bundle args = this.getArguments();

        // get the date passed to the bundle at newInstance
        String month = args.getString("month");
        String year = args.getString("year");
        String numMonth = args.getString("numMonth").replaceFirst("^0+(?!$)", "");

        // query the database traversing the JSON tree using the current userid
        // and the month+year combination to reach the transactions of the selected month

        db = db.document(firebaseAuth.getCurrentUser().getUid()).collection(numMonth + '-' + year);

        db.orderBy("day").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots) {
                    TransactionDetails t = documentSnapshots.toObject(TransactionDetails.class);
                    names.add(documentSnapshots.toObject(TransactionDetails.class));

                }
                // update the user interface: inflow,outflow and transactions list
                Collections.sort(names);
                Double[] inOut = getInflowOutFlow(names);

                inflow.setText(inOut[1].toString());
                outflow.setText(inOut[0].toString());

                Double tmp = inOut[1] - inOut[0];
                finalSum.setText(tmp.toString());
                recylerViewAdapter.notifyDataSetChanged();

                // apply the changes
                setData(view);
            }
        });


        setData(view);
        return view;
    }

    // update the view with the new data retrieved from the database
    public void setData(View view) {

        // get the reference to the layout and set its layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(layoutManager);

        // the get adapter which will receive the transactions to be displayed
        this.recylerViewAdapter = new RecylerViewAdapter(names, this.getActivity());

        // each transaction will have a click listener which will redirect
        // the user to a page where she/he can edit or delete the selected transaction
        this.recylerViewAdapter.setOnItemClickListener(new RecylerViewAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {

                String transactionId = names.get(position).getTransactionId();
                Intent intent = new Intent(getActivity(), EditTransaction.class);
                intent.putExtra("id", transactionId);
                intent.putExtra("transactionDate", numMonth + '-' + names.get(position).getYear());
                intent.putExtra("isQueried", false);

                startActivity(intent);

            }
        });
        recyclerView.setAdapter(recylerViewAdapter);

    }

    // set the inflow and the outflow of the current user considering the changes in her/his
    // transaction list
    public Double[] getInflowOutFlow(List<TransactionDetails> trans) {

        Double[] inOut = {0.0, 0.0};
        for (TransactionDetails t : trans) {

            if (t.getAmount() < 0)
                inOut[0] -= t.getAmount();
            else
                inOut[1] += t.getAmount();
        }

        return inOut;
    }

    /**
     * get the list of transactions
     */
    public List<TransactionDetails> getNames() {
        return names;
    }

}
