package net.icemix.mobilemoney.network;


import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;


public class RetrofitBuilder {

    private static final String BASE_URL = "https://ericssonbasicapi2.azure-api.net/";

    private final static OkHttpClient client = buildClient();
    private final static Retrofit retrofit = buildRetrofit(client);

    private static OkHttpClient buildClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();

                        Request.Builder builder = request.newBuilder()
                                .addHeader("Content-Type", "application/json")
                                /*.addHeader("Connection", "close")*/
                                .addHeader("X-Reference-Id", UUID.randomUUID().toString())
                                /*.addHeader("Authorization", "Bearer ")*/
                                .addHeader("X-Target-Environment", "sandbox")
                                .addHeader("Ocp-Apim-Subscription-Key", "9cbd68fb7e914089816299ba5d3a97b6");

                        request = builder.build();

                        return chain.proceed(request);

                    }
                });

       /* if(BuildConfig.DEBUG){
            builder.addNetworkInterceptor(new StethoInterceptor());
        }*/

        return builder.build();

    }

    private static Retrofit buildRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }

    public static <T> T createService(Class<T> service) {
        return retrofit.create(service);
    }


    public static <T> T createServiceWithAuth(Class<T> service, final TokenManager tokenManager) {

        OkHttpClient newClient = client.newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();

                Request.Builder builder = request.newBuilder();

                // if(tokenManager.getToken().getAccessToken() != null){
                if (!tokenManager.getToken().getAccess_token().isEmpty()) {
                    builder.addHeader("Authorization", "Bearer " + tokenManager.getToken().getAccess_token());
                }
                request = builder.build();
                return chain.proceed(request);
            }
        }).authenticator(CustomAuthenticator.getInstance(tokenManager)).build();

        Retrofit newRetrofit = retrofit.newBuilder().client(newClient).build();
        return newRetrofit.create(service);

    }

    public static <T> T createServiceForUGMOMO(Class<T> service, final TokenManager tokenManager) {

        OkHttpClient newClient = client.newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();

                Request.Builder builder = request.newBuilder();

                // if(tokenManager.getToken().getAccessToken() != null){
                if (!tokenManager.getToken().getAccess_token().isEmpty()) {
                    builder.addHeader("Authorization", "Bearer " + tokenManager.getToken().getAccess_token());
                }
                request = builder.build();
                return chain.proceed(request);
            }
        }).authenticator(CustomAuthenticator.getInstance(tokenManager)).build();

        Retrofit newRetrofit = retrofit.newBuilder().client(newClient).build();
        return newRetrofit.create(service);

    }

    public static <T> T createServiceForPayment(Class<T> service) {

        OkHttpClient newClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();

                Request.Builder builder = request.newBuilder();
//                builder
//                        .addHeader("X-Reference-Id", "e6f8972e-6781-11e5-a924-1601be663d3e")
//                        .addHeader("Content-Type", "application/json")
//                        .addHeader("Content-Length", String.valueOf(request.body().contentLength()))
//                        .addHeader("Accept", "*/*")
//                        .addHeader("Connection", "close")
//                        .addHeader("Authorization", "Bearer "+token.trim())
//                        .addHeader("X-Target-Environment", "sandbox")
//                        .addHeader("Ocp-Apim-Subscription-Key", "9cbd68fb7e914089816299ba5d3a97b6");

                request = builder.build();
                return chain.proceed(request);
            }
        }).build();

        Retrofit ret = new Retrofit.Builder()
                .baseUrl(Uri.parse(BASE_URL+"collection/v1_0/").toString())
                .client(newClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return ret.create(service);
        //Retrofit newRetrofit = retrofit.newBuilder().client(newClient).build();
        //  Retrofit newRetrofit = buildRetrofit(newClient);
        //return newRetrofit.create(service);

    }
    public static <T> T createServiceForToken(Class<T> service) {

        OkHttpClient newClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();

                Request.Builder builder = request.newBuilder();
                //builder
                       /* .addHeader("Accept", "application/json")*/
                        /*.addHeader("Authorization", "Basic " + Base64.encodeToString(str.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP).trim())
                        .addHeader("Ocp-Apim-Subscription-Key", "9cbd68fb7e914089816299ba5d3a97b6");*/

                request = builder.build();
                return chain.proceed(request);
            }
        }).build();

        Retrofit ret = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(newClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return ret.create(service);
        //Retrofit newRetrofit = retrofit.newBuilder().client(newClient).build();
        //  Retrofit newRetrofit = buildRetrofit(newClient);
        //return newRetrofit.create(service);

    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}
