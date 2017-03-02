package com.razorpay.android.expensemanager.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.razorpay.android.expensemanager.R;
import com.razorpay.android.expensemanager.model.API;
import com.razorpay.android.expensemanager.model.ExpenseResponse;
import com.razorpay.android.expensemanager.model.TransactionDetails;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Anuja on 2/28/17.
 */

public class ExpenseRecyclerAdapter extends RecyclerView.Adapter<ExpenseRecyclerAdapter.ExpenseViewHolder> {
    private Context mContext;
    private ArrayList<TransactionDetails> transactionDetails;

    public ExpenseRecyclerAdapter(Context mContext, ArrayList<TransactionDetails> details) {
        this.mContext = mContext;
        transactionDetails = details;
    }

    private ExpenseRecyclerAdapter getInstance(){
        return this;
    }
    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_presenter, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder holder, final int position) {
        TransactionDetails transact = transactionDetails.get(position);
        TextView expId = (TextView) holder.itemView.findViewById(R.id.expense_id);
        TextView expType = (TextView) holder.itemView.findViewById(R.id.expense_type);
        TextView expAmnt = (TextView) holder.itemView.findViewById(R.id.expense_amount);
        TextView expDate = (TextView) holder.itemView.findViewById(R.id.expense_date);
        TextView expDesc = (TextView) holder.itemView.findViewById(R.id.expense_desc);
        TextView expVerfied = (TextView) holder.itemView.findViewById(R.id.verified);

        expId.setText(Html.fromHtml("Order Id: <b>" + transact.getId() + "</b>"));
        expType.setText(transact.getCategory());
        expAmnt.setText("\u20B9" + String.valueOf(transact.getAmount()));
        expDate.setText(getDateFormatted(transact.getTime()));
        expDesc.setText(transact.getDescription());
        expVerfied.setText(transact.getState());

        if (expVerfied.getText() != null) {
            if (expVerfied.getText().equals("verified")) {
                expVerfied.setTextColor(Color.rgb(50, 205, 50));
            } else if (expVerfied.getText().equals("unverified")) {
                expVerfied.setTextColor(Color.rgb(153, 153, 0));
            } else if (expVerfied.getText().equals("fraud")) {
                expVerfied.setTextColor(Color.rgb(255, 0, 0));
            }
        }

        expVerfied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRadioButtonDialog(position);
            }
        });

        LinearLayout.LayoutParams normalLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        LinearLayout.LayoutParams lastLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        if (position == transactionDetails.size() - 1) {
            holder.itemView.setLayoutParams(lastLayoutParams);
        } else {
            holder.itemView.setLayoutParams(normalLayoutParams);
        }
    }

    @Override
    public int getItemCount() {
        return transactionDetails.size();
    }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder {
        public ExpenseViewHolder(View itemView) {
            super(itemView);
        }
    }

    /*private String getDateFormatted(String time){

        String defaultTimezone = TimeZone.getDefault().getID();
        try {
            Date date = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).parse(time.replaceAll("Z$", "+0000"));
            return date.toString();
        } catch (ParseException e) {
            e.printStackTrace();
            return time;
        }
    }*/

    private void callApiMethod(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonblob.com").addConverterFactory(GsonConverterFactory.create()).build();
        //Creating an object of our api interface
        API request = retrofit.create(API.class);
        ExpenseResponse response = new ExpenseResponse();
        response.setTransactionDetails(transactionDetails);

        Call<ExpenseResponse> call = request.callApi(response);

        call.enqueue(new Callback<ExpenseResponse>() {
            @Override
            public void onResponse(Call<ExpenseResponse> call, Response<ExpenseResponse> response) {
                ExpenseResponse expenses = response.body();
            }

            @Override
            public void onFailure(Call<ExpenseResponse> call, Throwable t) {
                Log.d("Failed", t.getMessage().toString() + call.request().url());

            }
        });
    }
    private void showRadioButtonDialog(final int position) {

        // custom dialog
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.radiobutton_dialog);
        List<String> stringList = new ArrayList<>();  // here is list
        // for (int i = 0; i < 5; i++) {
        stringList.add("verified");
        stringList.add("fraud");
        stringList.add("unverified");
        // }
        RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radio_group);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int childCount = group.getChildCount();
                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) group.getChildAt(x);
                    if (btn.getId() == checkedId) {
                        Log.e("selected RadioButton->", btn.getText().toString());
                        transactionDetails.get(position).setState(btn.getText().toString());
                        getInstance().notifyDataSetChanged();
                        dialog.dismiss();
                        callApiMethod();
                    }
                }
            }
        });

        for (int i = 0; i < stringList.size(); i++) {
            RadioButton rb = new RadioButton(mContext); // dynamically creating RadioButton and adding to RadioGroup.
            rb.setText(stringList.get(i));
            rg.addView(rb);
        }

        dialog.show();

    }

    public String getDateFormatted(String time) {
        if (time != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (time.toUpperCase().contains("T") && time.toUpperCase().contains("Z")) {
                time = time.toUpperCase().replace("T", " ");
                String[] str = time.split("\\.");

                if (str.length != 0) {
                    return str[0];
                }
            }

            try {
                Date date = new Date(time);
                time = simpleDateFormat.format(date);
            } catch (Exception e) {
                //
            }
        }
        return time;
    }
}
