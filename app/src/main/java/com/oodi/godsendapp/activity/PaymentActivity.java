package com.oodi.godsendapp.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oodi.godsend.R;
import com.oodi.godsendapp.fragment.hospital.ReviewReservationFragment;
import com.oodi.godsendapp.util.AppUtils;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import butterknife.BindView;

import static java.security.AccessController.getContext;

public class PaymentActivity extends FragmentActivity implements PaymentResultListener {

    @BindView(R.id.FrameId)
    FrameLayout fl;
    private static final String TAG = PaymentActivity.class.getSimpleName();
    ImageView imageView;
    AppUtils appUtils;
    Activity mContext;
    View view;
    String pid, did, bid, fee, payload, docname;
    TextView dcnm, fe;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        startPayment();
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_payment);

        // Payment button created by you in XML layout
      //  Button button = (Button) findViewById(R.id.btn_pay);
       mContext = this;
      //  ButterKnife.bind(this, view);
      //  appUtils = new AppUtils(mContext);
        SharedPreferences prefs = mContext.getSharedPreferences("Login", MODE_PRIVATE);
        final String auth_token = prefs.getString("auth_token", "");
        Log.e("auth" , auth_token);
     ///   pid = getIntent().getStringExtra("pid");
       // did = getIntent().getStringExtra("did");
      //  bid = getIntent().getStringExtra("bid");
     //   fee = getIntent().getStringExtra("fee");
     //   docname = getIntent().getStringExtra("docname");
      //  dcnm = (TextView) findViewById(R.id.dcname);
      //  fe = (TextView) findViewById(R.id.fee);
       // dcnm.setText(docname);
       // fe.setText(fee);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//            /*    Intent intent = new Intent(PaymentActivity.this, PatientDashboard.class);
//                intent.putExtra("did",did);
//                intent.putExtra("pid",pid);
//              *//*  intent.putExtra("docname",docname);
//                intent.putExtra("specility",speciality);
//                // intent.putExtra("imageurl",imageview);
//                //intent.putExtra("docdegl",docdegl[0]);
//                intent.putExtra("notino",n);*//*
//                startActivity(intent);*/
//                finish();
//            }
//        });

    }
    public void startPayment() {
        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", " Eoh Techservices Private Limited");
            options.put("description", "Demoing Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", "10000");

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
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();

            try {
ReviewReservationFragment fragment = new ReviewReservationFragment();
getSupportFragmentManager().beginTransaction().add(R.id.FrameId,fragment).commitAllowingStateLoss();
            }
            catch (Exception e)
            {
                Log.e(TAG, "Exception in onPaymentSuccess", e);
            }

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
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
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
