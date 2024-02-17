package com.akp.uniqpay.Basic;
/**
 * Created by Anoop pandey-9696381023.
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class API_Config {
    public static ApiService getApiClient_ByPost()
    {
        Gson gson = new GsonBuilder() .setLenient() .create();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(500, TimeUnit.SECONDS).writeTimeout(500, TimeUnit.SECONDS).connectTimeout(500, TimeUnit.SECONDS).build();
        OkHttpClient.Builder client1 = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client1.addNetworkInterceptor(loggingInterceptor);
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://api.uniqpay.in/Service/").addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient).client(client1.build());
        Retrofit retrofit = builder.build();
        return retrofit.create(ApiService.class);
    }
    public static ApiService getApiClient_ByPost1()
    {
        Gson gson = new GsonBuilder() .setLenient() .create();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(500, TimeUnit.SECONDS).writeTimeout(500, TimeUnit.SECONDS).connectTimeout(500, TimeUnit.SECONDS).build();
        OkHttpClient.Builder client1 = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client1.addNetworkInterceptor(loggingInterceptor);
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://ujjwalapi.signaturesoftware.org/Service/").addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient).client(client1.build());
        Retrofit retrofit = builder.build();
        return retrofit.create(ApiService.class);
    }
}
