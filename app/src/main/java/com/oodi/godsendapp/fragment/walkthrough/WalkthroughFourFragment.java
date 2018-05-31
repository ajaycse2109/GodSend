package com.oodi.godsendapp.fragment.walkthrough;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
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
import com.oodi.godsendapp.activity.MainActivity;
import com.oodi.godsendapp.activity.WalkthroughActivity;
import com.oodi.godsendapp.util.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalkthroughFourFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    public  int EdtNumber=1;
    public  int EdtNumberr=1;
    Activity mContext ;
    View view ;
    Session session;
    @BindView(R.id.spinner)
    Spinner mspinner;
    @BindView(R.id.lnrImg)
    LinearLayout mLnrImg;
    @BindView(R.id.sv)
    ScrollView mSv ;
    @BindView(R.id.hgt)
    EditText mhgt;
    @BindView(R.id.wgt)
    EditText mght;
    @BindView(R.id.EdtHsp11)
    EditText mEdtHsp11;
    @BindView(R.id.EdtHsp22)
    EditText mEdtHsp22;
    @BindView(R.id.EdtHsp33)
    EditText mEdtHsp33;
    @BindView(R.id.EdtHsp44)
    EditText mEdtHsp44;
    @BindView(R.id.EdtHsp55)
    EditText mEdtHsp55;
    @BindView(R.id.txtAddmore2)
    ImageView mtxtAddmore2;
    @BindView(R.id.EdtHp1)
    EditText mEdtHp1;
    @BindView(R.id.EdtHp2)
    EditText mEdtHp2;
    @BindView(R.id.EdtHp3)
    EditText mEdtHp3;
    @BindView(R.id.EdtHp4)
    EditText mEdtHp4;
    @BindView(R.id.EdtHp5)
    EditText mEdtHp5;
    @BindView(R.id.txtAddmore)
    ImageView mtxtAddmore;
    @BindView(R.id.btnFinish)
    Button mBtnFinish ;
    public WalkthroughFourFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_walkthrough_four, container, false);
        mContext = getActivity() ;
        ButterKnife.bind(this , view);
        session = new Session(mContext);

        mLnrImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WalkthroughActivity.mImgBack.setVisibility(View.VISIBLE);
                mLnrImg.setVisibility(View.GONE);
                mSv.setVisibility(View.VISIBLE);

            }
        });

        mBtnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                session.setLogin(true);
                getVitalInfo();
//                Intent intent = new Intent(mContext , MainActivity.class);
//                startActivity(intent);


            }
        });
        mspinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("A+");
        categories.add("O+");
        categories.add("B+");
        categories.add("AB+");
        categories.add("A-");
        categories.add("O-");
        categories.add("B-");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        mspinner.setAdapter(dataAdapter);
        mght.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mght.setHintTextColor(Color.TRANSPARENT);
                return false;
            }

        });
        mhgt.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mhgt.setHintTextColor(Color.TRANSPARENT);
                return false;
            }

        });
        mEdtHsp11.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHsp11.setHintTextColor(Color.TRANSPARENT);
                return false;
            }

        });
        mEdtHsp22.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHsp22.setHintTextColor(Color.TRANSPARENT);
                return false;
            }

        });
        mEdtHsp33.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHsp33.setHintTextColor(Color.TRANSPARENT);
                return false;
            }

        });
        mEdtHsp44.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHsp44.setHintTextColor(Color.TRANSPARENT);
                return false;
            }

        });
        mEdtHsp55.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHsp55.setHintTextColor(Color.TRANSPARENT);
                return false;
            }

        });
        mEdtHp1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHp1.setHintTextColor(Color.TRANSPARENT);
                return false;
            }

        });
        mEdtHp2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHp2.setHintTextColor(Color.TRANSPARENT);
                return false;
            }

        });
        mEdtHp3.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHp3.setHintTextColor(Color.TRANSPARENT);
                return false;
            }

        });
        mEdtHp4.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHp4.setHintTextColor(Color.TRANSPARENT);
                return false;
            }

        }); mEdtHp5.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHp5.setHintTextColor(Color.TRANSPARENT);
                return false;
            }

        });

        mtxtAddmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(EdtNumber == 1)
                {
                    mEdtHsp22.setVisibility(View.VISIBLE);
                    EdtNumber ++;
                }
                else if(EdtNumber == 2)
                {
                    mEdtHsp33.setVisibility(View.VISIBLE);
                    EdtNumber ++;
                }
                else if(EdtNumber == 3)
                {
                    mEdtHsp44.setVisibility(View.VISIBLE);
                    EdtNumber ++;
                }
                else if(EdtNumber == 4)
                {
                    mEdtHsp55.setVisibility(View.VISIBLE);
                    EdtNumber ++;
                }
                else
                {
                    Toast.makeText(mContext, "Sorry Limit Exceeds", Toast.LENGTH_SHORT).show();
                }

            }


        });
        mtxtAddmore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(EdtNumberr == 1)
                {
                    mEdtHp2.setVisibility(View.VISIBLE);
                    EdtNumberr ++;
                }
                else if(EdtNumberr == 2)
                {
                    mEdtHp3.setVisibility(View.VISIBLE);
                    EdtNumberr ++;
                }
                else if(EdtNumberr == 3)
                {
                    mEdtHp4.setVisibility(View.VISIBLE);
                    EdtNumberr ++;
                }
                else if(EdtNumberr == 4)
                {
                    mEdtHp5.setVisibility(View.VISIBLE);
                    EdtNumberr ++;
                }
                else
                {
                    Toast.makeText(mContext, "Sorry Limit Exceeds", Toast.LENGTH_SHORT).show();
                }

            }


        });
        return view;
    }

    public void updateVitalInfo()
    {
        //appUtils.showProgressBarLoading();

        String REGISTER_URL = mContext.getResources().getString(R.string.base_url) + "api/customer/vitalinfo/";

        final StringRequest stringRequest = new StringRequest(Request.Method.PUT, REGISTER_URL,
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

                        if (status.equals("success")){
                            Intent intent = new Intent(mContext , MainActivity.class);
                            startActivity(intent);
//                            WalkthroughActivity.mImgBack.setVisibility(View.VISIBLE);
//                            WalkthroughActivity.mTxtSkip.setVisibility(View.VISIBLE);
//
//                            WalkthroughActivity.mViewpager.setCurrentItem(1);
                        }

                      //  appUtils.dismissProgressBar();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //update_profile();

                        Toast.makeText(mContext , error.networkResponse.statusCode , Toast.LENGTH_LONG).show();
                        //appUtils.dismissProgressBar();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                String allergies="";
                String medicalcond = "";
                if(mEdtHp1.getText().toString()!=null || !(mEdtHp1.getText().toString().isEmpty()))
                {
                    medicalcond = medicalcond + mEdtHp1.getText().toString();
                }
                if(mEdtHp2.getText().toString()!=null || mEdtHp2.getText().toString()!= "")
                {
                    medicalcond =medicalcond+","+ mEdtHp2.getText().toString();
                }
                if(mEdtHp3.getText().toString()!=null || mEdtHp3.getText().toString()!= "")
                {
                    medicalcond =medicalcond+","+ mEdtHp3.getText().toString();
                }
                if(mEdtHp4.getText().toString()!=null || mEdtHp4.getText().toString()!= "")
                {
                    medicalcond =medicalcond+","+ mEdtHp4.getText().toString();
                }
                if(mEdtHp5.getText().toString()!=null || mEdtHp5.getText().toString()!= "")
                {
                    medicalcond =medicalcond+","+ mEdtHp5.getText().toString();
                }

                if(mEdtHsp11.getText().toString()!=null || mEdtHsp11.getText().toString()!= "")
                {
                    allergies = allergies + mEdtHsp11.getText().toString();
                }
                if(mEdtHsp22.getText().toString()!=null || mEdtHsp22.getText().toString()!= "")
                {
                    allergies =allergies+","+ mEdtHsp22.getText().toString();
                }
                if(mEdtHsp33.getText().toString()!=null || mEdtHsp33.getText().toString()!= "")
                {
                    allergies =allergies+","+ mEdtHsp33.getText().toString();
                }
                if(mEdtHsp44.getText().toString()!=null || mEdtHsp44.getText().toString()!= "")
                {
                    allergies =allergies+","+ mEdtHsp44.getText().toString();
                }
                if(mEdtHsp55.getText().toString()!=null || mEdtHsp55.getText().toString()!= "")
                {
                    allergies =allergies+","+ mEdtHsp55.getText().toString();
                }

                Map<String, String> params = new HashMap<String, String>();
                params.put("height" , mhgt.getText().toString());
                params.put("weight" ,mght.getText().toString() );
                params.put("blood_group" , mspinner.getSelectedItem().toString());
                params.put("medical_conditions" , allergies);
                params.put("medications" , "");
                params.put("allergies" ,medicalcond );
//                params.put("last_name" , mEdtSecondName.getText().toString());

                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                SharedPreferences prefs = mContext.getSharedPreferences("Login", Context.MODE_PRIVATE);
                final String auth_token = prefs.getString("auth_token", "");

                Map<String, String>  params = new HashMap<String, String>();
                params.put("auth-token", auth_token);
                params.put("Content-Type", "application/x-www-form-urlencoded");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }

    public void vitalInfo()
    {
        String REGISTER_URL = mContext.getResources().getString(R.string.base_url) + "api/customer/vitalinfo/";

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
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

                        if (status.equals("success")){
                            Intent intent = new Intent(mContext , MainActivity.class);
                            startActivity(intent);
//                            WalkthroughActivity.mImgBack.setVisibility(View.VISIBLE);
//                            WalkthroughActivity.mTxtSkip.setVisibility(View.VISIBLE);
//
//                            WalkthroughActivity.mViewpager.setCurrentItem(1);
                        }

                        //  appUtils.dismissProgressBar();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //update_profile();
                        updateVitalInfo();
                        //Toast.makeText(mContext , error.networkResponse.statusCode , Toast.LENGTH_LONG).show();
                        //appUtils.dismissProgressBar();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                String allergies="";
                String medicalcond = "";
                if(mEdtHp1.getText().toString()!=null || mEdtHp1.getText().toString()!= "")
                {
                    medicalcond = medicalcond + mEdtHp1.getText().toString();
                }
                if(mEdtHp2.getText().toString()!=null || mEdtHp2.getText().toString()!= "")
                {
                    medicalcond ="," +medicalcond + mEdtHp2.getText().toString();
                }
                if(mEdtHp3.getText().toString()!=null || mEdtHp3.getText().toString()!= "")
                {
                    medicalcond ="," +medicalcond + mEdtHp3.getText().toString();
                }
                if(mEdtHp4.getText().toString()!=null || mEdtHp4.getText().toString()!= "")
                {
                    medicalcond ="," +medicalcond + mEdtHp4.getText().toString();
                }
                if(mEdtHp5.getText().toString()!=null || mEdtHp5.getText().toString()!= "")
                {
                    medicalcond ="," +medicalcond + mEdtHp5.getText().toString();
                }

                if(mEdtHsp11.getText().toString()!=null || mEdtHsp11.getText().toString()!= "")
                {
                    medicalcond = medicalcond + mEdtHsp11.getText().toString();
                }
                if(mEdtHsp22.getText().toString()!=null || mEdtHsp22.getText().toString()!= "")
                {
                    medicalcond ="," +medicalcond + mEdtHsp22.getText().toString();
                }
                if(mEdtHsp33.getText().toString()!=null || mEdtHsp33.getText().toString()!= "")
                {
                    medicalcond ="," +medicalcond + mEdtHsp33.getText().toString();
                }
                if(mEdtHsp44.getText().toString()!=null || mEdtHsp44.getText().toString()!= "")
                {
                    medicalcond ="," +medicalcond + mEdtHsp44.getText().toString();
                }
                if(mEdtHsp55.getText().toString()!=null || mEdtHsp55.getText().toString()!= "")
                {
                    medicalcond ="," +medicalcond + mEdtHsp55.getText().toString();
                }

                Map<String, String> params = new HashMap<String, String>();
                params.put("height" , mhgt.getText().toString());
                params.put("weight" ,mght.getText().toString() );
                params.put("blood_group" , mspinner.getSelectedItem().toString());
                params.put("medical_conditions" , medicalcond);
                params.put("medications" , "");
                params.put("allergies" ,allergies );
//                params.put("last_name" , mEdtSecondName.getText().toString());

                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                SharedPreferences prefs = mContext.getSharedPreferences("Login", Context.MODE_PRIVATE);
                final String auth_token = prefs.getString("auth_token", "");

                Map<String, String>  params = new HashMap<String, String>();
                params.put("auth-token", auth_token);
                params.put("Content-Type", "application/x-www-form-urlencoded");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){

            WalkthroughActivity.mTxtSkip.setVisibility(View.INVISIBLE);

            WalkthroughActivity.mImgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WalkthroughActivity.mTxtSkip.setVisibility(View.VISIBLE);

                    WalkthroughActivity.mViewpager.setCurrentItem(0);

                }
            });


        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
       // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

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
updateVitalInfo();
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
                      //  mtxtHeight.setText(height);
                      //  mtxtWeight.setText(weight);
                      //  mtxtBloodgrp.setText(blood_group);


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
                        vitalInfo();
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
