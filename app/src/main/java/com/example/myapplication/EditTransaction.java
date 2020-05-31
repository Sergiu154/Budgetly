package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditTransaction extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    private CollectionReference db = FirebaseFirestore.getInstance().collection("users");
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private TextView CategoryText;
    private String categoryName;
    private int imgSrc;
    private EditText transactionPrice;
    private EditText transactionNotes;
    private TextView dateText;
    private ImageView categoryImage;
    private static Boolean queried = false;
    private static TransactionDetails transaction;
    private String month;
    private String year;
    private int day;
    private String stringDay;
    private String monthCollection;
    private static String transactionID;
    private static String transactionDate;
    private Double totalSum;
    private boolean hasMoney;
    private HashMap<String, Boolean> income;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_transaction_layout);
        transactionPrice = findViewById(R.id.transaction_price);
        dateText = findViewById(R.id.transaction_date);
        income = new HashMap<>();
        String[] incomes = {"Award", "Interest Money", "Salary", "Gifts", "Selling", "Others"};
        for (String type : incomes)
            income.put(type, true);

        this.hasMoney = true;


        db.document(firebaseAuth.getCurrentUser().getUid()).collection("Wallet").document("MainWallet").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    DocumentSnapshot documentSnapshot = task.getResult();
                    totalSum = (double) documentSnapshot.getData().get("amount");
                }

            }
        });
        if (getIntent().getExtras().getString("transactionDate") != null) {
            transactionDate = getIntent().getExtras().getString("transactionDate");
            transactionID = getIntent().getExtras().getString("id");
        }
        queried = getIntent().getExtras().getBoolean("isQueried");

        monthCollection = transactionDate;


        if (!queried) {
            DocumentReference docRef = db.document(firebaseAuth.getCurrentUser().getUid()).collection(transactionDate).document(transactionID);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            transaction = documentSnapshot.toObject(TransactionDetails.class);
                            queried = true;

                            setTransactionDate();
                            setCategory(transaction.getCategory(), transaction.getCategoryPath());
                            transactionPrice.setText(Double.toString(transaction.getAmount() < 0 ? -transaction.getAmount() : transaction.getAmount()));


                        }
                    }
                }
            });
        }
        if (queried) {

            setTransactionDate();
            setCategory(transaction.getCategory(), transaction.getCategoryPath());
            transactionPrice.setText(Double.toString(transaction.getAmount() < 0 ? -transaction.getAmount() : transaction.getAmount()));


        }

        // write the initial date to the view

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

        TextView textView = findViewById(R.id.edit_transaction_id);
        String transactionId = getIntent().getExtras().getString("transactionDate");
        textView.setText(transactionId);


    }

    private void setCategory(String s, int src) {
        CategoryText = findViewById(R.id.transaction_select_category);
        categoryImage = findViewById(R.id.category_image);
        CategoryText.setText(s);
        categoryName = s;
        categoryImage.setImageResource(src);
        categoryImage.setTag(src);
        imgSrc = src;
    }

    @SuppressLint("SetTextI18n")
    private void setTransactionDate() {
        HashMap<String, String> months = new HashMap<>();
        String[] mnths = {"", "Jan", "Feb", "March", "April", "May", "June", "July", "August", "Sept", "Oct", "Nov", "Dec"};
        for (int i = 1; i <= 12; i++) {
            if (i < 10) {
                months.put(mnths[i], Integer.toString(i));
            } else {

                months.put(mnths[i], Integer.toString(i));
            }
        }

        dateText.setText(months.get(transaction.getMonth()) + '-' + Integer.toString(transaction.getDay()) + '-' + transaction.getYear());
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

    public void onSelectCategory(View view) {

        Intent intent = new Intent(this, SelectCategoryActivity.class);
        intent.putExtra("whichActivity", 1);
        startActivity(intent);

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

    // delete the selected transaction
    public void onDeleteButton(View view) {

        DocumentReference docRef = db.document(firebaseAuth.getCurrentUser().getUid()).collection(transactionDate).document(transactionID);
        docRef.delete();

        // update totalsum
        HashMap<String, Double> sum = new HashMap<>();
        sum.put("amount", totalSum - transaction.getAmount());
        db.document(firebaseAuth.getCurrentUser().getUid()).collection("Wallet").document("MainWallet").set(sum);
        startActivity(new Intent(EditTransaction.this, MainActivity.class));


    }

    /* button for adding transaction */
    public void onSaveButton(View v) {

        hasMoney = true;

        transactionNotes = findViewById(R.id.transaction_notes);
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final EditText transactionPrice = findViewById(R.id.transaction_price);


        if (dateText.getText().toString().matches("")) {
//            Toast.makeText(AddTransactionActivity.this, transactionPrice.getText().toString(), Toast.LENGTH_SHORT).show();
//            Toast.makeText(AddTransactionActivity.this, transactionNotes.getText().toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(EditTransaction.this, monthCollection, Toast.LENGTH_SHORT).show();

            return;
        }

        if (transactionPrice.getText().toString().matches("")) {
            Toast.makeText(EditTransaction.this, monthCollection, Toast.LENGTH_SHORT).show();
            Toast.makeText(EditTransaction.this, "Insert a price!", Toast.LENGTH_SHORT).show();
            return;
        }

        DocumentReference docRef = db.document(firebaseAuth.getCurrentUser().getUid()).collection(transactionDate).document(transactionID);
        docRef.delete();

        // update totalsum
        HashMap<String, Double> sum = new HashMap<>();
        sum.put("amount", totalSum - transaction.getAmount());
        db.document(firebaseAuth.getCurrentUser().getUid()).collection("Wallet").document("MainWallet").set(sum);


        db.document(firebaseAuth.getCurrentUser().getUid()).collection("Wallet").document("MainWallet").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    DocumentSnapshot documentSnapshot = task.getResult();
                    Boolean isPosivite = false;
                    if (income.get(CategoryText.getText().toString()) != null)
                        isPosivite = true;

                    totalSum = (double) documentSnapshot.getData().get("amount");

                    if (totalSum - Double.parseDouble(transactionPrice.getText().toString()) < 0 && !isPosivite) {
                        Toast.makeText(EditTransaction.this, "You are a little poor human", Toast.LENGTH_SHORT).show();

                    } else {


                        DocumentReference ref = db
                                .document(firebaseAuth.getCurrentUser().getUid()).collection(monthCollection).document();

                        Map<String, String> transaction = new HashMap<>();
                        transaction.put("date", dateText.getText().toString());
                        transaction.put("price", transactionPrice.getText().toString());
                        transaction.put("notes", transactionNotes.getText().toString());
                        Toast.makeText(EditTransaction.this, dateText.getText().toString() + transactionPrice.getText().toString() + transactionNotes.getText().toString(), Toast.LENGTH_SHORT).show();

                        CategoryText = findViewById(R.id.transaction_select_category);
                        String[] dates = dateText.getText().toString().split("-");
                        String luna = dates[0], zi = dates[1], an = dates[2];


                        HashMap<String, String> months = new HashMap<>();
                        String[] mnths = {"", "Jan", "Feb", "March", "April", "May", "June", "July", "August", "Sept", "Oct", "Nov", "Dec"};
                        for (int i = 1; i <= 12; i++) {
                            if (i < 10) {
                                months.put(Integer.toString(i), mnths[i]);
                            } else {

                                months.put(Integer.toString(i), mnths[i]);
                            }
                        }

                        Toast.makeText(EditTransaction.this, months.get(luna), Toast.LENGTH_SHORT).show();


                        TransactionDetails transactionDetails = new TransactionDetails(Integer.parseInt(zi), zi, months.get(luna), an, CategoryText.getText().toString(), (Integer) findViewById(R.id.category_image).getTag(), -Double.parseDouble(transactionPrice.getText().toString()), ref.getId());

                        // daca adaug bani dau cu plus
                        if (isPosivite)
                            transactionDetails.setAmount(-transactionDetails.getAmount());

                        ref.set(transactionDetails);

                        // update totalsum
                        HashMap<String, Double> sum = new HashMap<>();
                        sum.put("amount", totalSum + transactionDetails.getAmount());
                        db.document(firebaseAuth.getCurrentUser().getUid()).collection("Wallet").document("MainWallet").set(sum);

                        startActivity(new Intent(EditTransaction.this, MainActivity.class));

                    }

                }

            }
        });
    }

}
