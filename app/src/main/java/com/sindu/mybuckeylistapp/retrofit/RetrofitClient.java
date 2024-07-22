package com.sindu.mybuckeylistapp.retrofit;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private final APIServiceInterface apiServiceInterface;
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            Log.v("RetrofitClient", message);
            if (message.contains("false") || message.contains("operation failed")) {
                Log.v("RetrofitClient", message);
            }else {
                Log.v("RetrofitClient", message);
            }
        }
    }).setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .cache(null)
            .connectTimeout(900, TimeUnit.SECONDS)
            .readTimeout(900, TimeUnit.SECONDS)
            .writeTimeout(900, TimeUnit.SECONDS)
            .build();


    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BaseUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        apiServiceInterface = retrofit.create(APIServiceInterface.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public APIServiceInterface getApiServiceInterface() {
        return apiServiceInterface;
    }


}
