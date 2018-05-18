package com.oodi.godsendapp.fragment.profile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oodi.godsend.R;
import com.oodi.godsendapp.fragment.RootFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class InsuranceAndAadhaarFragment extends RootFragment {


    public InsuranceAndAadhaarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insurance_and_aadhaar, container, false);
    }

}
