package com.oodi.godsendapp.fragment.hospital.cunsultation;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.oodi.godsend.R;
import com.oodi.godsendapp.activity.PaymentActivity;
import com.oodi.godsendapp.fragment.RootFragment;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;
import static com.android.volley.VolleyLog.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class CSaTReviewFragment extends RootFragment implements PaymentResultListener {

    Activity mContext;
    View view ;
String id;
    @BindView(R.id.lnrBack)
    LinearLayout mLnrBack ;
    @BindView(R.id.txtHeaderName)
    TextView mTxtHeaderName;
    @BindView(R.id.depname)
    TextView mdepname;
    @BindView(R.id.btnConfirm)
    Button mBtnConfirm;
@BindView(R.id.txtNamePatient)
TextView mtxtNamePatient;
    public CSaTReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sa_treview, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, view);
        mTxtHeaderName.setText("Review");

        mLnrBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.onBackPressed();
            }
        });
        SharedPreferences pref = getActivity().getSharedPreferences("MY" , Context.MODE_PRIVATE);
        id = pref.getString("service_name", "");
        String pname = pref.getString("prof_name","");
        mtxtNamePatient.setText(pname);

        Log.d("PARAM::", id);
        mdepname.setText(id);
        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startPayment();
                Intent intent = new Intent(getActivity(), PaymentActivity.class);
                startActivity(intent);

            }
        });


        return view;
    }
    public void startPayment() {
        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
       // final CSaTReviewFragment activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", " Eoh Techservices Private Limited");
            options.put("description", "Demoing Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", "53000");
            JSONObject preFill = new JSONObject();
            preFill.put("email", "");
            preFill.put("contact", "");

            options.put("prefill", preFill);

            co.open(this.getActivity(), options);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
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
                            }   String status = jsonObject.optString("status");


                            if (status.equals("success")) {
                                String code = jsonObject.optString("code");
                                Toast.makeText(mContext, "You have successfully pay", Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // update_profile();

                            Toast.makeText(mContext, "Error While connecting to server ur payment Has Done", Toast.LENGTH_SHORT).show();
                            mContext.finish();
                          //  appUtils.dismissProgressBar();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("appointment_code" ,"AD0000002H" );
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
            Toast.makeText(mContext, "Payment failed:" + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }

public void  getAppointmentDetails()
{




}
}