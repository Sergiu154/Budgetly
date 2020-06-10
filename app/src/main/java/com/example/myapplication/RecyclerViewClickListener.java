package com.example.myapplication;


import android.view.View;

// interface which is used to implement the click event
// on the items from the SelectCategory activity
public interface RecyclerViewClickListener {

    void onClick(View view, String name, int imgSrc);
}