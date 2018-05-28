package com.oodi.godsendapp.fragment.hospital.cunsultation;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.oodi.godsend.R;
import com.oodi.godsendapp.adapter.CDepartmentAdapter;
import com.oodi.godsendapp.adapter.CSaTAdapter;
import com.oodi.godsendapp.fragment.RootFragment;
import com.oodi.godsendapp.pojo.Department;
import com.oodi.godsendapp.pojo.SaT;

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
public class CScanAndTestDepartmentFragment extends RootFragment {
    // List<SaT> avatarList = new ArrayList<>();
    List<Department> departmentList = new ArrayList<>();
    Activity mContext;
    View view ;
    String id;
    String pid;
    CDepartmentAdapter cDepartmentAdapter;
    @BindView(R.id.lnrBack)
    LinearLayout mLnrBack ;
    @BindView(R.id.txtHeaderName)
    TextView mTxtHeaderName;
    @BindView(R.id.redDepartment)
    RecyclerView mRedDepartment;

    public CScanAndTestDepartmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cscan_and_test_department, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, view);


        mLnrBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.onBackPressed();
            }
        });
        SharedPreferences pref = getActivity().getSharedPreferences("MY" , Context.MODE_PRIVATE);
        id = pref.getString("depart_name", "");
         pid = pref.getString("providerId","");
       // Log.d("PARAM::", id);
        mTxtHeaderName.setText(id);
        cDepartmentAdapter = new CDepartmentAdapter(mContext , departmentList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRedDepartment.setLayoutManager(layoutManager);
        mRedDepartment.setAdapter(cDepartmentAdapter);
        providers();
        return view;
    }

    private void providers() {
        //appUtils.showProgressBarLoading();
        String REGISTER_URL = mContext.getResources().getString(R.string.base_url) + "api/customer/services/"+pid;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, REGISTER_URL,
                new Response.Listener<String>() {
                    public CSaTAdapter.MyViewHolder holder;

                    @Override
                    public void onResponse(String response) {

                        departmentList.clear();
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(response);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        for (int i = 0 ; i < jsonArray.length() ; i++) {
                            JSONObject jsonObject = jsonArray.optJSONObject(i);
                            String departmentname = jsonObject.optString("department");
                            if(departmentname.contains(id))
                            {
                                String id = jsonObject.optString("id");
                                String name = jsonObject.optString("name");
                                String service_type = jsonObject.optString("service_type");
// String departmentname = jsonObject.optString("department");
                                String address = jsonObject.optString("short_description");
                                String logo = jsonObject.optString("image");
                                String price_description = jsonObject.optString("price_description");
                                String price = jsonObject.optString("price");
                                Department department = new Department();
//saT.setImage(mContext.getResources().getString(R.string.base_url) + logo);
                                department.setName(name);
                                department.setService_type(service_type);
                                department.setPrice(price);
                                department.setId(id);
                                department.setImage(logo);
                                departmentList.add(department);
                            }

                        }
                        cDepartmentAdapter = new CDepartmentAdapter(mContext , departmentList);
                        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        mRedDepartment.setLayoutManager(mLayoutManager);
                        mRedDepartment.setItemAnimator(new DefaultItemAnimator());
                        mRedDepartment.setAdapter(cDepartmentAdapter);
                        mRedDepartment.setNestedScrollingEnabled(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(mContext , error.networkResponse.statusCode , Toast.LENGTH_LONG).show();
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
                // auth_token = "324cf5c7-67f7-489e-959d-5b98ea9c8b6a";
                Map<String, String>  params = new HashMap<String, String>();
                params.put("auth-token", auth_token);
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }
}
