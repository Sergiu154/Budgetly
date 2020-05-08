package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.logging.LoggingMXBean;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 0;
    private static final String TAG = "LOGIN_ACTIVITY";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private TextInputEditText mEmail;
    private EditText mPassword;
    private SignInButton signInBtn;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        mEmail = findViewById(R.id.emailLogin);
        mPassword = findViewById(R.id.passwordLogin);
        signInBtn = findViewById(R.id.sign_in_button);

        firebaseAuth = FirebaseAuth.getInstance();

        createRequest();

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signIn();


            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
//                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                        }

                        // ...
                    }
                });
    }

    public void createRequest() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);


    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        System.out.println("AM AFISAT");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null) {

            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    public void onLogin(View view) {

        String mail = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

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

        firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    Toast.makeText(LoginActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        });
//        editor = sharedPreferences.edit();
//        editor.putBoolean("logged", true);
//        editor.apply();

    }

    public void onRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

}
