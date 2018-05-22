package com.oodi.godsendapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.oodi.godsend.R;
import com.oodi.godsendapp.fragment.hospital.cunsultation.CSaTReviewFragment;
import com.razorpay.Checkout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.volley.VolleyLog.TAG;

public class AmbulanceActivity extends FragmentActivity implements OnMapReadyCallback {

    public static Activity mContext;

    private GoogleMap mMap;

    @BindView(R.id.lnrBack)
    LinearLayout mLnrBack ;
    @BindView(R.id.txtConfirmBooking)
    TextView mTxtConfirmBooking;
    @BindView(R.id.textView)
    TextView mtextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);
        mContext = this;
        ButterKnife.bind(mContext);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mLnrBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.onBackPressed();
            }
        });
        mtextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("content");
                builder.setCancelable(true);
                builder.setMessage(mContext.getResources().getString(R.string.canceldailogmsg));
                builder.setPositiveButton("OK", null);
                builder.show();
            }
        });
        mTxtConfirmBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();

                /*Intent intent = new Intent(mContext , RouteActivity.class);
                startActivity(intent);*/
            }
        });

    }
    public void startPayment() {
        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final AmbulanceActivity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", " Eoh Techservices Private Limited");
            options.put("description", "Demoing Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", "29900");
            JSONObject preFill = new JSONObject();
            preFill.put("email", "");
            preFill.put("contact", "");

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
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
                            String status = jsonObject.optString("code");
                            if (status.equals("code")){
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
                    params.put("appointment_code" ,"AD0000003H" );
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
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(mContext, "Payment failed:" + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng(12.9716, 77.5946);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

        //For zooming automatically to the location of the marker
        CameraPosition cameraPosition ;

        cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
