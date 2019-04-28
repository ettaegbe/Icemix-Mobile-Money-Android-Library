# Icemix-Mobile-Money-Android-Library
This a mobile money library for collecting payment through mobile money payment with android particularly for Africa. The aim is that the merchant communicate directly with the main mobile money provider rather than a third party.
This version supports only MTN Uganda and other Countries Under same services of MTN Uganda


## Getting Started

### Prerequisites


### Installing

Add it to your build.gradle with:

```
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

and

```
dependencies {
    implementation 'com.github.ettaegbe:Icemix-Mobile-Money-Android-Library:1.0.0'
}
```
Add Internet Permissions to your Android Manifest file

```
<uses-permission android:name="android.permission.INTERNET"/>
```
Initialize the library for UGANDA and other countries using the same MTN Mobile Money service as Uganda

```
UGMomoPaymentRequest ugMomoPaymentRequest = new UGMomoPaymentRequest();
```

And that's it! You now have access to the library functions and can make mobile money payments programatically!


## A Simple Example

Have a user receive the mobile money USSD prompt after clicking a button.

```
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String subscription = "2cbd35b7e91tr981629ba5d3a97b6";
        final String api_user = "e6fdf72e-6781-11e5-a9g3-1681bejg63dfe";
        final String api_key = "c2d87feac94246c3649f116f62971a88";
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
```

That's it! You should now be ready to use the API



## Authors

* **ETTA EGBE JOSEPH** - *Initial work* - [Yo (U) Ltd](https://etta.icemix.net)


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

 * GUY NONO
  * MTN MOBILE MONEY UGANDA
