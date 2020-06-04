package com.example.myapplication;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
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
    private String categoryName;
    private int imgSrc;
    private Double totalSum;
    private boolean hasMoney;
    private HashMap<String, Boolean> income;

    /* main function */
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        // hashmap used for addTransaction Button to add money in case
        // the user has chosen one of these categories
        income = new HashMap<>();
        String[] incomes = {"Award", "Interest Money", "Salary", "Gifts", "Selling", "Others"};
        for (String type : incomes)
            income.put(type, true);

        super.onCreate(savedInstanceState);
        this.hasMoney = true;
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
        ImageView categoryImage = findViewById(R.id.add_category_photo);
        CategoryText.setText(s);
        categoryName = s;
        categoryImage.setImageResource(src);
        categoryImage.setTag(src);
        imgSrc = src;
    }
    /* end of main function */

    public void onSelectCategory(View view) {

        Intent intent = new Intent(this, SelectCategoryActivity.class);
        intent.putExtra("whichActivity", 0);

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
        HashMap<String, String> months = new HashMap<>();
        String[] mnths = {"", "Jan", "Feb", "March", "April", "May", "June", "July", "August", "Sept", "Oct", "Nov", "Dec"};
        for (int i = 1; i <= 12; i++) {
            if (i < 10) {
                months.put(Integer.toString(i), mnths[i]);
            } else {

                months.put(Integer.toString(i), mnths[i]);
            }
        }
        month = month + 1;
        String date = month + "-" + dayOfMonth + "-" + year;
        this.day = dayOfMonth;
        this.stringDay = String.valueOf(dayOfMonth);
        this.year = String.valueOf(year);
        this.month = months.get(String.valueOf(month));
        monthCollection = month + "-" + year;
        dateText.setText(date);
    }
    /* end of date selection and display */

    /* button for adding transaction */
    public void addTransactionButtonClick(View v) {

        hasMoney = true;

        transactionNotes = findViewById(R.id.transaction_notes);
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final EditText transactionPrice = findViewById(R.id.transaction_price);


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

        collectionReference
                .document(firebaseAuth.getCurrentUser().getUid()).collection("Wallet").document("MainWallet").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    DocumentSnapshot documentSnapshot = task.getResult();
                    Boolean isPosivite = false;
                    if (income.get(CategoryText.getText().toString()) != null)
                        isPosivite = true;

                    totalSum = (double) documentSnapshot.getData().get("amount");

                    if (totalSum - Double.parseDouble(transactionPrice.getText().toString()) < 0 && !isPosivite) {
                        Toast.makeText(AddTransactionActivity.this, "You are a little poor human", Toast.LENGTH_SHORT).show();

                    } else {


                        DocumentReference ref = collectionReference
                                .document(firebaseAuth.getCurrentUser().getUid()).collection(monthCollection).document();

                        Map<String, String> transaction = new HashMap<>();
                        transaction.put("date", dateText.getText().toString());
                        transaction.put("price", transactionPrice.getText().toString());
                        transaction.put("notes", transactionNotes.getText().toString());
                        TransactionDetails transactionDetails = new TransactionDetails(day, stringDay, month, year, categoryName, imgSrc, -Double.parseDouble(transactionPrice.getText().toString()), ref.getId());

                        // daca adaug bani dau cu plus
                        if (isPosivite)
                            transactionDetails.setAmount(-transactionDetails.getAmount());

                        ref.set(transactionDetails);

                        // update totalsum
                        HashMap<String, Double> sum = new HashMap<>();
                        sum.put("amount", totalSum + transactionDetails.getAmount());
                        collectionReference
                                .document(firebaseAuth.getCurrentUser().getUid()).collection("Wallet").document("MainWallet").set(sum);


                        startActivity(new Intent(AddTransactionActivity.this, MainActivity.class));

                    }

                }

            }
        });
        // adaugam tranzactia la luna respectiva in colectia userului

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



