package com.labouralerts.webservice;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WSCallback {

    @POST(WSConstants.WS_METHOD_LOGIN)
    @FormUrlEncoded
    Call<ResponseBody> logIn(@Field(WSConstants.WS_PARAM_USER_NAME) String userId, @Field(WSConstants.WS_PARAM_PASSWORD) String userName,
                             @Field(WSConstants.WS_PARAM_DEVICE_ID) String userEmail);


    @POST(WSConstants.WS_METHOD_SIGN_UP)
    @FormUrlEncoded
    Call<ResponseBody> signUp(@Field(WSConstants.WS_PARAM_USER_EMAIL) String email,
                              @Field(WSConstants.WS_PARAM_STATE) String state,
                              @Field(WSConstants.WS_PARAM_USER_NAME) String userName,
                              @Field(WSConstants.WS_PARAM_SERVICE_ID) String service_id,
                              @Field(WSConstants.WS_PARAM_ADDRESS) String address1,
                              @Field(WSConstants.WS_PARAM_CITY) String city,
                              @Field(WSConstants.WS_PARAM_RENEW_DATE) String renew_date,
                              @Field(WSConstants.WS_PARAM_ZIP_CODE) String zip_code,
                              @Field(WSConstants.WS_PARAM_COUNTRY) String country,
                              @Field(WSConstants.WS_PARAM_PROFILE_STATUS) String profile_status,
                              @Field(WSConstants.WS_PARAM_DEVICE_ID) String device_id,
                              @Field(WSConstants.WS_PARAM_TRANSACTION_ID) String transaction_id,
                              @Field(WSConstants.WS_PARAM_TRANSACTION_DATE) String transaction_date,
                              @Field(WSConstants.WS_PARAM_PASSWORD) String password
    );

    @POST(WSConstants.WS_METHOD_CHECK_USER_NAME)
    @FormUrlEncoded
    Call<ResponseBody> checkUserName(@Field(WSConstants.WS_PARAM_USER_NAME) String userName);

    @POST(WSConstants.WS_METHOD_CHANGE_PASSWORD)
    @FormUrlEncoded
    Call<ResponseBody> changePassword(@Field(WSConstants.WS_PARAM_OLD_PASSWORD) String oldpwd,
                                      @Field(WSConstants.WS_PARAM_NEW_PASSWORD) String newpwd,
                                      @Field(WSConstants.WS_PARAM_USER_ID) String userId);

    @POST(WSConstants.WS_METHOD_BASIC_SEARCH)
    @FormUrlEncoded
    Call<ResponseBody> basicSearch(@Field(WSConstants.WS_PARAM_SEARCH_FEILD) String search,
                                   @Field(WSConstants.WS_PARAM_USER_ID) String userId);

    @POST(WSConstants.WS_METHOD_TOP_TEN)
    @FormUrlEncoded
    Call<ResponseBody> top10Result(@Field(WSConstants.WS_PARAM_USER_ID) String userId);

    @POST(WSConstants.WS_METHOD_SAVE_ALERT)
    @FormUrlEncoded
    Call<ResponseBody> saveAlert(@Field(WSConstants.WS_PARAM_BUSINESS_ID) String businessId,
                                 @Field(WSConstants.WS_PARAM_STATUS) String status,
                                 @Field(WSConstants.WS_PARAM_SERVICE) String service,
                                 @Field(WSConstants.WS_PARAM_USER_ID) String userID
    );

    @POST(WSConstants.WS_METHOD_DELETE_ALERT)
    @FormUrlEncoded
    Call<ResponseBody> deleteAlert(@Field(WSConstants.WS_PARAM_BUSINESS_ID) String businessId,
                                   @Field(WSConstants.WS_PARAM_SERVICE) String service,
                                   @Field(WSConstants.WS_PARAM_USER_ID) String userID
    );

    @POST(WSConstants.WS_METHOD_ADVANCED_SEARCH)
    @FormUrlEncoded
    Call<ResponseBody> advanceSearch(@Field(WSConstants.WS_PARAM_COMPANY_NAME) String companyName,
                                     @Field(WSConstants.WS_PARAM_CITY) String city,
                                     @Field(WSConstants.WS_PARAM_COUNTRY) String country,
                                     @Field(WSConstants.WS_PARAM_STATE) String state,
                                     @Field(WSConstants.WS_PARAM_EFFECTIVE_DATE_START) String startDate,
                                     @Field(WSConstants.WS_PARAM_EFFECTIVE_DATE_END) String EndDAte,
                                     @Field(WSConstants.WS_PARAM_USER_ID) String userID
    );

    @POST(WSConstants.WS_METHOD_NOTIFICATION)
    @FormUrlEncoded
    Call<ResponseBody> notification(@Field(WSConstants.WS_PARAM_USER_ID) String userID
    );

    @POST(WSConstants.WS_METHOD_ALERT_NOTIFICATION)
    @FormUrlEncoded
    Call<ResponseBody> alertNotification(@Field(WSConstants.WS_PARAM_USER_ID) String userID
    );

//    @GET(WSConstants.WS_METHOD_TOP_HEADLINES)
//    Call<ResponseBody> getGoogleNews(@Query(WSConstants.WS_PARAM_SOURCES) String sources, @Query(WSConstants.WS_PARAM_API_KEY) String apiKey);
}
