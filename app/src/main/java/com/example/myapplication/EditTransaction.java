package com.example.myapplication;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditTransaction extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_transaction_layout);
        TextView textView = findViewById(R.id.edit_transaction_id);
        String transactionId = getIntent().getExtras().getString("id");
        textView.setText(transactionId);


    }



}
