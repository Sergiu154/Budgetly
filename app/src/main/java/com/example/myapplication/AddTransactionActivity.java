package com.example.myapplication;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddTransactionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "AddTransactionActivity";
    private TextView dateText;
    private String month;
    private String year;
    private int day;
    private String stringDay;
    private EditText transactionNotes;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String monthCollection;
    CollectionReference collectionReference = db.collection("users");
    private TextView CategoryText;
    private int imgSrc;

    /* main function */
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_transaction_layout);
        setTransactionDate();
        String newString;
        int src;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
                src = 0;
            } else {
                newString = extras.getString("image_name");
                src = extras.getInt("image_url");
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("image_name");
            src = savedInstanceState.getInt("image_url");
        }
        setCategory(newString, src);
    }

    private void setCategory(String s, int src) {
        CategoryText = findViewById(R.id.transaction_select_category);
        CategoryText.setText(s);
        imgSrc = src;
    }
    /* end of main function */

    public void onSelectCategory(View view) {

        Intent intent = new Intent(this, SelectCategoryActivity.class);
        startActivity(intent);

    }

    /* date selection and display in transactions */

    private void setTransactionDate() {
        dateText = findViewById(R.id.transaction_date);

        findViewById(R.id.transaction_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month + 1;
        String date = month + "-" + dayOfMonth + "-" + year;
        this.day = dayOfMonth;
        this.stringDay = String.valueOf(dayOfMonth);
        this.year = String.valueOf(year);
        this.month = String.valueOf(month);
        monthCollection = month + "-" + year;
        dateText.setText(date);
    }
    /* end of date selection and display */

    /* button for adding transaction */
    public void addTransactionButtonClick(View v) {

        transactionNotes = findViewById(R.id.transaction_notes);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        EditText transactionPrice = findViewById(R.id.transaction_price);


        if (dateText.getText().toString().matches("")) {
//            Toast.makeText(AddTransactionActivity.this, transactionPrice.getText().toString(), Toast.LENGTH_SHORT).show();
//            Toast.makeText(AddTransactionActivity.this, transactionNotes.getText().toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(AddTransactionActivity.this, monthCollection, Toast.LENGTH_SHORT).show();

            return;
        }

        if (transactionPrice.getText().toString().matches("")) {
            Toast.makeText(AddTransactionActivity.this, monthCollection, Toast.LENGTH_SHORT).show();
            Toast.makeText(AddTransactionActivity.this, "Insert a price!", Toast.LENGTH_SHORT).show();
            return;
        }


        // adaugam tranzactia la luna respectiva in colectia userului

        Map<String, String> transaction = new HashMap<>();
        transaction.put("date", dateText.getText().toString());
        transaction.put("price", transactionPrice.getText().toString());
        transaction.put("notes", transactionNotes.getText().toString());
        TransactionDetails transactionDetails = new TransactionDetails(this.day, this.stringDay, this.month, this.year, "cat", 0, Double.parseDouble(transactionPrice.getText().toString()));
        DocumentReference ref = collectionReference
                .document(firebaseAuth.getCurrentUser().getUid()).collection(monthCollection).document();
        ref.set(transactionDetails);

        startActivity(new Intent(AddTransactionActivity.this, MainActivity.class));

        // TODO: de verificat si daca s-a ales o categorie ---- am nevoie de o functie ce primeste categoria

        // TODO: de executat ce trebuie facut cu butonul ---- am nevoie sa stiu ce trebuie returnat si tipul returnat (probabil o sa fie un sir de string-uri)
    }
    /* end of button for adding transaction */


    // get functions
    // TODO: get pentru categorie si pentru text-ul aditional

    // zicea bobo ca are nevoie de suma introdusa
    public Double getPrice() {
        EditText priceSelected = findViewById(R.id.transaction_price);

        if (priceSelected.getText().toString().matches("")) {
            return -1.0; //inseamna ca nu s-a introdus nimic !! - cred ca e inutil, dar mna de verificare
        }
        String p = priceSelected.getText().toString();
        return Double.parseDouble(p);
    }

    public TextView getDate() {
        return dateText;
    }
}



