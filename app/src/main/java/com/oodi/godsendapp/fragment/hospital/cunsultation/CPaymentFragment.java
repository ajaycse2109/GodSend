package com.oodi.godsendapp.fragment.hospital.cunsultation;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.oodi.godsend.R;
import com.oodi.godsendapp.fragment.RootFragment;
import com.oodi.godsendapp.util.AppUtils;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.PendingIntent.getActivity;
import static android.content.Context.MODE_PRIVATE;
import static com.android.volley.VolleyLog.TAG;

public class CPaymentFragment extends RootFragment implements PaymentResultListener {
    Activity mContext;
    View view;
    @BindView(R.id.lnrBack)
    LinearLayout mLnrBack;
    @BindView(R.id.txtHeaderName)
    TextView mTxtHeaderName;
    /*@BindView(R.id.btn_pay)
    Button mBtnConfirm;*/
   AppUtils appUtils;

    public CPaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_payment, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, view);
        mTxtHeaderName.setText("Payment Process");
        mLnrBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.onBackPressed();
            }
        });
      //  Checkout.preload(mContext.getApplicationContext());
        ButterKnife.bind(this, view);
        appUtils = new AppUtils(mContext);
        SharedPreferences prefs = mContext.getSharedPreferences("Login", MODE_PRIVATE);
        final String auth_token = prefs.getString("auth_token", "");
        Log.e("auth", auth_token);
       /* mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });*/


        return view;
    }


    public void startPayment() {
        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final RootFragment activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", " Eoh Techservices Private Limited");
            options.put("description", "Demoing Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", "1");

            JSONObject preFill = new JSONObject();
            preFill.put("email", "");
            preFill.put("contact", "");

            options.put("prefill", preFill);

            co.open(mContext, options);
        } catch (Exception e) {
            Toast.makeText(mContext, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }
    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Toast.makeText(mContext, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();

            String SAVECHANGES_URL = mContext.getResources().getString(R.string.base_url) + "api/customer/appointment/confirm_payment/";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, SAVECHANGES_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }

                            String status = jsonObject.optString("status");

                            if (status.equals("Booked")){
                                Toast.makeText(mContext, "You have successfully pay", Toast.LENGTH_LONG).show();
                                String code = jsonObject.optString("appointment_code");
                            }

                            appUtils.dismissProgressBar();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // update_profile();

                            Toast.makeText(mContext, "Error While connecting to server ur payment Has Done", Toast.LENGTH_SHORT).show();
                            mContext.finish();
                            appUtils.dismissProgressBar();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("appointment_code" ,"AD0000004H" );
                    return params;
                }

                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    SharedPreferences prefs = mContext.getSharedPreferences("Login", MODE_PRIVATE);
                    final String auth_token = prefs.getString("auth_token", "");

                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("auth-token", auth_token);
                    params.put("Content-Type", "application/x-www-form-urlencoded");

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            requestQueue.add(stringRequest);

        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    /**
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(mContext, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }


}

/*

    Merchant Id: 9v7W5f5758aN5SChange Password
        Merchant Name
        Eoh Techservices Private Limited
        Merchant Email
        catarunn@gmail.com
Activation Form Progress
        23%
        Activation Status
        --
        Registration Date
        Apr 04 2018, 04:37:19 pm
        GST DetailsAdd your GST details
        GST Details
        Not Updated
        Razorpay's GST Number
        29AAGCR4375J1ZU*/

/*

    Key Id	Created At	Expiry	Action

rzp_test_SCGpKrMCSKNG54
*/
