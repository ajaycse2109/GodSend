package com.oodi.godsendapp.fragment.appointments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.oodi.godsend.R;
import com.oodi.godsendapp.activity.AmbulanceActivity;
import com.oodi.godsendapp.activity.MainActivity;
import com.oodi.godsendapp.adapter.AppointmentAdapter;
import com.oodi.godsendapp.fragment.RootFragment;
import com.oodi.godsendapp.pojo.Appointment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentsFragment extends RootFragment {

    Activity mContext;
    View view ;

    List<Appointment> mUpcomingList = new ArrayList<>();
    AppointmentAdapter mUpcomingAppointmentAdapter ;

    List<Appointment> mCompletedList = new ArrayList<>();
    AppointmentAdapter mCompletedAppointmentAdapter ;

    @BindView(R.id.btnNewAppointment)
    Button mBtnNewAppointment;
    @BindView(R.id.recUpcoming)
    RecyclerView mRecUpcoming;
    @BindView(R.id.recCompleted)
    RecyclerView mRecCompleted;

    public AppointmentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_appointments, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, view);
        providers();



        mCompletedAppointmentAdapter = new AppointmentAdapter(mContext , mCompletedList );

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(mContext);
        mRecCompleted.setLayoutManager(layoutManager1);
        mRecCompleted.setAdapter(mCompletedAppointmentAdapter);

        mBtnNewAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext , MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void providers()
    {
        String REGISTER_URL = mContext.getResources().getString(R.string.base_url) + "api/customer/appointment/latest/";

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
JSONObject appObject=null;

                        appObject=jsonObject.optJSONObject("appointment");
                        String code = appObject.optString("code");
                     JSONObject provObject =null;
                     String provName="";
//                     provObject = appObject.optJSONObject("provider");
//                     String provName= provObject.optString("name");

//                     JSONObject appDetObject = null;
//                     appDetObject = appObject.optJSONObject("appointment_details");


                        JSONArray data=null;
                        data = appObject.optJSONArray("appointment_details");





                        for (int i = 0 ; i < data.length() ; i++){

                            JSONObject jsonObject2 = null;
                            try {
                                jsonObject2 = data.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            JSONObject servObject=null;
                            servObject = jsonObject2.optJSONObject("service");
                            String servName = servObject.optString("name");
                            provName=servName;


                        }



                        SharedPreferences sharedpreferences = view.getContext().getSharedPreferences("MY" , Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("app_code",code);
                        editor.putString("prov_name",provName);

                        // Log.d("PARAM::ADAP", saT.getDepartment());

                        editor.commit();
                        //   appUtils.dismissProgressBar();
                        Appointment appmnt = new Appointment();
                        appmnt.setAppCode(code);
                        appmnt.setProvName(provName);
                        mUpcomingList.add(appmnt);

                        mUpcomingAppointmentAdapter = new AppointmentAdapter(getActivity()  , mUpcomingList );

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                        mRecUpcoming.setLayoutManager(layoutManager);
                        mRecUpcoming.setAdapter(mUpcomingAppointmentAdapter);

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
