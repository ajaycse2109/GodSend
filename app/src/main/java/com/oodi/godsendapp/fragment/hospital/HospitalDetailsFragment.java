package com.oodi.godsendapp.fragment.hospital;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.oodi.godsend.R;
import com.oodi.godsendapp.fragment.RootFragment;
import com.oodi.godsendapp.fragment.hospital.cunsultation.CScanAndTestFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HospitalDetailsFragment extends RootFragment {

    Activity mContext;
    View view ;

    @BindView(R.id.lnrBack)
    LinearLayout mLnrBack ;
    @BindView(R.id.imgConsultation)
    ImageView mImgConsultation;
    @BindView(R.id.imgScanAndTest)
    ImageView mImgScanAndTest;
    @BindView(R.id.imgBilling)
    ImageView mImgBilling;
    @BindView(R.id.imgWayfinding)
    ImageView mImgWayfinding;

    public HospitalDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_hospital_details, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, view);

        mLnrBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.onBackPressed();
            }
        });

        mImgConsultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.root_hospital, new CScanAndTestFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        mImgScanAndTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.root_hospital, new ScanAndTestFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        mImgBilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.root_hospital, new BillingFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        mImgWayfinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.root_hospital, new WayFindingFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

}
