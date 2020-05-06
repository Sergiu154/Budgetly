package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.ViewHolder> {

    // list of transactionDetails of the current month
    // data will be
    private List<TransactionDetails> currentMonthTransactions;
    private Context context;

    public RecylerViewAdapter(List<TransactionDetails> currentMonthTransactions, Context context) {
        this.currentMonthTransactions = currentMonthTransactions;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TransactionDetails transactionDetails = currentMonthTransactions.get(position);
        holder.intDay.setText(String.valueOf(transactionDetails.getDay()));
        holder.stringDay.setText(transactionDetails.getStringDay());
        holder.category.setText(transactionDetails.getCategory());
        holder.amount.setText(String.format("%.1f", -transactionDetails.getAmount()));
        holder.monthYear.setText(transactionDetails.getMonth() + " " + transactionDetails.getYear());
        holder.categoryPhoto.setImageResource(transactionDetails.getCategoryPath());


    }

    @Override
    public int getItemCount() {
        return currentMonthTransactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView intDay;
        TextView stringDay;
        TextView monthYear;
        TextView amount;
        TextView category;
        ImageView categoryPhoto;
        ConstraintLayout relativeLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category);
            intDay = itemView.findViewById(R.id.transactionIntDay);
            stringDay = itemView.findViewById(R.id.transactionStringDay);
            amount = itemView.findViewById(R.id.transactionAmount);
            monthYear = itemView.findViewById(R.id.monthYear);
            relativeLayout = itemView.findViewById(R.id.relative_layout);
            categoryPhoto = itemView.findViewById(R.id.catergoryPhoto);
        }
    }
}
