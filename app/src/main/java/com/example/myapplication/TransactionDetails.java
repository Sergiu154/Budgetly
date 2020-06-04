package com.example.myapplication;

import java.util.Comparator;

public class TransactionDetails implements Comparable<TransactionDetails> {

    private int day;
    private String stringDay;
    private String month;
    private String year;
    private String category;
    private int categoryPath;
    private double amount;
    private String transactionId;

    // more data will be added later if necessary
    // maybe the source of the picture associated with the category
    public TransactionDetails() {
    }

    public String getStringDay() {
        return stringDay;
    }

    public void setStringDay(String stringDay) {
        this.stringDay = stringDay;
    }

    public TransactionDetails(int day, String stringDay, String month, String year, String category, int categoryPath, double amount,String transactionId) {
        this.day = day;
        this.stringDay = stringDay;
        this.month = month;
        this.year = year;
        this.category = category;
        this.categoryPath = categoryPath;
        this.amount = amount;
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCategory() {
        return category;
    }

    public int getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(int categoryPath) {
        this.categoryPath = categoryPath;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public int compareTo(TransactionDetails o) {
        return day - o.getDay();
    }
}
