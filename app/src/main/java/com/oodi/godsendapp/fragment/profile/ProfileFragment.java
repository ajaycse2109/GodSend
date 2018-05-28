package com.oodi.godsendapp.fragment.profile;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.oodi.godsendapp.adapter.AddressAdapter;
import com.oodi.godsendapp.fragment.RootFragment;
import com.oodi.godsendapp.pojo.Address;
import com.oodi.godsendapp.util.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v4.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;
/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends RootFragment {

    Activity mContext;
    View view ;
    AppUtils appUtils;
    List<Address> mAddressList = new ArrayList<>();
    AddressAdapter mAddressAdapter;
    private static final int RESULT_PICK_CONTACT = 1001;
    @BindView(R.id.imgEmergency_contact)
    ImageView mImgEmergency_contact;
    @BindView(R.id.recAddress)
    RecyclerView mRecAddress;
    @BindView(R.id.cardMedicalRecord)
    CardView mCardMedicalRecord;
    @BindView(R.id.cardPreferences)
    CardView mCardPreferences;


    @BindView(R.id.edtNumber)
    EditText mEdtNumber;

    @BindView(R.id.txtProfName)
    TextView mtxtProfName;
    @BindView(R.id.txtBloodgrp)
    TextView mtxtBloodgrp;
    @BindView(R.id.txtHeight)
    TextView mtxtHeight;
    @BindView(R.id.txtWeight)
    TextView mtxtWeight;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, view);

        mCardMedicalRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.root_profile, new MedicalRecordsFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        mCardPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.root_profile, new PreferencesFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        mImgEmergency_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);


            }
        });
        mEdtNumber.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtNumber.setHint("");
                return false;
            }

        });
        mAddressAdapter = new AddressAdapter(mContext , mAddressList , this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext , LinearLayoutManager.HORIZONTAL , false);
        mRecAddress.setLayoutManager(layoutManager);
        mRecAddress.setAdapter(mAddressAdapter);
        profile();
        getVitalInfo();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    Cursor cursor = null;
                    try {
                        String contactNumber = null;
                        String contactName = null;
// getData() method will have the
// Content Uri of the selected contact
                        Uri uri = data.getData();
//Query the content uri
                        cursor = mContext.getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();
// column index of the phone number
                        int phoneIndex = cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER);
// column index of the contact name
                        int nameIndex = cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                        contactNumber = cursor.getString(phoneIndex);
                        contactName = cursor.getString(nameIndex);
// Set the value to the textviews
// tvContactName.setText("Contact Name : ".concat(contactName));
                        mEdtNumber.setText(contactName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
    public void profile() {

       // appUtils.showProgressBarLoading();

        String REGISTER_URL = mContext.getResources().getString(R.string.base_url) + "api/customer/profile";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                        String gender = jsonObject.optString("gender");
                        String first_name = jsonObject.optString("first_name");
                        String last_name = jsonObject.optString("last_name");
                        String phone = jsonObject.optString("phone");
                        String address = jsonObject.optString("address");
                        String dob = jsonObject.optString("dob");
                       // String emergency_contact_name = jsonObject.optString("emergency_contact_name");
                        String emergency_contact_phone = jsonObject.optString("emergency_contact_phone");
//mAddressList.add(address);
                      //  mTxtName.setText(first_name);
                      //  mTxtDOB.setText(dob);
mtxtProfName.setText( first_name+" " + last_name);
                        mEdtNumber.setText( emergency_contact_phone);

                        SharedPreferences sharedpreferences = view.getContext().getSharedPreferences("MY" , Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("prof_name", first_name+" "+last_name);

                        // Log.d("PARAM::ADAP", saT.getDepartment());

                        editor.commit();
                     //   appUtils.dismissProgressBar();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(mContext , error.networkResponse.statusCode , Toast.LENGTH_LONG).show();
                      // appUtils.dismissProgressBar();
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                SharedPreferences prefs = mContext.getSharedPreferences("Login", Context.MODE_PRIVATE);
                String auth_token = prefs.getString("auth_token", "");

               // auth_token = "ug7ri89cthuhmxf9xymeo1kwm63fa8l8  ";
                Map<String, String> params = new HashMap<String, String>();
                params.put("auth-token", auth_token);
                params.put("Content-Type", "application/x-www-form-urlencoded");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }
public void getVitalInfo()
{
    String REGISTER_URL = mContext.getResources().getString(R.string.base_url) + "api/customer/vitalinfo/";

    StringRequest stringRequest = new StringRequest(Request.Method.GET, REGISTER_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }

                    String height = jsonObject.optString("height");
                    String weight = jsonObject.optString("weight");
                    String blood_group = jsonObject.optString("blood_group");
                    String medical_conditions = jsonObject.optString("medical_conditions");
                    String medications = jsonObject.optString("medications");
                    String allergies = jsonObject.optString("allergies");
                    // String emergency_contact_name = jsonObject.optString("emergency_contact_name");
                   // String emergency_contact_phone = jsonObject.optString("emergency_contact_phone");
//mAddressList.add(address);
                    //  mTxtName.setText(first_name);
                    //  mTxtDOB.setText(dob);
//                    mtxtProfName.setText( first_name+" " + last_name);
//                    mEdtNumber.setText( emergency_contact_phone);
mtxtHeight.setText(height);
mtxtWeight.setText(weight);
mtxtBloodgrp.setText(blood_group);


//                    SharedPreferences sharedpreferences = view.getContext().getSharedPreferences("MY" , Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    //editor.putString("prof_name", first_name+" "+last_name);

                    // Log.d("PARAM::ADAP", saT.getDepartment());

                    //editor.commit();
                    //   appUtils.dismissProgressBar();

                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(mContext , error.networkResponse.statusCode , Toast.LENGTH_LONG).show();
                    // appUtils.dismissProgressBar();
                }
            }) {
        @Override
        public String getBodyContentType() {
            return "application/x-www-form-urlencoded; charset=UTF-8";
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {

            SharedPreferences prefs = mContext.getSharedPreferences("Login", Context.MODE_PRIVATE);
            String auth_token = prefs.getString("auth_token", "");

            // auth_token = "ug7ri89cthuhmxf9xymeo1kwm63fa8l8  ";
            Map<String, String> params = new HashMap<String, String>();
            params.put("auth-token", auth_token);
            params.put("Content-Type", "application/x-www-form-urlencoded");

            return params;
        }
    };

    RequestQueue requestQueue = Volley.newRequestQueue(mContext);
    requestQueue.add(stringRequest);
}
}

