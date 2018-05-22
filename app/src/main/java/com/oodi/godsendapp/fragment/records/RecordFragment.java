package com.oodi.godsendapp.fragment.records;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.AlphabeticIndex;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.oodi.godsend.R;
import com.oodi.godsendapp.adapter.RecordAdapter;
import com.oodi.godsendapp.fragment.RootFragment;
import com.oodi.godsendapp.pojo.Records;
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

import static android.icu.util.Calendar.getInstance;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordFragment extends RootFragment {
    JSONObject notitem;
    JSONArray notilist, notic;
    Activity mContext;
    View view ;
    RequestQueue requestQueue;
    List<Records> mRecordsList = new ArrayList<>();
    RecordAdapter mRecordAdapter ;
    @BindView(R.id.allrecordsdropdown)
    Spinner mAllrecords;
    @BindView(R.id.datedropdown)
    Spinner spinyear;
    @BindView(R.id.recRecords)
    RecyclerView mRecRecords;
    @BindView(R.id.btnAddFiles)
    Button mBtnAddFiles;
    @BindView(R.id.txtHeaderName)
    TextView mTxtHeaderName;
    public RecordFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_record, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, view);
      //  mTxtHeaderName.setText("Records");
        mRecordAdapter = new RecordAdapter(mContext , mRecordsList , this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecRecords.setLayoutManager(layoutManager);
        mRecRecords.setAdapter(mRecordAdapter);
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = getInstance().get(Calendar.YEAR);
        for (int i = 2000; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, years);
        spinyear.setAdapter(adapter2);
        String [] values =
                {"All Records","Prescription","Test Report","Scan Image",};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mAllrecords.setAdapter(adapter);


        mAllrecords.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                // String selectedItem = parent.getItemAtPosition(position).toString();
                if (parent.getItemAtPosition(position).toString()
                        .equals("Prescription")) {
                    mAllrecords.setSelection(0,false);
                    FragmentTransaction transaction = getFragmentManager()
                            .beginTransaction();
                    transaction.replace(R.id.root_records, new AddHealthRecordFragment());
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                if (parent.getItemAtPosition(position).toString()
                        .equals("Test Report")) {
                    mAllrecords.setSelection(0,false);
                    FragmentTransaction transaction = getFragmentManager()
                            .beginTransaction();
                    transaction.replace(R.id.root_records, new AddHealthRecordFragment());
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.addToBackStack(null);
                    transaction.commit();}
                if (parent.getItemAtPosition(position).toString()
                        .equals("Scan Image")) {
                    mAllrecords.setSelection(0,false);
                    FragmentTransaction transaction = getFragmentManager()
                            .beginTransaction();
                    transaction.replace(R.id.root_records, new AddHealthRecordFragment());
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.addToBackStack(null);
                    transaction.commit();}
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        mBtnAddFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  //  providers();
                // openBottomSheet();
            }
        });

        return view;
    }
  /*  private void providers()   {

        //   appUtils.showProgressBarLoading();

        String REGISTER_URL = mContext.getResources().getString(R.string.base_url) + "api/customer/records/";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONArray data = null;
                        JSONObject jsonArray = null;
                        try {
                            jsonArray = new JSONObject(response);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                             data =jsonArray.optJSONArray("records");

                        for (int i = 0 ; i < data.length() ; i++){

                            JSONObject jsonObject = null;
                            try {
                                jsonObject = data.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String x=jsonObject.optString("ori_name").toString();
                            Toast.makeText(mContext,x,Toast.LENGTH_LONG).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext , error.networkResponse.statusCode , Toast.LENGTH_LONG).show();
                      //  appUtils.dismissProgressBar();
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


    }*/
    public void openBottomSheet() {

        View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);

        LinearLayout lnrPrescription = view.findViewById(R.id.lnrPrescription);
        LinearLayout lnrTestReport = view.findViewById(R.id.lnrTestReport);
        LinearLayout lnrScanImage = view.findViewById(R.id.lnrScanImage);
        final TextView txtPrescription = view.findViewById(R.id.txtPrescription);
        final TextView txtTestReport = view.findViewById(R.id.txtTestReport);
        final TextView txtScanImage = view.findViewById(R.id.txtScanImage);
        final ImageView imgPrescription = view.findViewById(R.id.imgPrescription);
        final ImageView imgTestReport = view.findViewById(R.id.imgTestReport);
        final ImageView imgScanImage = view.findViewById(R.id.imgScanImage);
        final Dialog mBottomSheetDialog = new Dialog(mContext,
                R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

        lnrPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();

                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.root_records, new AddHealthRecordFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
                txtPrescription.setTextColor(mContext.getResources().getColor(R.color.bg));
                imgPrescription.setImageResource(R.drawable.prescription_active);

                txtTestReport.setTextColor(mContext.getResources().getColor(R.color.black));
                imgTestReport.setImageResource(R.drawable.microscope_active);

                txtScanImage.setTextColor(mContext.getResources().getColor(R.color.black));
                imgScanImage.setImageResource(R.drawable.xrayactive);

            }
        });

        lnrTestReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();

                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.root_records, new AddHealthRecordFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
           txtPrescription.setTextColor(mContext.getResources().getColor(R.color.black));
                imgPrescription.setImageResource(R.drawable.prescription);

                txtTestReport.setTextColor(mContext.getResources().getColor(R.color.bg));
                imgTestReport.setImageResource(R.drawable.microscope_active);

                txtScanImage.setTextColor(mContext.getResources().getColor(R.color.black));
                imgScanImage.setImageResource(R.drawable.xray);

            }
        });

        lnrScanImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();

                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.root_records, new AddHealthRecordFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
              txtPrescription.setTextColor(mContext.getResources().getColor(R.color.black));
                imgPrescription.setImageResource(R.drawable.prescription);

                txtTestReport.setTextColor(mContext.getResources().getColor(R.color.black));
                imgTestReport.setImageResource(R.drawable.microscope);

                txtScanImage.setTextColor(mContext.getResources().getColor(R.color.bg));
                imgScanImage.setImageResource(R.drawable.xrayactive);

            }
        });


    }

}
