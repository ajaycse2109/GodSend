package com.oodi.godsendapp.fragment.hospital.cunsultation;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.oodi.godsendapp.GlobalClass;
import com.oodi.godsendapp.activity.MainActivity;
import com.oodi.godsendapp.activity.WalkthroughActivity;
import com.oodi.godsendapp.adapter.CSaTAdapter;
import com.oodi.godsendapp.adapter.DateSelectionAdapter;
import com.oodi.godsendapp.fragment.RootFragment;
import com.oodi.godsendapp.fragment.hospital.SaTReviewFragment;
import com.oodi.godsendapp.pojo.SaT;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class CScanAndTestDetailsFragment extends RootFragment {
    Activity mContext;
    View view;
    String id;
    public String dates=null,times=null,dt;
    private int mYear, mMonth, mDay;
    android.app.AlertDialog alertDialog ;
    String[] mTimeSlotList = {"09:00-09:30",
            "09:30-10:00",
            "10:00-10:30",
            "10:30-11:00",
            "11:00-11:30",
            "11:30-12:00",
            "12:00-12:30",
            "12:30-01:00",
            "01:00-01:30",
            "01:30-02:00",
            "02:00-02:30",
            "02:30-03:00",
            "03:00-03:30",
            "03:30-04:00",
            "04:00-04:30",
            "04:30-05:00",
            "05:00-05:30",
            "05:30-06:00"};
    DateSelectionAdapter mDateSelectionAdapter ;
    @BindView(R.id.lnrBack)
    LinearLayout mLnrBack;
    @BindView(R.id.txtHeaderName)
    TextView mTxtHeaderName;
    @BindView(R.id.btnConfirm)
    Button mBtnConfirm;
    @BindView(R.id.txtDepartment)
    TextView mTxtDepartment;
    @BindView(R.id.txtDate)
    TextView mTxtDate;
    @BindView(R.id.txtTime)
    TextView mTxtTime;
    @BindView(R.id.depname)
    TextView mdepname;
    @BindView(R.id.edtNotes)
    EditText medtNotes;
    @BindView(R.id.imgServices)
    ImageView imgServ;
    public CScanAndTestDetailsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_scan_and_test_details, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, view);
        mTxtHeaderName.setText("Book Appointment");
        mLnrBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.onBackPressed();
            }
        });
        mTxtDepartment.setVisibility(View.VISIBLE);
        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               book();
            }
        });
        SharedPreferences pref = getActivity().getSharedPreferences("MY" , Context.MODE_PRIVATE);
       String serName = pref.getString("service_name", "");
        String serType = pref.getString("service_type", "");
        String serImg = pref.getString("service_img","");
        Picasso.with(mContext).load(serImg).fit().centerCrop().into(imgServ);
       mdepname.setText(serName);
       mTxtDepartment.setText(serType);
//        Log.d("PARAM::", id);
        mTxtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                String day = "";
                                String month = "";

                                if (dayOfMonth == 1) {
                                    day = String.valueOf("01");
                                } else if (dayOfMonth == 2) {
                                    day = String.valueOf("02");
                                } else if (dayOfMonth == 3) {
                                    day = String.valueOf("03");
                                } else if (dayOfMonth == 4) {
                                    day = String.valueOf("04");
                                } else if (dayOfMonth == 5) {
                                    day = String.valueOf("05");
                                } else if (dayOfMonth == 6) {
                                    day = String.valueOf("06");
                                } else if (dayOfMonth == 7) {
                                    day = String.valueOf("07");
                                } else if (dayOfMonth == 8) {
                                    day = String.valueOf("08");
                                } else if (dayOfMonth == 9) {
                                    day = String.valueOf("09");
                                } else {
                                    day = String.valueOf(dayOfMonth);
                                }

                                if (monthOfYear == 0) {
                                    month = String.valueOf("01");
                                } else if (monthOfYear == 1) {
                                    month = String.valueOf("02");
                                } else if (monthOfYear == 2) {
                                    month = String.valueOf("03");
                                } else if (monthOfYear == 3) {
                                    month = String.valueOf("04");
                                } else if (monthOfYear == 4) {
                                    month = String.valueOf("05");
                                } else if (monthOfYear == 5) {
                                    month = String.valueOf("06");
                                } else if (monthOfYear == 6) {
                                    month = String.valueOf("07");
                                } else if (monthOfYear == 7) {
                                    month = String.valueOf("08");
                                } else if (monthOfYear == 8) {
                                    month = String.valueOf("09");
                                } else {
                                    month = String.valueOf(monthOfYear + 1);
                                }

                                //DOB = year + "-" + month + "-" + day;
                                mTxtDate.setText(day + "-" + month + "-" + year);
dates = year+"-"+month+"-"+day;
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        mTxtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeList();
            }
        });

        LocalBroadcastManager.getInstance(mContext).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));
        providers();
        return view;
    }

    public  void book()
    {
        String REGISTER_URL = mContext.getResources().getString(R.string.base_url) + "api/customer/appointment/add/";

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

                        MainActivity mainActivity = (MainActivity)mContext;
                        FragmentTransaction transaction = mainActivity.getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.root_hospital, new CSaTReviewFragment());
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        transaction.addToBackStack(null);
                        transaction.commit();

                        //String status = jsonObject.optString("status");



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // update_profile();

                        //Toast.makeText(mContext , error.networkResponse.statusCode , Toast.LENGTH_LONG).show();
                       // appUtils.dismissProgressBar();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                SharedPreferences pref = getActivity().getSharedPreferences("MY" , Context.MODE_PRIVATE);
          String    pid = pref.getString("providerId", "");
          String name= pref.getString("prof_name","");
String sid=pref.getString("services_id","");
String notes= medtNotes.getText().toString();
if(notes == null)
    notes="";

dt=dates +" "+times.substring(0,5);

String time = times.substring(0,5);
String monthName;
int monthNumber = Integer.parseInt(dates.substring(5,7));
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MONTH, monthNumber-1);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM");
                simpleDateFormat.setCalendar(calendar);
                monthName = simpleDateFormat.format(calendar.getTime()).substring(0,3);

                String date = dates.substring(8,10);
                String year = dates.substring(0,4);

                String dtString = time +" "+monthName+" "+date+" "+year;
                SharedPreferences sharedpreferences = mContext.getSharedPreferences("MY" , Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("dtString",dtString);
                editor.commit();

                params.put("provider_id" , pid);
                params.put("appointment_details" , "[{\"service_id\":"+sid+", \"quantity\": 1}]");
                params.put("booked_for" , dt);
                params.put("patient_name" , name);
                params.put("note" ,notes );
                params.put("appointment_type" , "1");
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

    private void providers() {

        //appUtils.showProgressBarLoading();

        String REGISTER_URL = mContext.getResources().getString(R.string.base_url) + "api/customer/services/1";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, REGISTER_URL,
                new Response.Listener<String>() {
                    public CSaTAdapter.MyViewHolder holder;

                    @Override
                    public void onResponse(String response) {

                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(response);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                        for (int i = 0 ; i < jsonArray.length() ; i++) {

                            JSONObject jsonObject = jsonArray.optJSONObject(i);

                            String id1 = jsonObject.optString("id");
                            String name = jsonObject.optString("name");
                            String service_type = jsonObject.optString("service_type");
                            String departmentname = jsonObject.optString("department");
                            String address = jsonObject.optString("short_description");
                            String logo = jsonObject.optString("image");
                            String price_description = jsonObject.optString("price_description");
                            String price = jsonObject.optString("price");
                           // mdepname.setText(id);
                        }
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
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!intent.hasExtra("item_name")){
            }
            else {
                String item_name = intent.getStringExtra("item_name");

                mTxtTime.setText(item_name);
                times = item_name;

                try {
                    alertDialog.dismiss();
                }catch (Exception e){
                }
            }
        }
    };

    public void timeList(){

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View promptsView = layoutInflater.inflate(R.layout.date_selection_popup, null);

        final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(mContext);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setCancelable(true);
        alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(true);

        final TextView Update = (TextView) promptsView.findViewById(R.id.btn_confirm);

        final RecyclerView recyclerView = (RecyclerView) promptsView.findViewById(R.id.list);

        mDateSelectionAdapter = new DateSelectionAdapter(getActivity() , mTimeSlotList , this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mDateSelectionAdapter);

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


}
