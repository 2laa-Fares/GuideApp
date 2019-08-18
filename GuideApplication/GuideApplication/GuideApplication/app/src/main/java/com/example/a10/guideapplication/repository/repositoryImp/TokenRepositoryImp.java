package com.example.a10.guideapplication.repository.repositoryImp;

import android.content.Context;

import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.app.Const;
import com.example.a10.guideapplication.model.Token;
import com.example.a10.guideapplication.network.ApiService;
import com.example.a10.guideapplication.repository.TokenRepository;
import com.example.a10.guideapplication.view.TokenListener;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenRepositoryImp implements TokenRepository{

    ApiService apiService;
    private static int REQUEST_TIMEOUT = 60;
    private static OkHttpClient okHttpClient;
    public TokenRepositoryImp(){
        initOkHttp(App.getContext());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.BASE_URL_TOKEN)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }


    @Override
    public void getToken(String userName, String password, String grantType, final TokenListener listener) {
        Call<Token> tokenCall = apiService.getToken(userName, password, grantType);
        tokenCall.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.body()!=null){
                    listener.token(response.body());
                }else {
                    listener.token(null);
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                listener.token(null);
            }
        });
    }

    private static void initOkHttp(final Context context) {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.addInterceptor(interceptor);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder;
                requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded");

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        okHttpClient = httpClient.build();
    }
}
