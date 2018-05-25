package com.oodi.godsendapp.fragment.walkthrough;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import com.oodi.godsendapp.pojo.Providers;
import com.squareup.picasso.Picasso;

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
public class WalkthroughTwoFragment extends Fragment {


    public List<Providers> providersList = new ArrayList<>();

    public  List<String> mStrings = new ArrayList<String>();

    Activity mContext ;
    View view ;
public   int EdtNumber=1;
    @BindView(R.id.lnrImg)
    LinearLayout mLnrImg;
    @BindView(R.id.sv)
    ScrollView mSv ;
    @BindView(R.id.btnNext)
    Button mBtnNext;
 @BindView(R.id.txtAddmore)
    TextView mtxtAddmore;
@BindView(R.id.EdtHsp1)
AutoCompleteTextView mEdtHsp1;
    @BindView(R.id.EdtHsp2)
    AutoCompleteTextView mEdtHsp2;
    @BindView(R.id.EdtHsp3)
    AutoCompleteTextView  mEdtHsp3;
    @BindView(R.id.EdtHsp4)
    AutoCompleteTextView mEdtHsp4;
    @BindView(R.id.EdtHsp5)
    AutoCompleteTextView mEdtHsp5;
    public WalkthroughTwoFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_walkthrough_two, container, false);
        mContext = getActivity() ;
        ButterKnife.bind(this , view);
        mLnrImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WalkthroughActivity.mImgBack.setVisibility(View.VISIBLE);
                WalkthroughActivity.mTxtSkip.setVisibility(View.VISIBLE);

                mLnrImg.setVisibility(View.GONE);
                mSv.setVisibility(View.VISIBLE);
            }
        });

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WalkthroughActivity.mImgBack.setVisibility(View.VISIBLE);
                WalkthroughActivity.mTxtSkip.setVisibility(View.VISIBLE);
                WalkthroughActivity.mViewpager.setCurrentItem(3);
            }
        });
        mEdtHsp1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHsp1.setHint("");
                return false;
            }

        });
        mEdtHsp2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHsp2.setHint("");
                return false;
            }

        });
        mEdtHsp3.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHsp3.setHint("");
                return false;
            }

        });
        mEdtHsp4.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHsp4.setHint("");
                return false;
            }

        });
        mEdtHsp4.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHsp4.setHint("");
                return false;
            }

        });
 mtxtAddmore.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        if(EdtNumber == 1)
        {
            mEdtHsp2.setVisibility(View.VISIBLE);
            EdtNumber ++;
        }
        else
        if(EdtNumber == 2)
        {
            mEdtHsp3.setVisibility(View.VISIBLE);
            EdtNumber ++;
        }
        else if(EdtNumber == 3)
        {
            mEdtHsp4.setVisibility(View.VISIBLE);
            EdtNumber ++;
        }
        else if(EdtNumber == 4)
        {
            mEdtHsp5.setVisibility(View.VISIBLE);
            EdtNumber ++;
        }
        else
        {
            Toast.makeText(mContext, "Sorry Limit Exceeds", Toast.LENGTH_SHORT).show();
        }

    }


});
prepareProvidersData();
        return view;
    }

    public void prepareProvidersData() {




        String REGISTER_URL = mContext.getResources().getString(R.string.base_url) + "api/customer/providers/";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(response);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        mStrings.clear();
                        for (int i = 0 ; i < jsonArray.length() ; i++){


                            JSONObject jsonObject = jsonArray.optJSONObject(i);

                            String id = jsonObject.optString("id");
                            String name = jsonObject.optString("name");
                            String category = jsonObject.optString("category");
                            String phone = jsonObject.optString("phone");
                            String address = jsonObject.optString("address");
                            String logo = jsonObject.optString("logo");

                            mStrings.add(name);

                            Providers p = new Providers(name,logo);
                            providersList.add(p);

//                            mTxtNearestName.setText(name);
//                            Picasso.with(mContext)
//                                    .load(logo)
//                                    .fit().centerCrop()
//                                    .into(mImgNearest);
//                            mTxtPH.setText(name);
//                            Picasso.with(mContext)
//                                    .load(logo)
//                                    .into(mImgPH);

                        }
                        String[] mStringArray = new String[mStrings.size()];
                        mStringArray = mStrings.toArray(mStringArray);
                        ArrayAdapter<String> Hospitaladapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mStringArray);
                        mEdtHsp1.setThreshold(1);
                        mEdtHsp1.setAdapter(Hospitaladapter);
                        mEdtHsp1.setTextColor(Color.BLACK);

                        mEdtHsp2.setThreshold(1);
                        mEdtHsp2.setAdapter(Hospitaladapter);
                        mEdtHsp2.setTextColor(Color.BLACK);

                        mEdtHsp3.setThreshold(1);
                        mEdtHsp3.setAdapter(Hospitaladapter);
                        mEdtHsp3.setTextColor(Color.BLACK);

                        mEdtHsp4.setThreshold(1);
                        mEdtHsp4.setAdapter(Hospitaladapter);
                        mEdtHsp4.setTextColor(Color.BLACK);

                        mEdtHsp5.setThreshold(1);
                        mEdtHsp5.setAdapter(Hospitaladapter);
                        mEdtHsp5.setTextColor(Color.BLACK);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(mContext , error.networkResponse.statusCode , Toast.LENGTH_LONG).show();

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

            WalkthroughActivity.mTxtSkip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext , MainActivity.class);
                    startActivity(intent);

                }
            });
        }
    }

}
