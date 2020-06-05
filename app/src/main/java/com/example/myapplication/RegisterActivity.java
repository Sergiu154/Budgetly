package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Register the user to the app and automatically create an empty wallet
 */
public class RegisterActivity extends AppCompatActivity {

    // email and password fields
    TextInputEditText mEmail;
    EditText mPassword;
    // refrence to the DB and to the current user
    FirebaseAuth firebaseAuth;
    private CollectionReference db = FirebaseFirestore.getInstance().collection("users");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        mEmail = findViewById(R.id.emailLogin);
        mPassword = findViewById(R.id.passwordLogin);

        // if the user if already logged in , skip this activity
        // and redirect him/her to the main page

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {

            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    // if the user reminds that he has already an account,
    // pressing the back button will take him to the login page
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // triggered when the register button is pressed
    public void onPressRegister(View view) {

        // get the input data
        String mail = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        // validate the fields
        if (TextUtils.isEmpty(mail)) {

            mEmail.setError("Email is required");
            return;
        }
        if (TextUtils.isEmpty(password)) {

            mPassword.setError("Password is required");
            return;
        }

        if (password.length() < 6) {
            mPassword.setError("Password should have at least 6 characters");
            return;
        }

        // register user into your app
        firebaseAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                // if it is ok
                if (task.isSuccessful()) {

                    // announce the user that the account has been created
                    Toast.makeText(RegisterActivity.this, "User created", Toast.LENGTH_SHORT).show();

                    // create an empty wallet and save it to the DB
                    Map<String, Object> amount = new HashMap<>();
                    amount.put("amount", 0.0);
                    db.document(firebaseAuth.getCurrentUser().getUid()).collection("Wallet").document("MainWallet").set(amount);

                    // redirect the user the the main page
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();

                }
                // in case of error , display and appropriate message
                else {

                    Toast.makeText(RegisterActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
