package net.icemix.testmobilemoney;

import android.arch.core.util.Function;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.icemix.mobilemoney.PaymentRequest;
import net.icemix.mobilemoney.UGMomoPaymentRequest;
import net.icemix.mobilemoney.network.models.StatusResponse;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String subscription = "9cbd68fb7e914089816299ba5d3a97b6";
        final String api_user = "e6f8972e-6781-11e5-a923-1681be663d3e";
        final String api_key = "c2d87feac94246c6898f116f62971a88";
        final EditText amount = findViewById(R.id.editTextAmount);
        final EditText phone = findViewById(R.id.editTextPhone);
        final TextView status = findViewById(R.id.textViewStatus);
        AppCompatButton btn = findViewById(R.id.btnPayNow);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UGMomoPaymentRequest ugMomoPaymentRequest = new UGMomoPaymentRequest();
                ugMomoPaymentRequest.setSubscriptionKey(subscription);
                ugMomoPaymentRequest.setApiUser(api_user);
                ugMomoPaymentRequest.setApiKey(api_key);
                ugMomoPaymentRequest.setAmount(Integer.parseInt(amount.getText().toString()));
                ugMomoPaymentRequest.setPhone(phone.getText().toString());
                ugMomoPaymentRequest.setCurrency("EUR");
                ugMomoPaymentRequest.setEnvironment("sandbox");
                ugMomoPaymentRequest.setExternalId("11212121122");
                ugMomoPaymentRequest.setPayerMessage("Message to payer");
                ugMomoPaymentRequest.setPayeeNote("Message to payee");

                status.setText("Payment started");
                ugMomoPaymentRequest.sendPayment();
                ugMomoPaymentRequest.setOnRequestCallbackListener(new UGMomoPaymentRequest.OnRequestCallbackListener() {
                    @Override
                    public void onComplete(StatusResponse response) {
                        Toast.makeText(getApplicationContext(),response.getStatus(),Toast.LENGTH_LONG).show();
                        status.setText("Payment completed");
                    }

                    @Override
                    public void onFail(String errorMessage) {
                        status.setText("Payment fail");
                        Log.e("ICEMIX_MOBILE_MONEY",errorMessage);
                    }

                    @Override
                    public void onPaymentFail(StatusResponse response, String errorMessage) {
                        status.setText("Payment fail: "+response.getReason().getMessage());
                    }

                    @Override
                    public void onStatusFail(Throwable throwable) {
                        status.setText("Payment fail");
                        Log.e("ICEMIX_MOBILE_MONEY",throwable.getMessage());
                    }

                    @Override
                    public void onAuthentificationSuccessful(String token) {
                        status.setText("Connected to payment server");

                    }

                    @Override
                    public void onAuthentificationFail(Throwable throwable) {
                        status.setText("Unable to connect to payment server");
                    }
                });
            }
        });
    }
}
