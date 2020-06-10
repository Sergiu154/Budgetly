package com.example.myapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class ElementViewHolder extends ChildViewHolder implements View.OnClickListener {
    private TextView mTextView;
    private ImageView imageView;
    private String name;
    private int imgSrc;
    private RecyclerViewClickListener mListener;

    // get the references to the data from the view
    public ElementViewHolder(View itemView, RecyclerViewClickListener listener) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.textView);
        imageView = itemView.findViewById(R.id.imageView);
        mListener = listener;
        itemView.setOnClickListener(this);
    }

    // bind the data to the elements of the view
    public void bind(Element element, String n, int src) {
        mTextView.setText(element.name);
        imageView.setImageResource(element.getImgSrc());
        name = n;
        imgSrc = src;
    }

    // set the listener to each category to extend a sublist
    @Override
    public void onClick(View view) {
        mListener.onClick(view, name, imgSrc);
    }
}
