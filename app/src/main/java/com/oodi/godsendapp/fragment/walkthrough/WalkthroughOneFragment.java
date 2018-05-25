package com.oodi.godsendapp.fragment.walkthrough;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Region;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.android.gms.maps.OnMapReadyCallback;
import com.oodi.godsend.R;
import com.oodi.godsendapp.activity.GPSService;
import com.oodi.godsendapp.activity.MainActivity;
import com.oodi.godsendapp.activity.WalkthroughActivity;
import com.oodi.godsendapp.util.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalkthroughOneFragment extends Fragment implements  LocationListener {
    private static final int PICKFILE_RESULT_CODE = 100;
    Activity mContext ;
    View view ;
    AppUtils appUtils;
    boolean mLocationPermissionGranted = false;
    final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private int mYear, mMonth, mDay;
    String DOB = "" , gender= "M";
    @BindView(R.id.txtUseGPS)
    ImageView mtxtUseGPS;
    private static final int RESULT_PICK_CONTACT = 1001;
    @BindView(R.id.imgEmergency_contact)
    ImageView mImgEmergency_contact;
    @BindView(R.id.lnrImg)
    LinearLayout mLnrImg;
    @BindView(R.id.sv)
    ScrollView mSv ;
    @BindView(R.id.btnNext)
    Button mBtnNext;
    @BindView(R.id.edtFirstName)
    EditText mEdtFirstName;
    @BindView(R.id.edtSecondName)
    EditText mEdtSecondName;
    @BindView(R.id.edtAddress)
    EditText mEdtAddress;
    @BindView(R.id.edtGender)
    TextView mEdtGender;
    @BindView(R.id.edtDOB)
    TextView mEdtDOB;

    @BindView(R.id.edtNumber)
    EditText mEdtNumber;
    private static final int RQS_OPEN_IMAGE = 1;
    private static final int RQS_GET_IMAGE = 2;
    private static final int RQS_PICK_IMAGE = 3;
    public WalkthroughOneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_walkthrough_one, container, false);
        mContext = getActivity() ;
        ButterKnife.bind(this , view);
        appUtils = new AppUtils(mContext);

        SharedPreferences prefs = mContext.getSharedPreferences("Login", Context.MODE_PRIVATE);
        final String auth_token = prefs.getString("auth_token", "");
        Log.e("auth" , auth_token);
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

                profile();
               // update_profile();

            }
        });

        mEdtDOB.setOnClickListener(new View.OnClickListener() {
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

                                DOB = year + "-" + month + "-" + day;
                                mEdtDOB.setText(day + "-" + month + "-" + year);
                               // mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());

                            }
                        }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            }
        });

        mEdtGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(mContext, mEdtGender);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Male")){
                            mEdtGender.setText("Male");
                            gender = "M";
                        }
                        else if(item.getTitle().equals("Female"))
                        {
                            mEdtGender.setText("Female");
                            gender ="F";
                        }
                        else  {
                            mEdtGender.setText("Other");
                            gender = "O";
                        }
                        return true;
                    }
                });

                popup.show();
            }
        });
mtxtUseGPS.setOnClickListener(new View.OnClickListener() {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {


        getLocationPermission();

        mtxtUseGPS.setImageResource(R.drawable.gps_selected);
        String address = "";
        GPSService mGPSService = new GPSService(mContext);
        mGPSService.getLocation();

        if (mGPSService.isLocationAvailable == false) {


        // Here you can ask the user to try again, using return; for that
           // Toast.makeText(mContext, "Your location is not available, please try again.", Toast.LENGTH_SHORT).show();
            return;

            // Or you can continue without getting the location, remove the return; above and uncomment the line given below
            // address = "Location not available";
        } else {

            // Getting location co-ordinates
            double latitude = mGPSService.getLatitude();
            double longitude = mGPSService.getLongitude();
            // Toast.makeText(mContext, "Latitude:" + latitude + " | Longitude: " + longitude, Toast.LENGTH_LONG).show();

            address = mGPSService.getLocationAddress();
        }

        //Toast.makeText(mContext, "Your address is: " + address, Toast.LENGTH_SHORT).show();
        mEdtAddress.setText(address);
// make sure you close the gps after using it. Save user's battery power
        mGPSService.closeGPS();
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


        mEdtFirstName.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtFirstName.setHint("");
                return false;
            }

        });
        mEdtSecondName.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtSecondName.setHint("");
                return false;
            }

        });
        mEdtAddress.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtAddress.setHint("");
                return false;
            }

        });
        mEdtGender.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtGender.setHint("");
                return false;
            }

        });
        mEdtDOB.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtDOB.setHint("");
                return false;
            }

        });
        mEdtNumber.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtNumber.setHint("");
                return false;
            }

        });
        mEdtSecondName.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtSecondName.setHint("");
                return false;
            }

        });
        return view;
    }
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){

            WalkthroughActivity.mImgBack.setVisibility(View.INVISIBLE);
            WalkthroughActivity.mTxtSkip.setVisibility(View.INVISIBLE);

            WalkthroughActivity.mTxtSkip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext , MainActivity.class);
                    startActivity(intent);
                }
            });

        }
    }

    private void profile() {

        appUtils.showProgressBarLoading();

        String REGISTER_URL = mContext.getResources().getString(R.string.base_url) + "api/customer/profile/";

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

                        String status = jsonObject.optString("status");

                        if (status.equals("success")){
                            WalkthroughActivity.mImgBack.setVisibility(View.VISIBLE);
                            WalkthroughActivity.mTxtSkip.setVisibility(View.VISIBLE);

                            WalkthroughActivity.mViewpager.setCurrentItem(1);
                        }

                        appUtils.dismissProgressBar();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        update_profile();

                        //Toast.makeText(mContext , error.networkResponse.statusCode , Toast.LENGTH_LONG).show();
                        appUtils.dismissProgressBar();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("gender" , gender);
                params.put("address" , mEdtAddress.getText().toString());
                params.put("dob" , DOB);
                params.put("emergency_contact_phone" , mEdtNumber.getText().toString());
                params.put("emergency_contact_name" , "Test");
                params.put("first_name" , mEdtFirstName.getText().toString());
                params.put("last_name" , mEdtSecondName.getText().toString());

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

    private void update_profile() {

        appUtils.showProgressBarLoading();

        String REGISTER_URL = mContext.getResources().getString(R.string.base_url) + "api/customer/profile/";

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, REGISTER_URL,
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
                            WalkthroughActivity.mImgBack.setVisibility(View.VISIBLE);
                            WalkthroughActivity.mTxtSkip.setVisibility(View.VISIBLE);

                            WalkthroughActivity.mViewpager.setCurrentItem(1);
                        }

                        appUtils.dismissProgressBar();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext , error.networkResponse.statusCode , Toast.LENGTH_LONG).show();
                         appUtils.dismissProgressBar();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("gender" , gender);
                params.put("address" , mEdtAddress.getText().toString());
                params.put("dob" , DOB);
                params.put("emergency_contact_phone" , mEdtNumber.getText().toString());
                params.put("emergency_contact_name" , "Test");
                params.put("first_name" , mEdtFirstName.getText().toString());
                params.put("last_name" , mEdtSecondName.getText().toString());

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
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}
