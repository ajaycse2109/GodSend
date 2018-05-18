/*
package com.oodi.godsendapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConfirmedConsult extends Activity {
    DatabaseReference rootRef, childref;
    DatabaseReference rootRef1, listOrders, ref, lef;
    String URL_JSON_OBJECT, did, dname, specility, imageview, n;
    ImageView imageView;
    String pid, Imageurl, docname, speciality;
    // Defining the Volley request queue that handles the URL request concurrently
    RequestQueue requestQueue;
    ArrayList<String> orderItems = new ArrayList<>();
    List<Item> mItems = new ArrayList<>();
    RecyclerConfirmed madapter;
    RecyclerView recyclerviewa;
    JSONObject notitem;
    JSONArray notilist, notic;
    Toolbar toolbar;
    private ProgressDialog pDialog;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private LinearLayoutManager mLayoutManager;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmconsult);
        imageView = (ImageView) findViewById(R.id.backimg);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        showProgressDialog();
        sharedpreferences = getSharedPreferences("mypreference",
                Context.MODE_PRIVATE);

        pid = sharedpreferences.getString("PID",null);
        did = sharedpreferences.getString("DID",null);
        
   */
/*     pid = getIntent().getStringExtra("pid");
        did = getIntent().getStringExtra("did");*//*

        System.out.println(did);
      */
/*
        Imageurl = getIntent().getStringExtra("imageurl");
        docname = getIntent().getStringExtra("docname");
        speciality = getIntent().getStringExtra("specility");
        //docdegl[0] = getIntent().getStringExtra("docdegl");
        n = getIntent().getStringExtra("notino");*//*

        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
      */
/*          Intent intent = new Intent(ConfirmedConsult.this, PatientDashboard.class);
                intent.putExtra("did",did);
                intent.putExtra("pid",pid);
              *//*
*/
/*  intent.putExtra("docname",docname);
                intent.putExtra("specility",speciality);
                // intent.putExtra("imageurl",imageview);
                //intent.putExtra("docdegl",docdegl[0]);
                intent.putExtra("notino",n);*//*
*/
/*
                startActivity(intent);*//*

                finish();
            }
        });

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //did=getIntent().getStringExtra("did");
        // URL_JSON_OBJECT="https://therightdoctors.com/api/beta/opm/doctor/todays/appoinments/all/"+did+"?token=trd_token&key=7xOyNH554tY83cBN7Ktpw3s1y68ql6Eg";
        URL_JSON_OBJECT = "https://therightdoctors.com/api/beta/opm/doctor-patient/all/requests/" + pid + "?token=trd_token&key=7xOyNH554tY83cBN7Ktpw3s1y68ql6Eg&doctor_id_c=" + did + "";
        recyclerviewa = (RecyclerView) findViewById(R.id.my_recycler_confirmed);
        recyclerviewa.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(ConfirmedConsult.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerviewa.setLayoutManager(mLayoutManager);
        madapter = new RecyclerConfirmed(this, mItems);
        recyclerviewa.setAdapter(madapter);
        requestQueue = Volley.newRequestQueue(this);

        System.out.println("url:" + URL_JSON_OBJECT);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL_JSON_OBJECT, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String jsonValue = null;
                if (response != null) {
                    String result = response.optString("message");
                    Boolean status = response.optBoolean("success");
                    if (status) {

                        try {
                            notitem = new JSONObject();
                            notilist = new JSONArray();
                            notic = new JSONArray();
                            notilist = response.optJSONArray("data");
                            if (notilist.length() > 0) {
                                for (int i = 0; i < notilist.length(); i++) {
                                    JSONObject jobj = notilist.getJSONObject(i);
                                    jsonValue = jobj.getString("booking_flag");
                                    if (jsonValue.equals("confirm")) {
                                        notic.put(jobj);
                                        String time = jobj.getString("start_time");
                                        String date = jobj.getString("appoinment_date");
                                        String type = jobj.getString("booking_type");
                                        String doctor = jobj.getString("doctor_name");
                                        String bid = jobj.getString("booking_id");
                                        String fee = jobj.getString("fee");
                                        String docimg = jobj.getString("doc_image");
                                        String payment = jobj.getString("payment_flag");
                                        String patient_img;

                                        orderItems.add(time);
                                        orderItems.add(date);
                                        orderItems.add(type);
                                        orderItems.add(doctor);

                                        Item n = new Item(type, date, time, doctor, pid, did, bid, fee,payment,docimg);
                                        mItems.add(n);
                                        madapter.notifyDataSetChanged();
                                    }

                                }
                            } else {
                                pDialog.dismiss();
                                Snackbar snackbar = Snackbar
                                        .make(findViewById(android.R.id.content), "You don't have any consultations Today  or check  if any  in Confirmed Consultations Paymnet Due ", Snackbar.LENGTH_LONG);
                                snackbar.setDuration(5000);
                                snackbar.show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                        // hideProgressDialog();
                        pDialog.dismiss();
                    }


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // do something
                pDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Snackbar snackbar = Snackbar
                            .make(findViewById(android.R.id.content), "TimeOut or no Connection", Snackbar.LENGTH_LONG);
                    snackbar.setDuration(5000);
                    snackbar.show();
                } else if (error instanceof AuthFailureError) {
                    //TODO
                    Snackbar snackbar = Snackbar
                            .make(findViewById(android.R.id.content), "AuthFailure try again with Correct Credentials", Snackbar.LENGTH_LONG);
                    snackbar.setDuration(5000);
                    snackbar.show();
                } else if (error instanceof ServerError) {
                    //TODO
                    Snackbar snackbar = Snackbar
                            .make(findViewById(android.R.id.content), "Server Error Please Try After Some time", Snackbar.LENGTH_LONG);
                    snackbar.setDuration(5000);
                    snackbar.show();
                } else if (error instanceof NetworkError) {
                    //TODO
                    Snackbar snackbar = Snackbar
                            .make(findViewById(android.R.id.content), "Please the check the Network", Snackbar.LENGTH_LONG);
                    snackbar.setDuration(5000);
                    snackbar.show();
                } else if (error instanceof ParseError) {
                    //TODO
                    Snackbar snackbar = Snackbar
                            .make(findViewById(android.R.id.content), "Parse Error", Snackbar.LENGTH_LONG);
                    snackbar.setDuration(5000);
                    snackbar.show();
                }

            }
        });


        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(3 * DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 0));
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0, 0, 0));
        // Adds the JSON object request "obreq" to the request queue
        requestQueue.add(jsonObjectRequest);




    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }


    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }


}

    
*/
