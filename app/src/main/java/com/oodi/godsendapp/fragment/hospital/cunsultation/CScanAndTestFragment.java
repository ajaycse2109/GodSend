package com.oodi.godsendapp.fragment.hospital.cunsultation;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oodi.godsend.R;
import com.oodi.godsendapp.adapter.CSaTAdapter;
import com.oodi.godsendapp.fragment.RootFragment;
import com.oodi.godsendapp.pojo.SaT;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CScanAndTestFragment extends RootFragment {

    Activity mContext;
    View view ;

    List<SaT> saTList = new ArrayList<>();
    CSaTAdapter saTAdapter;

    @BindView(R.id.lnrBack)
    LinearLayout mLnrBack ;
    @BindView(R.id.txtHeaderName)
    TextView mTxtHeaderName;
    @BindView(R.id.recSaT)
    RecyclerView mRecSaT;
    @BindView(R.id.edtSearch)
    EditText mEdtSearch;

    public CScanAndTestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_scan_and_test, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, view);
        mTxtHeaderName.setText("Departments");
        mLnrBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.onBackPressed();
            }
        });
       // mEdtSearch.setText("Search for Departments");
        saTAdapter = new CSaTAdapter(mContext , saTList , this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mRecSaT.setLayoutManager(mLayoutManager);
        mRecSaT.setAdapter(saTAdapter);
        return view;
    }
}
