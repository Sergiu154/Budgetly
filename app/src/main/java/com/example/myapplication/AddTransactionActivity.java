package com.example.myapplication;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddTransactionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView dateText;

    /* main function */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_transaction_layout);
        setTransactionDate();
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
        String date = month + "/" + dayOfMonth + "/" + year;
        dateText.setText(date);
    }
    /* end of date selection and display */

    /* button for adding transaction */
    public void addTransactionButtonClick(View v) {
        if (dateText.getText().toString().matches("")) {
            Toast.makeText(AddTransactionActivity.this, "Select the date!", Toast.LENGTH_SHORT).show();
            return;
        }

        EditText transactionPrice = findViewById(R.id.transaction_price);
        if (transactionPrice.getText().toString().matches("")) {
            Toast.makeText(AddTransactionActivity.this, "Insert a price!", Toast.LENGTH_SHORT).show();
            return;
        }

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



