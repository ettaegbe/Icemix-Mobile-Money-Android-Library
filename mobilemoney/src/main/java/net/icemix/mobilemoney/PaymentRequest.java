package net.icemix.mobilemoney;

import android.arch.core.util.Function;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class PaymentRequest {
    private String token = null;
    private String user_id = null;
    private String api_key = null;
    private String subscription_key = null;
    private String callback_url = null;
    private int amount = 0;
    private String phone_number = null;
    private String currency = "EUR";
    private String status = "SUCCESS";
    private String payerMessage = "Payment";
    private String payeeNote = "Payment \n Amount: \n " + amount;


    PaymentRequest(){}

    public PaymentRequest(int amount, String phone_number, String currency, String payerMessage, String payeeNote) {
        this.amount = amount;
        this.phone_number = phone_number;
        this.currency = currency;
        this.payerMessage = payerMessage;
        this.payeeNote = payeeNote;
    }

    public PaymentRequest(int amount, String phone_number) {
        this.amount = amount;
        this.phone_number = phone_number;
    }

    public PaymentRequest(String user_id, String subscription_key) {
        this.user_id = user_id;
        this.subscription_key = subscription_key;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getSubscription_key() {
        return subscription_key;
    }

    public void setSubscription_key(String subscription_key) {
        this.subscription_key = subscription_key;
    }

    public String getCallback_url() {
        return callback_url;
    }

    public void setCallback_url(String callback_url) {
        this.callback_url = callback_url;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String sendPayment(Function<String, String> onComplete){
        return onComplete.apply(this.status);
    }
    public static void s(Context c, String message){
        Toast.makeText(c,message,Toast.LENGTH_LONG).show();;
    }
}
