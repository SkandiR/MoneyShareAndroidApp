package com.example.moneyshare;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {
    @GET("isNewUser")
    Call<JsonData.IsNewUser> isNewUser(@Query("id") String id);

    @GET("getWalletAmount")
    Call<JsonData.WalletResponse> getWalletAmount(@Query("id") String id);

    @GET("getLentRequests")
    Call<JsonData.lentDetailsList> getLentRequests(@Query("id") String id);

    @GET("getBorrowRequests")
    Call<JsonData.BorrowRequests> getBorrowRequest(@Query("id") String id);

    @POST("saveUser")
    Call<JsonData.SaveUser> saveUser(@Body JsonData.SaveUser user_details);

    @POST("addMoney")
    Call<JsonData.addMoney> addMoney(@Body JsonData.addMoney add_money);

    @POST("addLendRequest")
    Call<JsonData.LendRequest> addLendRequest(@Body JsonData.LendRequest lend_request);

    @POST("addBorrowRequest")
    Call<JsonData.BorrowRequest> addBorrowRequest(@Body JsonData.BorrowRequest borrow_request);

    @POST("acceptLentRequest")
    Call<JsonData.AcceptLendRequest> acceptLentRequest(@Body JsonData.AcceptLendRequest acceptLendRequest);

    @POST("settleBorrowRequest")
    Call<JsonData.AcceptLendRequest> settleBorrowRequest(@Body JsonData.AcceptLendRequest settleBorrowRequest);
}
