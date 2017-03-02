package com.razorpay.android.expensemanager.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.razorpay.android.expensemanager.MainActivity;
import com.razorpay.android.expensemanager.R;
import com.razorpay.android.expensemanager.adapter.ExpenseRecyclerAdapter;
import com.razorpay.android.expensemanager.model.ExpenseResponse;
import com.razorpay.android.expensemanager.model.TransactionDetails;

import java.util.ArrayList;

/**
 * Created by Anuja on 2/28/17.
 */

public class MainFragment extends Fragment {
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    public static final String RESPONSE_DATA = "data";

    private RecyclerView recyclerView;
    private  ArrayList<TransactionDetails> mDetails;
    private ArrayList<TransactionDetails> transactionDetails = new ArrayList<>();
    private String msg ;
    private ExpenseRecyclerAdapter mAdapter;
    private  Context mContext;

    public MainFragment newInstance(String msg , Context context , ArrayList<TransactionDetails> details) {
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_MESSAGE , msg);
        bundle.putParcelableArrayList(RESPONSE_DATA , details);
        fragment.setArguments(bundle);
        mDetails = details;
        mContext = context;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.expense_details, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_expenses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        transactionDetails = getArguments().getParcelableArrayList(RESPONSE_DATA);

        mAdapter = new ExpenseRecyclerAdapter(getContext(),transactionDetails);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();



        return view;
    }
    public ExpenseRecyclerAdapter getAdapter(){
        return mAdapter;
    }

    public String getTitle(){
        return "All";
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
