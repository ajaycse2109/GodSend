package com.oodi.godsendapp.fragment.appointments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oodi.godsend.R;
import com.oodi.godsendapp.fragment.RootFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentsReplaceFragment extends RootFragment {


    public AppointmentsReplaceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appointments_replace, container, false);

        FragmentTransaction transaction = getChildFragmentManager()
                .beginTransaction();
		/*
		 * When this container fragment is created, we fill it with our first
		 * "real" fragment
		 */
        transaction.replace(R.id.root_appointment, new AppointmentsFragment() , "");

        transaction.commit();

        return view;
    }

}
