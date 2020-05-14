package com.example.myapplication;

import android.annotation.SuppressLint;
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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PAGE = "ARG_PAGE";

    // TODO: Rename and change types of parameters
    private int mPage;

    private CollectionReference db = FirebaseFirestore.getInstance().collection("users");
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private final List<TransactionDetails> names = new ArrayList<>();
    private RecylerViewAdapter recylerViewAdapter;


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
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString("numMonth", tabDate.get(0));
        args.putString("month", tabDate.get(1));
        args.putString("year", tabDate.get(2));
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


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
//        if (isVisibleToUser) {
        //This means this fragment is visible to user so you can write code to refresh the fragment here by reloaded the data.

        Bundle args = this.getArguments();

        // get the date passed to the bundle at newInstance
        String month = args.getString("month");
        String year = args.getString("year");
        String numMonth = args.getString("numMonth").replaceFirst("^0+(?!$)", "");

        db = db.document(firebaseAuth.getCurrentUser().getUid()).collection(numMonth + '-' + year);
        db.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("YourTag", "Listen failed.", e);
                    return;
                }

                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    if (doc.exists()) {
                        TransactionDetails message = doc.toObject(TransactionDetails.class);
                        names.add(message);
                    }
                    Collections.sort(names);
                    recylerViewAdapter.notifyDataSetChanged();
                }
            }
        });

//        }
    }


    // doar temporar aici
//    private List<TransactionDetails> names = new ArrayList<>();
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_transaction, container, false);

        // here we will get the transactions of the current month
        // and pass that list to the recyclerView adapter
        Bundle args = this.getArguments();

        // get the date passed to the bundle at newInstance
        String month = args.getString("month");
        String year = args.getString("year");
        String numMonth = args.getString("numMonth").replaceFirst("^0+(?!$)", "");

        db = db.document(firebaseAuth.getCurrentUser().getUid()).collection(numMonth + '-' + year);

        db.orderBy("day").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots) {
                    TransactionDetails t = documentSnapshots.toObject(TransactionDetails.class);
                    names.add(documentSnapshots.toObject(TransactionDetails.class));

                }
                Collections.sort(names);
                recylerViewAdapter.notifyDataSetChanged();
                setData(view);
            }
        });


//        names.add(new TransactionDetails(12, "Monday", month, year, "Sport", R.drawable.icons8_restaurant_100, 124.4f));
//        names.add(new TransactionDetails(12, "Monday", month, year, "Food", R.drawable.icons8_restaurant_100, 100.4f));
//        names.add(new TransactionDetails(12, "Monday", month, year, "Sport", R.drawable.icons8_restaurant_100, 10.4f));
//        names.add(new TransactionDetails(12, "Monday", month, year, "Sport", R.drawable.icons8_restaurant_100, 124.4f));
//        names.add(new TransactionDetails(12, "Monday", month, year, "Sport", R.drawable.icons8_restaurant_100, 124.4f));
//        names.add(new TransactionDetails(12, "Monday", month, year, "Sport", R.drawable.icons8_restaurant_100, 124.4f));
//        names.add(new TransactionDetails(12, "Monday", month, year, "Car Repair", R.drawable.icons8_restaurant_100, 500f));
//        names.add(new TransactionDetails(12, "Monday", month, year, "Sport", R.drawable.icons8_restaurant_100, 124.4f));
//        names.add(new TransactionDetails(12, "Monday", month, year, "Food", R.drawable.icons8_restaurant_100, 50f));
//        names.add(new TransactionDetails(12, "Monday", month, year, "Sport", R.drawable.icons8_restaurant_100, 124.4f));

        setData(view);
        return view;
    }

    public void setData(View view) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        this.recylerViewAdapter = new RecylerViewAdapter(names, this.getActivity());
        recyclerView.setAdapter(recylerViewAdapter);

    }


    public List<TransactionDetails> getNames() {
        return names;
    }

}
