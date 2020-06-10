package com.example.myapplication;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * A Category class which has the ability to expand itself generating a list of subcategories
 */
public class Category extends ExpandableGroup<Element> {
    // add the image of the parent category
    private int imgSrc;

    // constructor of the category
    public Category(String title, List<Element> items, int imgSrc) {
        super(title, items);
        this.imgSrc = imgSrc;
    }

    public int getImgSrc() {
        return imgSrc;
    }
}
