package com.razorpay.android.expensemanager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.razorpay.android.expensemanager.fragments.MainFragment;
import com.razorpay.android.expensemanager.fragments.RechargeFragment;
import com.razorpay.android.expensemanager.fragments.TaxiFragment;
import com.razorpay.android.expensemanager.model.API;
import com.razorpay.android.expensemanager.model.ExpenseResponse;
import com.razorpay.android.expensemanager.model.TransactionDetails;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.razorpay.android.expensemanager.fragments.MainFragment.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    private ExpenseResponse expenses, jsonResponse;
    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;
    private ArrayList<Fragment> vpFragments;
    private TabLayout tabLayout;
    private android.app.ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getExpenseDetails();


    }

    public void setTitle() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tab1");
    }

    public void getExpenseDetails() {
        //While the app fetched data we are displaying a progress dialog

        //Creating a rest adapter
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonblob.com").addConverterFactory(GsonConverterFactory.create()).build();
        //Creating an object of our api interface
        API request = retrofit.create(API.class);
        Call<ExpenseResponse> call = request.getExpenses();

        call.enqueue(new Callback<ExpenseResponse>() {
            @Override
            public void onResponse(Call<ExpenseResponse> call, Response<ExpenseResponse> response) {
                expenses = response.body();
                jsonResponse = response.body();
                updateResponse(expenses);
            }

            @Override
            public void onFailure(Call<ExpenseResponse> call, Throwable t) {
                Log.d("Failed", t.getMessage().toString() + call.request().url());

            }
        });
    }

    private ArrayList<Fragment> getFragments(ExpenseResponse response) {
        ArrayList<Fragment> fList = new ArrayList<Fragment>();
        fList.add(new MainFragment().newInstance("All", this, response.getTransactionDetails()));
        fList.add(new MainFragment().newInstance("Recharge", this, filterRechargeDetails(response)));
        fList.add(new MainFragment().newInstance("Taxi", this, filterTaxiDetails(response)));
        return fList;
    }

    private ArrayList<TransactionDetails> filterRechargeDetails(ExpenseResponse response) {
        ArrayList<TransactionDetails> rechargeTrasactions = new ArrayList<>();
        ArrayList<TransactionDetails> totalTrasactions = response.getTransactionDetails();

        for (TransactionDetails transactionDetails : totalTrasactions) {
            if (transactionDetails.getCategory().equals("Recharge"))
                rechargeTrasactions.add(transactionDetails);
        }
        return rechargeTrasactions;
    }

    private ArrayList<TransactionDetails> filterTaxiDetails(ExpenseResponse response) {
        ArrayList<TransactionDetails> taxiDetails = new ArrayList<>();
        ArrayList<TransactionDetails> totalTransactions = response.getTransactionDetails();
        for (TransactionDetails transactionDetails : totalTransactions) {
            if (transactionDetails.getCategory().equals("Taxi")) {
                taxiDetails.add(transactionDetails);
            }
        }
        return taxiDetails;
    }

    private ExpenseResponse getResponse() {
        return jsonResponse;
    }

    private void updateResponse(ExpenseResponse response) {
        //getFragmentManager().beginTransaction().replace()
        ArrayList<Fragment> fragments = getFragments(response);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Expense Manager");
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(2);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);


        mViewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //    Log.d("Page Scrolled" , position + "");
            }

            @Override
            public void onPageSelected(int position) {
                mViewPager.getAdapter().notifyDataSetChanged();
                Log.d("Page Selected" , position + "");

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            //    Log.d("Page Scroll State" , state + "");

            }
        });
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> fragments;

        public ViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {

            if (this.fragments.get(position) instanceof MainFragment) {
                MainFragment mf = (MainFragment) this.fragments.get(position);
                if (mf.getAdapter() != null)
                    mf.getAdapter().notifyDataSetChanged();
            }
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            MainFragment mf = null;
            RechargeFragment rf = null;
            TaxiFragment tf = null;
            Fragment fragment = fragments.get(position);
            String s = fragment.getArguments().getString(EXTRA_MESSAGE);

            return s;

            /*if(fragments.get(position) instanceof  MainFragment){
                mf =(MainFragment) fragments.get(position);
                return mf.getTitle();
            } else if(fragments.get(position) instanceof  RechargeFragment){
                rf = (RechargeFragment) fragments.get(position);
                return rf.getTitle();
            } else if(fragments.get(position) instanceof TaxiFragment){
                tf = (TaxiFragment) fragments.get(position);
                return tf.getTitle();
            }
            else
                return "All";*/

        }
    }

}
