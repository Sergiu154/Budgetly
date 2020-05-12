package com.example.myapplication;

import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import android.content.Context;


public class AccountFragment extends Fragment {


    public AccountFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_account,container,false);

        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // String nameCurrUser = currentUser.getDisplayName();
        String placeholderName = "placehorder tp ca nu exista nume";
        String emailCurrUSer = currentUser.getEmail();

        TextView nameTextView = view.findViewById(R.id.textView4);
        nameTextView.setText(placeholderName);

        TextView emailTextView = view.findViewById(R.id.textView5);
        emailTextView.setText(emailCurrUSer);

        Button signOutBut = view.findViewById(R.id.signOutBut);

        signOutBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
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
