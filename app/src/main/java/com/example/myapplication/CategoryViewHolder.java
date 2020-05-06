package com.example.myapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;


/*
 * Copyright (C) 2018 Levi Rizki Saputra (levirs565@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created by LEVI on 22/09/2018.
 */
public class CategoryViewHolder extends GroupViewHolder {
    private TextView mTextView;
    private ImageView imageView;

    public CategoryViewHolder(View itemView) {
        super(itemView);

        mTextView = itemView.findViewById(R.id.textView);
        imageView = itemView.findViewById(R.id.imageView);
    }

    public void bind(Category category) {
        mTextView.setText(category.getTitle());
        imageView.setImageResource(category.getImgSrc());
    }


}
