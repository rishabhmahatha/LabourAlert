package com.labouralerts.webservice;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Calls the the API.
 */
public class WSUtils {

    private static WSCallback wsCallback;
    private static WSCallback wsCallbackGoogleNews;

    /**
     * Static method to to get api client instance
     *
     * @return ApiCallback instance
     */
    public static WSCallback getClient() {

        if (wsCallback == null) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(WSConstants.WS_CONNECTION_TIMEOUT, TimeUnit.SECONDS);
            httpClient.readTimeout(WSConstants.WS_READ_TIMEOUT, TimeUnit.SECONDS);

            /**
             *  interceptor for printing logs of request and response
             */
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);  // <-- this is the important line!


            Retrofit client = new Retrofit.Builder().baseUrl(WSConstants.WS_BASE_API_URL).client(httpClient.build()).addConverterFactory(GsonConverterFactory.create()).build();
            wsCallback = client.create(WSCallback.class);
        }

        return wsCallback;
    }


    public static JSONObject getResponseJSON(final Response<ResponseBody> response) {

        try {
            String responseString = "";
            final ResponseBody body = response.body();
            final ResponseBody errorBody = response.errorBody();

            if (body != null) {
                responseString = body.string();
            } else if (errorBody != null) {
                responseString = errorBody.string();
            }


            return new JSONObject(responseString);

        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static boolean isJSONValid(String json) {
        try {
            new JSONObject(json);
            return true;
        } catch (JSONException ex) {
            return false;
        }
    }


}
