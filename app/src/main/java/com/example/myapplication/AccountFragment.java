package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Fragment which is inflated into the main view then the option button "Account"
 * from the bottom menu is pressed
 * It displays basic information about the user.
 * The user has the option to change the colour theme to dark mode.
 * The user can log out from the app.
 * About the app button.
 */
public class AccountFragment extends Fragment {


    public AccountFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_account, container, false);

        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        String emailCurrUSer = currentUser.getEmail();
        int index = emailCurrUSer.indexOf('@');
        String username = emailCurrUSer.substring(0, index);

        TextView nameTextView = view.findViewById(R.id.textView4);
        nameTextView.setText(username);

        TextView emailTextView = view.findViewById(R.id.textView5);
        emailTextView.setText(emailCurrUSer);

        final TextView about = view.findViewById(R.id.textView2);
        about.setVisibility(View.INVISIBLE);
        Button signOutBut = view.findViewById(R.id.signOutBut);
        Button darkModeButton = view.findViewById(R.id.darkModeButton);
        Button lightModeButton = view.findViewById(R.id.lightModeButton);
        Button aboutButton = view.findViewById(R.id.aboutButton);

        signOutBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        darkModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });
        lightModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
        aboutButton.setOnClickListener(new View.OnClickListener() {
            boolean visible;

            @Override
            public void onClick(View view) {
                visible = !visible;
                about.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        });
        return view;
    }

    public static AccountFragment newInstance() {

        Bundle args = new Bundle();

        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
