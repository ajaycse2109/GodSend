package com.oodi.godsendapp.fragment.records;


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
public class RecordReplaceFragment extends RootFragment {
    public RecordReplaceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_record_replace, container, false);

        FragmentTransaction transaction = getChildFragmentManager()
                .beginTransaction();
		/*
		 * When this container fragment is created, we fill it with our first
		 * "real" fragment
		 */
        transaction.replace(R.id.root_records, new RecordFragment() , "");

        transaction.commit();

        return view;
    }

}
