package com.razorpay.android.expensemanager.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.razorpay.android.expensemanager.R;

/**
 * Created by Anuja on 2/28/17.
 */

public class RechargeFragment extends Fragment {

    public static final String MESSAGE = "MESSAGE";
    private String msg ;
    private static Context mContext ;

    public static RechargeFragment newInstance(String msg , Context context) {
        RechargeFragment fragment = new RechargeFragment();
        mContext = context;
        Bundle args = new Bundle();
        args.putString(MESSAGE, msg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            msg = getArguments().getString(MESSAGE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  super.onCreateView(inflater, container, savedInstanceState);


        return inflater.inflate(R.layout.expense_details ,container,false);
    }

    public String getTitle(){
        return "Recharge";
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


}
