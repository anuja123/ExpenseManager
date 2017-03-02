package com.razorpay.android.expensemanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Anuja on 2/27/17.
 */

public class ExpenseResponse implements Parcelable {

    @SerializedName("expenses")
    private ArrayList<TransactionDetails> transactionDetails;

    public ExpenseResponse() {

    }

    public ExpenseResponse(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ArrayList<TransactionDetails> getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(ArrayList<TransactionDetails> transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public static final Creator<ExpenseResponse> CREATOR = new Creator<ExpenseResponse>() {
        @Override
        public ExpenseResponse createFromParcel(Parcel in) {
            return new ExpenseResponse(in);
        }

        @Override
        public ExpenseResponse[] newArray(int size) {
            return new ExpenseResponse[size];
        }
    };
}
