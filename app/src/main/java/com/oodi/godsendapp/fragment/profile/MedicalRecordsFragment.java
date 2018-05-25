package com.oodi.godsendapp.fragment.profile;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v4.app.Fragment;

import android.view.LayoutInflater;

import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.PhoneAuthProvider;
import com.oodi.godsend.R;
import com.oodi.godsendapp.activity.AppHelper;
import com.oodi.godsendapp.activity.VolleyMultipartRequest;
import com.oodi.godsendapp.activity.VolleySingleton;
import com.oodi.godsendapp.activity.WalkthroughActivity;
import com.oodi.godsendapp.fragment.RootFragment;
import com.oodi.godsendapp.util.AppUtils;
import com.oodi.godsendapp.util.Session;


import org.json.JSONException;
import org.json.JSONObject;

import static android.Manifest.permission.CAMERA;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.CAMERA;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicalRecordsFragment extends RootFragment implements AdapterView.OnItemSelectedListener {
    AppUtils appUtils;
    Activity mContext;
    Session session;
    public  int EdtNumber=1;
    public  int EdtNumberr=1;
    public  int EdtNumberrr=1;
    View view;
    private static final int PICKFILE_RESULT_CODE = 100;
    @BindView(R.id.attachfile)
    ImageView mattachfile;
    @BindView(R.id.spinner)
    Spinner mspinner;
    @BindView(R.id.edtRecords)
    EditText medtRecords;
    @BindView(R.id.lnrBack)
    LinearLayout mLnrBack;
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
    @BindView(R.id.txtHeaderName)
    TextView mTxtHeaderName;
    @BindView(R.id.txtAddmore)
    ImageView mtxtAddmore;
    @BindView(R.id.btnFinish)
    Button msavechanges;
    private static final int RQS_OPEN_IMAGE = 1;
    private static final int RQS_GET_IMAGE = 2;
    private static final int RQS_PICK_IMAGE = 3;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    String token = "";

    public MedicalRecordsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_medical_records, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, view);
        appUtils = new AppUtils(mContext);
        SharedPreferences prefs = mContext.getSharedPreferences("Login", MODE_PRIVATE);
        final String auth_token = prefs.getString("auth_token", "");
        Log.e("auth" , auth_token);
        mTxtHeaderName.setText("Vitals");

        mLnrBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.onBackPressed();
            }
        });

        // Spinner click listener
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

        mattachfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //    intent.setType("file/*");
                startActivityForResult(intent, RQS_OPEN_IMAGE);
                Toast.makeText(mContext, "please choose file", Toast.LENGTH_LONG).show();

            }
        });
        msavechanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attachfile();
               // saveProfileAccount();
            }
        });
        mght.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mght.setHint("");
                return false;
            }

        });
        mhgt.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mhgt.setHint("");
                return false;
            }

        });
     /*   mEdtHsp11.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHsp11.setHint("");
                return false;
            }

        });
        mEdtHsp22.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHsp22.setHint("");
                return false;
            }

        });
        mEdtHp1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHp1.setHint("");
                return false;
            }

        });
        mEdtHp2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHp2.setHint("");
                return false;
            }

        });
        mEdtHp3.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHp3.setHint("");
                return false;
            }

        });*/
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

   private void attachfile() {

        appUtils.showProgressBarLoading();

        String SAVECHANGES_URL = mContext.getResources().getString(R.string.base_url) + "api/customer/vitalinfo/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SAVECHANGES_URL,
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
                            Toast.makeText(mContext , "updated" , Toast.LENGTH_LONG).show();
/*WalkthroughActivity.mImgBack.setVisibility(View.VISIBLE);
                            WalkthroughActivity.mTxtSkip.setVisibility(View.VISIBLE);

                            WalkthroughActivity.mViewpager.setCurrentItem(1);*/
                        }

                        appUtils.dismissProgressBar();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // update_profile();

                        Toast.makeText(mContext , error.networkResponse.statusCode , Toast.LENGTH_LONG).show();
                        appUtils.dismissProgressBar();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("height" ,mhgt.getText().toString() );
                params.put("weight" , mght.getText().toString());
                params.put("blood_group" , mspinner.getSelectedItem().toString());
                params.put("medical_conditions" , mEdtHsp11.getText().toString());
                params.put("medications" , "Test");
                params.put("allergies" , mEdtHp1.getText().toString());

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
//    private void saveProfileAccount() {
//        // loading or check internet connection or something...
//        // ... then
//        String SAVECHANGES_URL = mContext.getResources().getString(R.string.base_url) + "api/customer/upload/";
//        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, SAVECHANGES_URL, new Response.Listener<NetworkResponse>() {
//            @Override
//            public void onResponse(NetworkResponse response) {
//                        String resultResponse = new String(response.data);
//                        try {
//                            JSONObject result = new JSONObject(resultResponse);
//                            String status = result.getString("status");
//                            if (status.equals("success")) {
//                                Toast.makeText(mContext , "image file updated" , Toast.LENGTH_LONG).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        NetworkResponse networkResponse = error.networkResponse;
//                        String errorMessage = "Unknown error";
//                        if (networkResponse == null) {
//                            if (error.getClass().equals(TimeoutError.class)) {
//                                errorMessage = "Request timeout";
//                            } else if (error.getClass().equals(NoConnectionError.class)) {
//                                errorMessage = "Failed to connect server";
//                            }
//                        } else {
//                            String result = new String(networkResponse.data);
//                            try {
//                                JSONObject response = new JSONObject(result);
//                                String status = response.getString("status");
//                                String message = response.getString("message");
//
//                                Log.e("Error Status", status);
//                                Log.e("Error Message", message);
//
//                                if (networkResponse.statusCode == 404) {
//                                    errorMessage = "Resource not found";
//                                } else if (networkResponse.statusCode == 401) {
//                                    errorMessage = message+" Please login again";
//                                } else if (networkResponse.statusCode == 400) {
//                                    errorMessage = message+ " Check your inputs";
//                                } else if (networkResponse.statusCode == 500) {
//                                    errorMessage = message+" Something is getting wrong";
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        Log.i("Error", errorMessage);
//                        error.printStackTrace();
//                    }
//                }) {
//                    @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//
//                SharedPreferences prefs = mContext.getSharedPreferences("Login", Context.MODE_PRIVATE);
//                final String auth_token = prefs.getString("auth_token", "");
//
//                Map<String, String>  params = new HashMap<String, String>();
//                params.put("auth_token", auth_token);
//                params.put("record_type" ,"PRESCRIPTION");
//                params.put("file_name" , "filenametesting.pdf");
//                params.put("description" , "medicalstesting");
//               // params.put("name", mNameInput.getText().toString());
//               // params.put("location", mLocationInput.getText().toString());
//              //  params.put("about", mAvatarInput.getText().toString());
//              //  params.put("contact", mContactInput.getText().toString());
//                return params;
//            }
//            @Override
//            protected Map<String, DataPart> getByteData() {
//                Map<String, DataPart> params = new HashMap<>();
//                // file name could found file base or direct access from real path
//                // for now just get bitmap data from ImageView
//                params.put("recfile", new DataPart("file_avatar.jpg", AppHelper.getFileDataFromDrawable(mContext.getBaseContext(), mattachfile.getDrawable()), "image/jpeg"));
//             //  params.put("cover", new DataPart("file_cover.jpg", AppHelper.getFileDataFromDrawable(mContextgetBaseContext(), mCoverImage.getDrawable()), "image/jpeg"));
//
//                return params;
//            }
//        };
//
//        VolleySingleton.getInstance(mContext.getBaseContext()).addToRequestQueue(multipartRequest);
//    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {


            if (requestCode == RQS_OPEN_IMAGE ||
                    requestCode == RQS_GET_IMAGE ||
                    requestCode == RQS_PICK_IMAGE) {

                //imageView.setImageBitmap(null);
                // textInfo1.setText("");
                medtRecords.setText("");

                Uri mediaUri = data.getData();
               // File myFile = new File(String.valueOf((mediaUri)));
                String FileName = mediaUri.getLastPathSegment();
                //FileName.substring(FileName.lastIndexOf("."));
                //textInfo1.setText(mediaUri.toString());
               String mediaPath = mediaUri.getPath();
                medtRecords.setText(" \n" + FileName +"\n");
                //display the image
                try {
                    InputStream inputStream = mContext.getBaseContext().getContentResolver().openInputStream(mediaUri);
                    Bitmap bm = BitmapFactory.decodeStream(inputStream);
                    //imageView.setImageBitmap(bm);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

