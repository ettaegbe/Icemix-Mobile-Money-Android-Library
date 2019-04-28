package net.icemix.mobilemoney;

import android.arch.core.util.Function;
import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import net.icemix.mobilemoney.network.ApiService;
import net.icemix.mobilemoney.network.RetrofitBuilder;
import net.icemix.mobilemoney.network.models.AccessToken;
import net.icemix.mobilemoney.network.models.ApiUser;
import net.icemix.mobilemoney.network.models.PayResponse;
import net.icemix.mobilemoney.network.models.StatusResponse;
import net.icemix.mobilemoney.network.models.UGPayer;
import net.icemix.mobilemoney.network.models.UGPayment;

import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HeaderMap;

public class UGMomoPaymentRequest {

    private String apiUser = null;
    private String apiKey = null;
    private String subscriptionKey = null;
    private String callbackUrl = null;
    private int amount = 0;
    private String currency = "EUR";
    private String externalId = "EUR";
    private String phone = "";
    private String partyType = "MSISDN";
    private String payerMessage = "Payment";
    private String environment = "sandbox";
    private String payeeNote = "Payment \n Amount: \n " + amount;
    private ApiService service;
    private String TAG = "ICEMIX_MOBILE_MONEY";
    private OnRequestCallbackListener listener;

    public UGMomoPaymentRequest() {
    }

    /**
     * @param apiUser : MOMO Api User key
     * @param apiKey MOMO Api Key
     * @param subscriptionKey MOMO Subscription key
     * @param amount Amount of money to charge
     * @param currency ISO currency code
     * @param phone Subscriber Mobile Number
     * @param partyType Subscriber party type Enum[MSISDN,EMAIL]
     * @param payerMessage Message to send to the payer
     * @param payeeNote Message to send to the payee
     */
    public UGMomoPaymentRequest(String apiUser, String apiKey, String subscriptionKey, int amount, String currency, String phone, String partyType, String payerMessage, String payeeNote,String externalId) {
        this.apiUser = apiUser;
        this.apiKey = apiKey;
        this.subscriptionKey = subscriptionKey;
        this.amount = amount;
        this.currency = currency;
        this.phone = phone;
        this.partyType = partyType;
        this.payerMessage = payerMessage;
        this.payeeNote = payeeNote;
        this.externalId = externalId;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getApiUser() {
        return apiUser;
    }

    public void setApiUser(String apiUser) {
        this.apiUser = apiUser;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSubscriptionKey() {
        return subscriptionKey;
    }

    public void setSubscriptionKey(String subscriptionKey) {
        this.subscriptionKey = subscriptionKey;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPartyType() {
        return partyType;
    }

    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }



    public OnRequestCallbackListener getListener() {
        return listener;
    }

    public void setListener(OnRequestCallbackListener listener) {
        this.listener = listener;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPayerMessage() {
        return payerMessage;
    }

    public void setPayerMessage(String payerMessage) {
        this.payerMessage = payerMessage;
    }

    public String getPayeeNote() {
        return payeeNote;
    }

    public void setPayeeNote(String payeeNote) {
        this.payeeNote = payeeNote;
    }

    public void sendPayment() {
        Map<String, String> headerMapToken = new HashMap<>();
        final String str = getApiUser()+":" + getApiKey();
        headerMapToken.put("Ocp-Apim-Subscription-Key", getSubscriptionKey());
        headerMapToken.put("Authorization", "Basic " + Base64.encodeToString(str.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP).trim());

        service = RetrofitBuilder.createServiceForToken(ApiService.class);
        Call<AccessToken> call = service.token(headerMapToken);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {

                if (response.isSuccessful()) {
                    /*Log.i(TAG, response.toString());
                    Log.e(TAG, "Token Received");
                    Log.e(TAG, "ApiKey "+getApiKey());
                    Log.e(TAG, "Subscription "+getSubscriptionKey());
                    Log.e(TAG, "Amount "+getAmount());
                    Log.e(TAG, "Phone "+getPhone());
                    Log.e(TAG, "Type "+getPartyType());
                    Log.e(TAG,"PayerMessage "+ getPayerMessage());
                    Log.e(TAG,"getPayeeNote "+ getPayeeNote());
                    Log.e(TAG, getApiKey());
                    Log.e(TAG, response.toString());*/
                    final String token = response.body() != null ? response.body().getAccess_token() : "";

                    final String reference_id = UUID.randomUUID().toString();
                    Map<String, String> headerMap = new HashMap<>();
                    headerMap.put("X-Reference-Id", reference_id);
                    headerMap.put("Content-Type", "application/json");

                    headerMap.put("Accept", "*/*");
                            /*.addHeader("Connection", "close")*/
                    headerMap.put("X-Target-Environment", getEnvironment());
                    headerMap.put("Ocp-Apim-Subscription-Key", getSubscriptionKey());
                    headerMap.put("Authorization", "Bearer "+token.trim());

                    ApiService servicePay = RetrofitBuilder.createServiceForPayment(ApiService.class);

                    UGPayment payment = new UGPayment(getAmount(),getCurrency(),getExternalId(),getPayerMessage(),getPayeeNote(),
                            new UGPayer(getPartyType(),getPhone()));

                    Call<Void> callPayment = servicePay.requestToPay(headerMap,payment);
                    callPayment.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()){
                                Log.e(TAG, "Response was successful");
                                Log.e(TAG, response.toString());
                                checkStatus(token,reference_id);

                            }else{
                                Log.e(TAG, "Response was not successful");
                                Log.e(TAG, response.toString());

                                if(response.errorBody() != null){
                                    Gson errorgson = new Gson();
                                    String json11 = errorgson.toJson(response.errorBody());
                                    try {
                                        listener.onFail(response.errorBody().string().concat(""));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e(TAG, "Api Payment failed");
                            listener.onFail(t.getMessage());
                        }
                    });
                } else {

                    Log.e(TAG, "Check was not successful");
                    listener.onFail(response.toString());
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {

                Log.e(TAG, "Api Token verification failed");
                Log.e(TAG, t.getMessage());
                listener.onAuthentificationFail(t);


            }
        });
    }
    public void checkStatus(final String token,final String reference_id){

        Log.e(TAG,"checking status");

        Map<String, String> headerMap = new HashMap<>();

        headerMap.put("Content-Type", "application/json");

        headerMap.put("Accept", "*/*");
        /*.addHeader("Connection", "close")*/
        headerMap.put("X-Target-Environment", getEnvironment());
        headerMap.put("Ocp-Apim-Subscription-Key", getSubscriptionKey());
        headerMap.put("Authorization", "Bearer "+token.trim());

        ApiService serviceStatus = RetrofitBuilder.createServiceForPayment(ApiService.class);

        final Call<StatusResponse> checkStatus = serviceStatus.checkStatus(headerMap,reference_id);
        checkStatus.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
               if(response.isSuccessful()){
                   if(response.body() != null){
                       String status = response.body().getStatus();
                       switch (status.toUpperCase()){
                           case "PENDING":
                               checkStatus(token,reference_id);
                               break;
                           case "SUCCESSFUL":
                               Log.e(TAG,"SUCCESSFUL");
                               break;
                           case "FAILED":
                               Log.e(TAG,"PAYMENT FAILED");
                               listener.onFail(response.body().getReason().getMessage());
                               break;
                       }
                       listener.onComplete(response.body());
                       /*try {

                           Log.e(TAG,response.body().getReason().getMessage());
                       } catch (Exception e) {
                           e.printStackTrace();
                       }*/
                   }else{
                       Log.e(TAG,"Unknown body");
                       Log.e(TAG, response.errorBody() != null ? response.errorBody().toString() : "");
                       listener.onFail(response.toString());
                   }
               }else{
                   Log.e(TAG,"Error checking status");
                   Log.e(TAG, response.errorBody() != null ? response.errorBody().toString() : "");
                   listener.onFail(response.toString());
               }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Log.e(TAG,"Failed checking status");
                Log.e(TAG, t.getMessage());
                listener.onStatusFail(t);
            }
        });
    }



    public void setOnRequestCallbackListener(OnRequestCallbackListener listener) {
        this.listener = listener;
    }

    public interface OnRequestCallbackListener{
        void onComplete(StatusResponse response);
        void onFail(String errorMessage);
        void onPaymentFail(@Nullable StatusResponse response, String errorMessage);
        void onStatusFail(Throwable throwable);
        void onAuthentificationSuccessful(String token);
        void onAuthentificationFail(Throwable throwable);
    }
}
