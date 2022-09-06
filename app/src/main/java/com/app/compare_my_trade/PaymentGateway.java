package com.app.compare_my_trade;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.compare_my_trade.ui.postauthenticationui.HomeActivity;
import com.app.compare_my_trade.utils.PreferenceUtils;
import com.github.ybq.android.spinkit.SpinKitView;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.model.CardParams;
import com.stripe.android.model.Token;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class PaymentGateway extends AppCompatActivity {

    private Stripe stripe;



    ImageView imageView;
    TextView plan_name,plan_price,plan_type;
    String Id;

    EditText card_year,card_month,card_name,card_cvv,card_number;
    ImageView back;
    TextView change_plan;

    SpinKitView spinKitView;

    Context mContext;
    TextView error1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        Intent i = getIntent();

        String Name = i.getStringExtra("Name");
        String Price = i.getStringExtra("Price");
        String Type = i.getStringExtra("Type");
        Id = i.getStringExtra("Id");




        imageView = findViewById(R.id.image);
        plan_name= findViewById(R.id.plan_name);
        plan_price= findViewById(R.id.plan_price);
        plan_type= findViewById(R.id.plan_type);

        card_year = findViewById(R.id.card_year);
        card_month = findViewById(R.id.card_month);
        card_name = findViewById(R.id.card_name);
        card_number = findViewById(R.id.card_number);
        card_cvv = findViewById(R.id.card_cvv);
        error1 =findViewById(R.id.error);

        back = findViewById(R.id.back);
        change_plan = findViewById(R.id.change_plan);

        spinKitView =  findViewById(R.id.progressBar);




        addTextWatcher(card_number);
        addTextWatcher(card_year);
        addTextWatcher(card_month);
        addTextWatcher(card_cvv);
        addTextWatcher(card_name);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(PaymentGateway.this,PlanDetails.class);
                startActivity(i);
            }
        });


        change_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(PaymentGateway.this,PlanDetails.class);
                startActivity(i);
            }
        });



        SpinKitView spinKitView = findViewById(R.id.progressBar);


        plan_name.setText(Name);
        plan_price.setText(Price);
        plan_type.setText(Type);






        PaymentConfiguration.init(getApplicationContext(), "pk_test_51JySFSSHDthITBpOsyZMyZjJ7ZdBXWm7cZwSqROD3SlSnfT1tSZLWLSzEaI1qSMR8SRQRcSrZCFbmOjZaMRPycuI00919640WI"); // Get your key here: https://stripe.com/docs/keys#obtain-api-keys

        // Hook up the pay button to the card widget and stripe instance
        Button payButton = findViewById(R.id.payButton);

        payButton.setOnClickListener((View view) -> {

            error1.setVisibility(View.GONE);

            String name,number,cvv,smonth,syear;
            int month,year;

            name = card_name.getText().toString();
            number = card_number.getText().toString();
            cvv = card_cvv.getText().toString();
            smonth= card_month.getText().toString();
            syear = card_year.getText().toString();

            if (number.isEmpty()){
                error1.setVisibility(View.VISIBLE);
                error1.setText("The card number field is required.");
            }else if (card_number.getText().length() != 16 ){
                error1.setVisibility(View.VISIBLE);
                error1.setText("The card number should be 16 digits.");
            } else if (smonth.isEmpty()) {
                error1.setVisibility(View.VISIBLE);
                error1.setText("The card month field is required.");

            }else if (Integer.parseInt(smonth) > 12 ){
                error1.setVisibility(View.VISIBLE);
                error1.setText("The card month must be less than 12.");
            }else if (syear.isEmpty()) {
                error1.setVisibility(View.VISIBLE);
                error1.setText("The card year field is required.");
            }else if (cvv.isEmpty()) {
                error1.setVisibility(View.VISIBLE);
                error1.setText("The card cvv field is required.");
            }else if (card_cvv.getText().length() != 3 ){
                error1.setVisibility(View.VISIBLE);
                error1.setText("The card cvv should be 3 digits.");

            } else if (name.isEmpty()){
                error1.setVisibility(View.VISIBLE);
                error1.setText("The card name field is required.");

            }else {
                month=Integer.parseInt(smonth);
                year=Integer.parseInt(syear);


                CardParams card = new CardParams(number, month, year, cvv, name);
                if (card != null) {
                    payButton.setEnabled(false);
                    spinKitView.setVisibility(View.VISIBLE);

                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    // Create a Stripe Token from the card details
                    stripe = new Stripe(getApplicationContext(), PaymentConfiguration.getInstance(getApplicationContext()).getPublishableKey());
                    stripe.createCardToken(card, new ApiResultCallback<Token>() {
                        @Override
                        public void onSuccess(@NonNull Token result) {
                            // Send the token identifier to the server
                            String tokenID = result.getId();
//                        Toast.makeText(view.getContext(),tokenID, Toast.LENGTH_SHORT).show();
                            Log.i("edfghjerf",tokenID);


                            String URL = "http://motortraders.zydni.com/api/sellers/subscription-create/" + Id;

                            RequestQueue queue = Volley.newRequestQueue(view.getContext());
                            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    spinKitView.setVisibility(View.GONE);
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    if (response.equals("true")) {
                                        Toast.makeText(view.getContext(), "Success", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(view.getContext(), HomeActivity.class);
                                        view.getContext().startActivity(intent);
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    spinKitView.setVisibility(View.GONE);
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    payButton.setEnabled(true);

                                    Log.i("edfghjerf",tokenID);
                                    if (error.toString().equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")){
                                        Toast.makeText(PaymentGateway.this,"Check your internet connection", Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(PaymentGateway.this,"Error",Toast.LENGTH_LONG).show();
                                        error1.setVisibility(View.VISIBLE);
                                        error1.setText("Please check your card");
                                    }
                                    Charset charset = Charset.defaultCharset();
                                    String str = new String(error.networkResponse.data,charset);
//                                Toast.makeText(view.getContext(),error.toString(), Toast.LENGTH_SHORT).show();
                                    Log.i("edfghjerf",str.toString());
                                }
                            }) {
                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("Accept", "application/json");
                                    params.put("Authorization", "Bearer " + PreferenceUtils.getTokan(view.getContext()));

                                    return params;
                                }

                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("token", tokenID);

                                    return params;

                                }
                            };
                            request.setRetryPolicy(new DefaultRetryPolicy(
                                    10000,
                                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                            queue.add(request);


//
//                        JSONObject jsonObject = new JSONObject();
//
//                        try {
//                            jsonObject.put("token",tokenID);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        Str jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//
//                                Toast.makeText(view.getContext(),response.toString(), Toast.LENGTH_SHORT).show();
//                                Log.i("edfghjerf",response.toString());
//
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//
//
//
////                                Charset charset = Charset.defaultCharset();
////                                String str = new String(error.networkResponse.data,charset);
//
//                                Toast.makeText(view.getContext(),error.toString(), Toast.LENGTH_SHORT).show();
//                                Log.i("edfghjerf",error.toString());
////
//                            }
//                        }){
//                            @Override
//                            public Map<String, String> getHeaders() throws AuthFailureError {
//                                Map<String, String> params = new HashMap<String, String>();
//                                params.put("Accept", "application/json");
//                                params.put("Authorization", "Bearer 645|Hc15vnLGnRXbc2cGVTWO5n5eYxVend1X7n27FtiL");
//
//                                return params;
//                            }
////                            @Override
////                            protected Map<String, String> getParams() throws AuthFailureError{
////                                Map<String, String> params = new HashMap<String, String>();
////                                params.put("token", tokenID);
////
////                                return params;
////
////                            }
//                        };
//                        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
//                                10000,
//                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                        queue.add(jsonArrayRequest);


                        }


                        @Override
                        public void onError(@NonNull Exception e) {
                            spinKitView.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            payButton.setEnabled(true);
                            error1.setVisibility(View.VISIBLE);
                            error1.setText("Please check your card details");
//                            Toast.makeText(view.getContext(), "Please check your card details", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }



    private void displayAlert(@NonNull String title,
                              @Nullable String message,
                              boolean restartDemo) {
        Activity activity = this;
        runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                    .setTitle(title)
                    .setMessage(message);
            if (restartDemo) {
                builder.setPositiveButton("Restart demo",
                        (DialogInterface dialog, int index) -> {


                        });
            } else {
                builder.setPositiveButton("Ok", null);
            }
            builder.create().show();
        });
    }

    private void initialize(){
        card_number = findViewById(R.id.card_number);
        card_year= findViewById(R.id.card_year);
        card_cvv = findViewById(R.id.card_cvv);
        card_month = findViewById(R.id.card_month);
        card_name = findViewById(R.id.card_name);

        mContext = PaymentGateway.this;
    }
    private void addTextWatcher(final EditText one) {
        one.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                switch (one.getId()) {



                    case R.id.card_number:
                        if (one.length() == 16) {
                            card_month.requestFocus();
                        }
                        break;
                    case R.id.card_month:
                        if (one.length() == 2) {
                            card_year.requestFocus();
                        }
                        break;
                    case R.id.card_year:
                        if (one.length() == 2) {
                            card_cvv.requestFocus();
                        }
                        break;

                    case R.id.card_cvv:
                        if (one.length() == 3) {
                            card_name.requestFocus();
                        }
                        break;
                    case R.id.card_name:

                        break;

//                    case R.id.et_otp_email3:
//                        if (one.length() == 1) {
//                            InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//                            inputManager.hideSoftInputFromWindow(PaymentGateway.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                        } else if (one.length() == 0) {
//                            otp3.requestFocus();
//                        }
//                        break;
                }
            }
        });


    }



    @Override
    public void onBackPressed() {
        Intent i = new Intent(PaymentGateway.this,PlanDetails.class);
        startActivity(i);
    }
}