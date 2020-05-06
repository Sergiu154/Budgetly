package com.example.myapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class ElementViewHolder extends ChildViewHolder {
    private TextView mTextView;
    private ImageView imageView;

    public ElementViewHolder(View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.textView);
        imageView = itemView.findViewById(R.id.imageView);
    }
    public void bind(Element element){
        mTextView.setText(element.name);
        imageView.setImageResource(element.getImgSrc());
    }
}
