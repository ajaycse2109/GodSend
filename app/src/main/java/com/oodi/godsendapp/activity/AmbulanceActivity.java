package com.oodi.godsendapp.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.oodi.godsendapp.activity.GPSService;
import com.oodi.godsend.R;
import com.oodi.godsendapp.adapter.CSaTAdapter;
import com.oodi.godsendapp.adapter.ProvidersAdapter;
import com.oodi.godsendapp.fragment.hospital.ReviewReservationFragment;
import com.oodi.godsendapp.fragment.hospital.cunsultation.CSaTReviewFragment;
import com.oodi.godsendapp.pojo.Providers;
import com.oodi.godsendapp.pojo.SaT;
import com.razorpay.Checkout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.volley.VolleyLog.TAG;

public class AmbulanceActivity extends FragmentActivity implements OnMapReadyCallback {

    public static int loc;
    public static Float minDistance = null;
    public List<Providers> providersList = new ArrayList<>();
    public  List<String> mStrings = new ArrayList<String>();
    public static List<Location> locList = new ArrayList<>();
    public static Location source = new Location("");
    public static Location destination = new Location("");
    public static Activity mContext;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;
    private GoogleMap mMap;
    //  private CustomMapFragment mCustomMapFragment;

    private View mMarkerParentView;
    private ImageView mMarkerImageView;

    private int imageParentWidth = -1;
    private int imageParentHeight = -1;
    private int imageHeight = -1;
    private int centerX = -1;
    private int centerY = -1;
    public double latitude;
    public double longitude;
    @BindView(R.id.lnrBack)
    LinearLayout mLnrBack;
    @BindView(R.id.txtConfirmBooking)
    TextView mTxtConfirmBooking;
    @BindView(R.id.textView)
    TextView mtextView;
    @BindView(R.id.txtPickup)
    TextView mtxtPickup;
    @BindView(R.id.txtNearestHospital)
    AutoCompleteTextView mtxtNearestHospital;
    @BindView(R.id.FrameId)
    FrameLayout f1;
    @BindView(R.id.RelLay)
    RelativeLayout mRelay;
@BindView(R.id.txtPrice)
TextView mtxtPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);
        mContext = this;
        ButterKnife.bind(mContext);
        configureCameraIdle();
        prepareProvidersData();
        getUserLocation();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mtxtNearestHospital.setInputType(0);

        mLnrBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.onBackPressed();
            }
        });
        mtextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Coming Soon");
                builder.setCancelable(true);
                builder.setMessage(mContext.getResources().getString(R.string.canceldailogmsg3));
                builder.setPositiveButton("OK", null);
                builder.show();
            }
        });
        mTxtConfirmBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String hosname = mtxtNearestHospital.getText().toString().substring(0,mtxtNearestHospital.getText().toString().indexOf(","));
                for (Providers p:providersList
                        ) {
                    if(p.get_hospitalName().trim().equalsIgnoreCase(hosname.trim()))
                    {



                        SharedPreferences sharedpreferences = mContext.getSharedPreferences("MY", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("provider_id",p.getProviderid());
                        editor.putString("provider_name", p.get_hospitalName());
                        editor.putString("provider_logo", p.getLogo());
                        editor.commit();
                    }
                }
getServiceId();
                book();
                /*Intent intent = new Intent(mContext , RouteActivity.class);
                startActivity(intent);*/
            }
        });

        mtxtNearestHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtxtNearestHospital.showDropDown();

            }
        });
mtxtNearestHospital.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {

        String hosname = mtxtNearestHospital.getText().toString().substring(0,mtxtNearestHospital.getText().toString().indexOf(",")).trim();
        for (Providers p:providersList
                ) {
            if(p.get_hospitalName().trim().equalsIgnoreCase(hosname.trim()))
            {



                SharedPreferences sharedpreferences = mContext.getSharedPreferences("MY", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("provider_id",p.getProviderid());
                editor.putString("provider_name", p.get_hospitalName());
                editor.putString("provider_logo", p.getLogo());
                editor.commit();
            }
        }
        getServiceId();
        SharedPreferences pref = mContext.getSharedPreferences("MY", Context.MODE_PRIVATE);
        String price = pref.getString("service_price", "");
        mtxtPrice.setText(price);

    }
});
        mtxtPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(mContext);
                    startActivityForResult(intent, 100);
                } catch (Exception e) {
                    Log.e(TAG, e.getStackTrace().toString());
                }
            }
        });





    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 100){
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
//                Toast.makeText(this, "place "+place.toString(),
//                        Toast.LENGTH_LONG).show();

                mtxtPickup.setText(place.getAddress());
                LatLng loc = place.getLatLng();
                latitude=loc.latitude;
                longitude=loc.longitude;
                source.setLatitude(latitude);
                source.setLongitude(longitude);

                CameraPosition cameraPosition ;
                cameraPosition = new CameraPosition.Builder().target(loc).zoom(15).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        }
    }


    private void getServiceId() {

        //appUtils.showProgressBarLoading();
        SharedPreferences prefs = mContext.getSharedPreferences("MY", Context.MODE_PRIVATE);
        String pid = prefs.getString("provider_id", "");

        String REGISTER_URL = mContext.getResources().getString(R.string.base_url) + "api/customer/services/"+pid;

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
                        ArrayList<String> abcList = new ArrayList<>();
                        for (int i = 0 ; i < jsonArray.length() ; i++) {
                            JSONObject jsonObject = jsonArray.optJSONObject(i);
                            String departmentname = jsonObject.optString("department");
                            if(abcList.contains(departmentname))
                            {}
                            else{
                                abcList.add(departmentname);

                                if(departmentname.contains("EMERGENCY")){

String serid = jsonObject.optString("id");
String prce = jsonObject.optString("price");

                                    SharedPreferences sharedpreferences = mContext.getSharedPreferences("MY", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString("service_id",serid);
                                    editor.putString("service_price",prce);
                                    mtxtPrice.setText(prce);
                                    editor.commit();

                                }

                            }
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


    public void book() {
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

//                        MainActivity mainActivity = (MainActivity)mContext;
//                        FragmentTransaction transaction = mainActivity.getSupportFragmentManager().beginTransaction();
//                        transaction.replace(R.id.root_hospital, new CSaTReviewFragment());
//                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                        transaction.addToBackStack(null);
//                        transaction.commit();

                        startPayment();

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





                SharedPreferences pref = mContext.getSharedPreferences("MY", Context.MODE_PRIVATE);
                String pid = pref.getString("provider_id", "");
                String name = pref.getString("prof_name", "");
                String sid=pref.getString("service_id","");
                String notes = "";
                if (notes == null)
                    notes = "";

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date now = new Date();
                String strDate = sdf.format(now);

                params.put("provider_id", pid);
                params.put("appointment_details", "[{\"service_id\":" + sid + ", \"quantity\": 1}]");
                params.put("booked_for", strDate);
                params.put("patient_name", name);
                params.put("note", notes);
                params.put("appointment_type", "2");
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

                Map<String, String> params = new HashMap<String, String>();
                params.put("auth-token", auth_token);
                params.put("Content-Type", "application/x-www-form-urlencoded");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }

    public void getUserLocation() {
        // mtxtUseGPS.setImageResource(R.drawable.gps_selected);
        String address = "";
        GPSService mGPSService = new GPSService(mContext);
        mGPSService.getLocation();

        if (mGPSService.isLocationAvailable == false) {
            //getLocationPermission();
            // Here you can ask the user to try again, using return; for that
            // Toast.makeText(mContext, "Your location is not available, please try again.", Toast.LENGTH_SHORT).show();
            return;

            // Or you can continue without getting the location, remove the return; above and uncomment the line given below
            // address = "Location not available";
        } else {

            // Getting location co-ordinates
            latitude = mGPSService.getLatitude();
            longitude = mGPSService.getLongitude();
            // Toast.makeText(mContext, "Latitude:" + latitude + " | Longitude: " + longitude, Toast.LENGTH_LONG).show();
            source.setLatitude(latitude);
            source.setLongitude(longitude);
            address = mGPSService.getLocationAddress();
            // mtxtPickup.setText(address);
        }

        //Toast.makeText(mContext, "Your address is: " + address, Toast.LENGTH_SHORT).show();
        // mEdtAddress.setText(address);
// make sure you close the gps after using it. Save user's battery power
        //mGPSService.closeGPS();
    }

    private void configureCameraIdle() {
        onCameraIdleListener = new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng latLng = mMap.getCameraPosition().target;
                latitude = latLng.latitude;
                longitude = latLng.longitude;
                Geocoder geocoder = new Geocoder(AmbulanceActivity.this);
                try {
                    List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        String locality = addressList.get(0).getAddressLine(0);
                        String country = addressList.get(0).getCountryName();
                        if (!locality.isEmpty() && !country.isEmpty())
                            mtxtPickup.setText(locality + "  " + country);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
    }

    public void prepareProvidersData() {


//        appUtils.showProgressBarLoading();

        String REGISTER_URL = mContext.getResources().getString(R.string.base_url) + "api/customer/providers/";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, REGISTER_URL,
                new Response.Listener<String>() {
                    @TargetApi(Build.VERSION_CODES.O)
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {

                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(response);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        locList.clear();
                        mStrings.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.optJSONObject(i);
                            String id = jsonObject.optString("id");
                            String name = jsonObject.optString("name");
                            String lat = jsonObject.optString("lat");
                            String lon = jsonObject.optString("lon");
                            String category = jsonObject.optString("category");
                            String phone = jsonObject.optString("phone");
                            String address = jsonObject.optString("address");
                            String logo = jsonObject.optString("logo");
                            Location location = new Location("");
                            location.setLatitude(Double.parseDouble(lat));
                            location.setLongitude(Double.parseDouble(lon));
                            locList.add(location);

                            GPSService gpsServices = new GPSService(mContext);
                            String addresss= gpsServices.getLocationAddress(location);
                            mStrings.add(name+","+addresss.replaceAll("\n",""));

//globalClass.setProviderId(id);
                            Providers p = new Providers();
                            p.setName(name);
                            p.setLogo(logo);
                            p.setProviderid(id);
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

                        minDistance = source.distanceTo(locList.get(0));
                        for (int i = 1; i < locList.size(); i++) {
                            destination = locList.get(i);

                            Float Distance = source.distanceTo(destination);

                            if (Distance < minDistance) {
                                minDistance = Distance;
                                loc = i;
                            }

                        }

                        GPSService gpsService = new GPSService(mContext);
                        String address = gpsService.getLocationAddress(locList.get(loc));
                        String hospName = providersList.get(loc).get_hospitalName();
                        mtxtNearestHospital.setText(hospName + " , " + address.replaceAll("\n",""));
                        SharedPreferences sharedpreferences = mContext.getSharedPreferences("MY", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("provider_id", providersList.get(loc).getProviderid());
                        editor.putString("provider_name", providersList.get(loc).get_hospitalName());
                        editor.putString("provider_logo", providersList.get(loc).getLogo());
                        editor.commit();
//                        mAdapter = new ProvidersAdapter(getActivity() , providersList);
//                        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//                        recyclerView.setLayoutManager(mLayoutManager);
//                        recyclerView.setItemAnimator(new DefaultItemAnimator());
//                        recyclerView.setAdapter(mAdapter);
//                        recyclerView.setNestedScrollingEnabled(false);
//
                        String[] mStringArray = new String[mStrings.size()];
                        mStringArray = mStrings.toArray(mStringArray);
                        ArrayAdapter<String> Hospitaladapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_dropdown_item_1line, mStringArray);
                        mtxtNearestHospital.setThreshold(1);
                        mtxtNearestHospital.setAdapter(Hospitaladapter);
                        mtxtNearestHospital.setTextColor(Color.BLACK);
getServiceId();
                        //         appUtils.dismissProgressBar();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(mContext , error.networkResponse.statusCode , Toast.LENGTH_LONG).show();
                        //  appUtils.dismissProgressBar();
                    }
                }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                SharedPreferences prefs = mContext.getSharedPreferences("Login", MODE_PRIVATE);
                String auth_token = prefs.getString("auth_token", "");

                // auth_token = "324cf5c7-67f7-489e-959d-5b98ea9c8b6a";

                Map<String, String> params = new HashMap<String, String>();
                params.put("auth-token", auth_token);
                params.put("Content-Type", "application/x-www-form-urlencoded");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }

    public void startPayment() {
        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final AmbulanceActivity activity = this;
//        String hosname = mtxtNearestHospital.getText().toString().substring(0,mtxtNearestHospital.getText().toString().indexOf(","));
//String name="",logo="";
//        for (Providers p:providersList
//             ) {
//
//            if(p.get_hospitalName().trim().equalsIgnoreCase(hosname.trim()))
//            {
//                name=p.get_hospitalName();
//                logo = p.getLogo();
//                SharedPreferences sharedpreferences = mContext.getSharedPreferences("MY", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedpreferences.edit();
//                editor.putString("provider_id",p.getProviderid());
//                editor.putString("provider_name", p.get_hospitalName());
//                editor.putString("provider_logo", p.getLogo());
//                editor.commit();
//            }
//        }

        final Checkout co = new Checkout();

        try {

            SharedPreferences pref = mContext.getSharedPreferences("MY", Context.MODE_PRIVATE);
            String logo = pref.getString("provider_logo", "");
            String name = pref.getString("provider_name", "");
            String amt = pref.getString("service_price","")+"00";

            JSONObject options = new JSONObject();
            options.put("name", name);
            options.put("description", "Emergency");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", logo);
            options.put("currency", "INR");
            options.put("amount", amt);
            JSONObject preFill = new JSONObject();
            preFill.put("email", "");
            preFill.put("contact", "");

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")

    public LatLng getLocationFromAddress(String address)
    {
        LatLng latLng=null;
        Geocoder coder = new Geocoder(this);
        List<Address> addresses;
        try {
            addresses = coder.getFromLocationName(address, 5);
            if (addresses == null) {
            }
            Address location = addresses.get(0);
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            Log.i("Lat",""+lat);
            Log.i("Lng",""+lng);
            latLng = new LatLng(lat,lng);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return latLng;
    }


    public void onPaymentSuccess(String razorpayPaymentID) {

        mRelay.setVisibility(View.INVISIBLE);
        Toast.makeText(mContext, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();




//LatLng sourcell = getLocationFromAddress(mtxtPickup.getText().toString());
//LatLng destll = getLocationFromAddress(mtxtNearestHospital.getText().toString());
//        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                Uri.parse("http://maps.google.com/maps?saddr="+sourcell.latitude+","+sourcell.longitude+"&daddr="+destll.latitude+","+destll.longitude+"&mode=driving"));
////        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
////                Uri.parse("google.navigation:q=an+address+city"));
//        startActivity(intent);
        Intent intent = new Intent(this,RouteActivity.class);
        startActivity(intent);
this.finish();
//        ReviewReservationFragment fragment = new ReviewReservationFragment();
//        getSupportFragmentManager().beginTransaction().add(R.id.FrameId, fragment).commitAllowingStateLoss();


    }

    /**
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(mContext, "Payment failed:" + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(latitude, longitude);
        mMap.setOnCameraIdleListener(onCameraIdleListener);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

          /*  mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.gps_selected))

                    .draggable(true));*/

        //For zooming automatically to the location of the marker
        CameraPosition cameraPosition ;
        cameraPosition = new CameraPosition.Builder().target(sydney).zoom(18).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }
            @Override
            public void onMarkerDrag(Marker marker) {
            }
            @Override
            public void onMarkerDragEnd(Marker marker) {
                LatLng  l = marker.getPosition();
                latitude = l.latitude;
                longitude =l.longitude;
            }
        });
    }
}
