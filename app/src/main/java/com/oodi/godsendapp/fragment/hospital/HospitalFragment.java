package com.oodi.godsendapp.fragment.hospital;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.oodi.godsend.R;
import com.oodi.godsendapp.GlobalClass;
import com.oodi.godsendapp.activity.MainActivity;
import com.oodi.godsendapp.adapter.ProvidersAdapter;
import com.oodi.godsendapp.fragment.RootFragment;
import com.oodi.godsendapp.fragment.hospital.cunsultation.CScanAndTestFragment;
import com.oodi.godsendapp.pojo.Providers;
import com.oodi.godsendapp.util.AppUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HospitalFragment extends RootFragment implements OnMapReadyCallback, LocationListener {

    Activity mContext;
    View view ;
    AppUtils appUtils;


    @BindView(R.id.autoCompleteTextView)
    AutoCompleteTextView mautoCompleteTextView;
    @BindView(R.id.mapView)
    MapView mMapView;
//    @BindView(R.id.cardNearestHospital)
//    CardView mCardNearestHospital ;
//    @BindView(R.id.cardPreferredHospital)
//    CardView mCardPreferredHospital;
//    @BindView(R.id.imgNearest)
//    ImageView mImgNearest;
//   @BindView(R.id.txtNearestName)
//    TextView mTxtNearestName ;
//    @BindView(R.id.imgPH)
//    ImageView mImgPH;
//    @BindView(R.id.txtPH)
//    TextView mTxtPH;



    public List<Providers> providersList = new ArrayList<>();

    public  List<String> mStrings = new ArrayList<String>();

    @BindView(R.id.my_recycler_view)
    public RecyclerView recyclerView;
    public ProvidersAdapter mAdapter;

    @BindView(R.id.lnrLayout)
    LinearLayout mlnrLayout;
    private GoogleMap googleMap;
    boolean mLocationPermissionGranted = false;
    final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    public HospitalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_hospital, container, false);
        mContext = getActivity();

        prepareProvidersData();
        ButterKnife.bind(this, view);
        appUtils = new AppUtils(mContext);
        getLocationPermission();
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately




        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);

        mautoCompleteTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(mautoCompleteTextView.getText().toString() == "" || mautoCompleteTextView.getText() == null)
                {
                    mautoCompleteTextView.setHint("");
                    return false;
                }
                else
                {
                    mautoCompleteTextView.setHint("Search for hospitals");
                    return false;
                }
            }
        });
//        mCardNearestHospital.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentTransaction transaction = getFragmentManager()
//                        .beginTransaction();
//                transaction.replace(R.id.root_hospital, new CScanAndTestFragment());
//                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                transaction.addToBackStack(null);
//                transaction.commit();
//            }
//        });

        mAdapter = new ProvidersAdapter((FragmentActivity)mContext,providersList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    public void prepareProvidersData() {


//        appUtils.showProgressBarLoading();

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

                        mAdapter = new ProvidersAdapter(getActivity() , providersList);
                        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(mAdapter);
                        recyclerView.setNestedScrollingEnabled(false);

                        String[] mStringArray = new String[mStrings.size()];
                        mStringArray = mStrings.toArray(mStringArray);
                        ArrayAdapter<String> Hospitaladapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mStringArray);
                        mautoCompleteTextView.setThreshold(1);
                        mautoCompleteTextView.setAdapter(Hospitaladapter);
                        mautoCompleteTextView.setTextColor(Color.BLACK);
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
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        displayLocationSettingsRequest();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(final GoogleMap mMap) {
        googleMap = mMap;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        mMap.setMyLocationEnabled(true);
        // For dropping a marker at a point on the Map
        LatLng sydney = new LatLng(12.9716, 77.5946);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

        //For zooming automatically to the location of the marker
        CameraPosition cameraPosition ;

        cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange (Location location) {


                    LatLng loc = new LatLng (location.getLatitude(), location.getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));

            }
        };
        mMap.setOnMyLocationChangeListener(myLocationChangeListener);

        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                Log.e("TAG", googleMap.getCameraPosition().target.toString());
                //lat = googleMap.getCameraPosition().target.latitude ;
                //lng = googleMap.getCameraPosition().target.longitude;
            }
        });

        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

            @Override
            public void onCameraChange(final CameraPosition arg0) {
                googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                    @Override
                    public void onMapLoaded() {
                        LatLng latLng= arg0.target;

                        //lat = latLng.latitude ;
                        //lng = latLng.longitude;

                        //Toast.makeText(this, latLng.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                displayLocationSettingsRequest(getActivity());
                return false;
            }
        });

        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                Log.e("TAG", googleMap.getCameraPosition().target.toString());

                //lat = googleMap.getCameraPosition().target.latitude ;
                //lng = googleMap.getCameraPosition().target.longitude;
            }
        });

        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

            @Override
            public void onCameraChange(final CameraPosition arg0) {
                googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                    @Override
                    public void onMapLoaded() {
                        LatLng latLng= arg0.target;

                        /*lat = latLng.latitude ;
                        lng = latLng.longitude;

                        try {
                            addresses = geocoder.getFromLocation(lat, lng, 1);
                            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                            String city = addresses.get(0).getLocality();
                            String state = addresses.get(0).getAdminArea();
                            String country = addresses.get(0).getCountryName();
                            pincode = addresses.get(0).getPostalCode();
                            String knownName = addresses.get(0).getFeatureName();

                            Log.e("TAG", address.toString() + city + state );

                            Log.e("TAG", latLng.toString());// Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        } catch (Exception e) {
                            e.printStackTrace();
                        }*/

                    }
                });
            }
        });
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

    private void displayLocationSettingsRequest(Context context) {
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setCancelable(false);
            dialog.setMessage("Enable GPS");
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                    //get gps
                }
            });
            dialog.show();
        }else {

        }
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

    private void providers() {
 }

    private void displayLocationSettingsRequest() {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i("", "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i("", "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(mContext, 1);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i("", "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i("", "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

}
