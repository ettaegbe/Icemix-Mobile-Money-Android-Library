package net.icemix.mobilemoney.network;



import com.google.gson.Gson;
import com.google.gson.JsonElement;

import net.icemix.mobilemoney.network.models.AccessToken;
import net.icemix.mobilemoney.network.models.ApiUser;
import net.icemix.mobilemoney.network.models.PayResponse;
import net.icemix.mobilemoney.network.models.StatusResponse;
import net.icemix.mobilemoney.network.models.UGPayment;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("collection/token/")
    Call<AccessToken> token(@HeaderMap Map<String,String> headers);

    @POST("collection/accesstoken/")
    Call<AccessToken> accesstoken();

    @POST("v1_0/apiuser")
    Call<ApiUser> apiUser(@Field("providerCallbackHost") String providerCallbackHost);

    @POST("requesttopay")
    Call<Void> requestToPay(@HeaderMap Map<String, String> headers, @Body UGPayment body);
    @GET("requesttopay/{referenceId}")
    Call<StatusResponse> checkStatus(@HeaderMap Map<String, String> headers, @Path("referenceId") String referenceId);

    /*@POST("login")
    @FormUrlEncoded
    Call<AccessToken> login(@Field("username") String username, @Field("password") String password);

    @POST("social_auth")
    @FormUrlEncoded
    Call<AccessToken> socialAuth(@Field("name") String name,
                                 @Field("email") String email,
                                 @Field("provider") String provider,
                                 @Field("provider_user_id") String providerUserId);

    @POST("refresh")
    @FormUrlEncoded
    Call<AccessToken> refresh(@Field("refresh_token") String refreshToken);*/

    /*@GET("ads")
    Call<AdResponse> ads();

    @GET("apps")
    Call<AppResponse> apps();

    @GET("ad/coordinates/{ad}")
    Call<CoordinateResponse> adCoordinates(@Path("ad") int ad);

    @GET("app/coordinates/{app}")
    Call<CoordinateResponse> appCoordinates(@Path("app") int app);
    @GET("top")
    Call<CoordinateResponse> top();*/

}
