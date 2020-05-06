package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class ElementAdapter extends ExpandableRecyclerViewAdapter<CategoryViewHolder, ElementViewHolder> {
    public ElementAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public CategoryViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_recyclerview_category, parent, false);
        return new CategoryViewHolder(v);
    }

    @Override
    public ElementViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_recyclerview_element, parent, false);
        return new ElementViewHolder(v);
    }

    @Override
    public void onBindChildViewHolder(ElementViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Element element = (Element) group.getItems().get(childIndex);
        holder.bind(element);
    }

    @Override
    public void onBindGroupViewHolder(CategoryViewHolder holder, int flatPosition, ExpandableGroup group) {
        final Category category = (Category) group;
        holder.bind(category);
    }
}

