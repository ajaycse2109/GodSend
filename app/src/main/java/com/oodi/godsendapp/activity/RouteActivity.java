package com.oodi.godsendapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.oodi.godsend.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RouteActivity extends FragmentActivity implements OnMapReadyCallback {

    Activity mContext;

    private GoogleMap mMap;

    @BindView(R.id.txtCancel)
    TextView mTxtCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        mContext = this;
        ButterKnife.bind(mContext);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mTxtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AmbulanceActivity.mContext.finish();
                finish();
            }
        });
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(12.9716, 77.5946);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
        //For zooming automatically to the location of the marker
        CameraPosition cameraPosition ;
        cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
