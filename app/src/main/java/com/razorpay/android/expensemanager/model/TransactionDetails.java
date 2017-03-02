package com.razorpay.android.expensemanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Anuja on 2/27/17.
 */

public class TransactionDetails implements Parcelable {
    @SerializedName("amount")
    int amount;

    @SerializedName("category")
    String category;
    @SerializedName("description")
    String description;
    @SerializedName("id")
    String id;
    @SerializedName("state")
    String state;
    @SerializedName("time")
    String time;


    protected TransactionDetails(Parcel in) {
        amount = in.readInt();
        category = in.readString();
        description = in.readString();
        id = in.readString();
        state = in.readString();
        time = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(amount);
        dest.writeString(category);
        dest.writeString(description);
        dest.writeString(id);
        dest.writeString(state);
        dest.writeString(time);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TransactionDetails> CREATOR = new Creator<TransactionDetails>() {
        @Override
        public TransactionDetails createFromParcel(Parcel in) {
            return new TransactionDetails(in);
        }

        @Override
        public TransactionDetails[] newArray(int size) {
            return new TransactionDetails[size];
        }
    };

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
