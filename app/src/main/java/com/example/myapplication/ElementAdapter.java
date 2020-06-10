package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * adapter class used to map the data from the Element class to the adapter to
 * display it to the user
 */
public class ElementAdapter extends ExpandableRecyclerViewAdapter<CategoryViewHolder, ElementViewHolder> {
    private RecyclerViewClickListener mListener;

    // set a listener to each element to extend a sublist of categories
    public ElementAdapter(List<? extends ExpandableGroup> groups, RecyclerViewClickListener listener) {
        super(groups);
        mListener = listener;
    }

    // inflate the data intro the layout
    @Override
    public CategoryViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_recyclerview_category, parent, false);
        return new CategoryViewHolder(v);
    }

    // create child view where the user will find the list of subclasses
    @Override
    public ElementViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_recyclerview_element, parent, false);
        return new ElementViewHolder(v, mListener);
    }

    // bind the child list to the parent
    @Override
    public void onBindChildViewHolder(ElementViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Element element = (Element) group.getItems().get(childIndex);
        holder.bind(element, element.getName(), element.getImgSrc());
    }

    // bind the parent category to the view
    @Override
    public void onBindGroupViewHolder(CategoryViewHolder holder, int flatPosition, ExpandableGroup group) {
        final Category category = (Category) group;
        holder.bind(category);
    }
}

