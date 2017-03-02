package com.razorpay.android.expensemanager.model;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;

/**
 * Created by Anuja on 2/27/17.
 */

public interface API {
    @GET("/api/jsonBlob/d8a6b9ce-fcc9-11e6-a0ba-fba242ce22dd")
    Call<ExpenseResponse> getExpenses();

    @PUT("/api/jsonBlob/d8a6b9ce-fcc9-11e6-a0ba-fba242ce22dd")
    Call<ExpenseResponse> callApi(@Body ExpenseResponse response);
}
