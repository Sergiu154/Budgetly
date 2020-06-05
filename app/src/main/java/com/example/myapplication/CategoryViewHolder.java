package com.example.myapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

/**
 * viewHolder which will bind to the data found in a Category object
 */
public class CategoryViewHolder extends GroupViewHolder {
    private TextView mTextView;
    private ImageView imageView;

    public CategoryViewHolder(View itemView) {
        super(itemView);

        mTextView = itemView.findViewById(R.id.textView);
        imageView = itemView.findViewById(R.id.imageView);
    }

    // bind the data to the viewHolder
    public void bind(Category category) {
        mTextView.setText(category.getTitle());
        imageView.setImageResource(category.getImgSrc());
    }


}
