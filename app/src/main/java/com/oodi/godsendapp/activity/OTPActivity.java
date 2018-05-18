package com.oodi.godsendapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.oodi.godsend.R;
import com.oodi.godsendapp.util.AppUtils;
import com.oodi.godsendapp.util.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OTPActivity extends AppCompatActivity {

    Activity mContext;
    Session session;
    AppUtils appUtils;

    @BindView(R.id.txtSendOTP)
    TextView mTxtSendOTP;
    @BindView(R.id.lnr1)
    LinearLayout mLnr1;
    @BindView(R.id.lnr2)
    LinearLayout mLnr2;
    @BindView(R.id.edtNumber)
    EditText mEdtNumber;
    @BindView(R.id.pinView)
    PinView otp_view;
    @BindView(R.id.txtRegister)
    TextView mTxtRegister;

    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    String token = "";

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        //verifyPhoneNumberWithCode(mVerificationId,phoneNumber);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = task.getResult().getUser();

                            //token = user.getUid();

                            token = FirebaseInstanceId.getInstance().getToken();

                            register();

                            try {
                                appUtils.dismissProgressBar();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            /*Intent intent = new Intent(LoginActivity.this , ForgotPasswordActivity.class);
                            intent.putExtra("phone" , phone);
                            intent.putExtra("type" , "login");
                            intent.putExtra("id" , "Sign In");
                            intent.putExtra("otp" , mResendToken);
                            startActivityForResult(intent , 2);*/

                            //Toast.makeText(getApplicationContext(), "sign in successfull", Toast.LENGTH_SHORT).show();
                            // [START_EXCLUDE]

                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and update the UI

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]

                                // [END_EXCLUDE]
                            }
                            // [START_EXCLUDE silent]
                            // Update UI

                            // [END_EXCLUDE]
                        }
                    }
                });
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    private void callback_verificvation() {

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                // [START_EXCLUDE silent]
                // [END_EXCLUDE]

                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential
                // [END_EXCLUDE]
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                // [START_EXCLUDE silent]
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]

                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]

                    // [END_EXCLUDE]
                }

                // Show a message and update the UI
                // [START_EXCLUDE]

                // [END_EXCLUDE]
                try {
                    appUtils.dismissProgressBar();

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.


                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                mLnr2.setVisibility(View.VISIBLE);

                try {
                    appUtils.dismissProgressBar();

                } catch (Exception e) {
                    e.printStackTrace();
                }

               /* Intent intent = new Intent(LoginActivity.this , OTPActivity.class);
                intent.putExtra("phone" , phone);
                intent.putExtra("type" , "login");
                intent.putExtra("OTP" , mVerificationId);
                startActivityForResult(intent , 2);*/

                // [START_EXCLUDE]
                // Update UI

                // [END_EXCLUDE]
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        mContext = this;
        ButterKnife.bind(mContext);
        session = new Session(mContext);
        appUtils = new AppUtils(mContext);

        //FirebaseAuth.getInstance().signOut();

        callback_verificvation();

        mAuth = FirebaseAuth.getInstance();

        /*Intent intent_ = new Intent(mContext, MainActivity.class);
        startActivity(intent_);
        finish();*/

        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(mContext, WalkthroughActivity.class);
            startActivity(intent);
            finish();
        }


        mTxtSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEdtNumber.getText().toString().equals("")
                        || mEdtNumber.getText().toString().length() != 10) {
                    Toast.makeText(mContext, "Please enter 10 digit mobile number", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        appUtils.showProgressBarLoading();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    startPhoneNumberVerification("+91" + mEdtNumber.getText().toString());
                }

            }
        });

        mTxtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    appUtils.showProgressBarLoading();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                verifyPhoneNumberWithCode(mVerificationId, otp_view.getText().toString());
            }
        });

    }

    private void register() {

        appUtils.showProgressBarLoading();

        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUser.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    @Override
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isComplete()) {
                            token = task.getResult().getToken();
                            Log.e("token", token);
                        }
                    }
                });

        String REGISTER_URL = mContext.getResources().getString(R.string.base_url) + "api/user/register/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                        /*{
    "mobile": "8490947210",
	"token": "eyJhbGciOiJSUzI1NiIsImtpZCI6ImZlZjg5YmE3MjEyNmU1NGZkZDUwYzkxOWI3YWRiNWUyNmYwMjk5Y2YifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vY2xpcWhlYWx0aC1vb2RpIiwiYXVkIjoiY2xpcWhlYWx0aC1vb2RpIiwiYXV0aF90aW1lIjoxNTI0MTIwMjQwLCJ1c2VyX2lkIjoidUtUNDBXTkppeFdGc2VOWWM0d0FTMzZGaUR5MSIsInN1YiI6InVLVDQwV05KaXhXRnNlTlljNHdBUzM2RmlEeTEiLCJpYXQiOjE1MjQxMjAyNDcsImV4cCI6MTUyNDEyMzg0NywicGhvbmVfbnVtYmVyIjoiKzkxODQ5MDk0NzIxMCIsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsicGhvbmUiOlsiKzkxODQ5MDk0NzIxMCJdfSwic2lnbl9pbl9wcm92aWRlciI6InBob25lIn19.BkfpD8b_Vm3aByi3vpQ3RhOJWHQg0icqSXCStP3tUjxAD-xHvmKMgttBMRgBk8E6LsIqKAIxIgkm-5kkLnvUSliaUTEaWew5tChjMdy5Ui2p0-dBP-Sv5WeXgmN61uJw9CUI5_U2roKm8vb1Mqsr32ABdqI6t-SgwI4q91EZ8_16Mo5E7nqg9L4vybgE81knFtPA5MbCZnRkxWS58ZoWvu1SjWKfR34yWSwM36NPS_BcIhwMCtSSPD4oN7MzxVy87L_ufXsYR4oqE2tQkBwD3kJRNf6RvjHBc2MAd5h9E7m7QTiop1HSNZxJbileKh4gcShvLpwpn2kb6GQgx0DMCw",
	"auth_token": "cc17157c-b4e2-4763-a4c9-13c94f243fb7",
	"status": "success",
	"uid": "uKT40WNJixWFseNYc4wAS36FiDy1"
}*/

                        String status = jsonObject.optString("status");

                        if (status.equals("success")) {
                            Toast.makeText(mContext, "You have successfully registered and logged in", Toast.LENGTH_LONG).show();
                            String mobile = jsonObject.optString("mobile");
                            String token = jsonObject.optString("token");
                            String auth_token = jsonObject.optString("auth_token");
                            String uid = jsonObject.optString("uid");

                            SharedPreferences.Editor editor = getSharedPreferences("Login", MODE_PRIVATE).edit();
                            editor.putString("mobile", mobile);
                            editor.putString("token", token);
                            editor.putString("auth_token", auth_token);
                            editor.putString("uid", uid);
                            editor.commit();

                            session.setLogin(true);

                            appUtils.dismissProgressBar();

                            Intent intent = new Intent(mContext, WalkthroughActivity.class);
                            startActivity(intent);

                        }

                        appUtils.dismissProgressBar();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(mContext , error.networkResponse.statusCode , Toast.LENGTH_LONG).show();
                        login();
                        appUtils.dismissProgressBar();

                        // session.setLogin(true);
                    }
                }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");

                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", "91" + mEdtNumber.getText().toString());
                params.put("token", token);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void login() {

        appUtils.showProgressBarLoading();

        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUser.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    @Override
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isComplete()) {
                            token = task.getResult().getToken();
                            Log.e("token", token);
                        }
                    }
                });

        String REGISTER_URL = mContext.getResources().getString(R.string.base_url) + "api/customer/login/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                        //Toast.makeText(mContext, "You have successfully registered and logged in", Toast.LENGTH_LONG).show();

                        String mobile = jsonObject.optString("mobile");
                        String token = jsonObject.optString("token");
                        String auth_token = jsonObject.optString("auth_token");

                        SharedPreferences.Editor editor = getSharedPreferences("Login", MODE_PRIVATE).edit();
                        editor.putString("mobile", mobile);
                        editor.putString("token", token);
                        editor.putString("auth_token", auth_token);
                        editor.commit();

                        session.setLogin(true);

                        appUtils.dismissProgressBar();

                        Intent intent = new Intent(mContext, WalkthroughActivity.class);
                        startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(mContext , error.networkResponse.statusCode , Toast.LENGTH_LONG).show();
                        appUtils.dismissProgressBar();

                        // session.setLogin(true);
                    }
                }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");

                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", "91" + mEdtNumber.getText().toString());
                params.put("token", token);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}