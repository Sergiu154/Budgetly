package com.example.myapplication;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Category extends ExpandableGroup<Element> {
    private int imgSrc;
    public Category(String title, List<Element> items, int imgSrc) {
        super(title, items);
        this.imgSrc = imgSrc;
    }

    public int getImgSrc() {
        return imgSrc;
    }
}
