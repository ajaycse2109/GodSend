package com.oodi.godsendapp.fragment.hospital.cunsultation;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oodi.godsend.R;
import com.oodi.godsendapp.adapter.CDepartmentAdapter;
import com.oodi.godsendapp.fragment.RootFragment;
import com.oodi.godsendapp.pojo.Department;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CScanAndTestDepartmentFragment extends RootFragment {

    Activity mContext;
    View view ;

    List<Department> departmentList = new ArrayList<>();
    CDepartmentAdapter cDepartmentAdapter;

    @BindView(R.id.lnrBack)
    LinearLayout mLnrBack ;
    @BindView(R.id.txtHeaderName)
    TextView mTxtHeaderName;
    @BindView(R.id.redDepartment)
    RecyclerView mRedDepartment;

    public CScanAndTestDepartmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cscan_and_test_department, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, view);
        mTxtHeaderName.setText("General Medicine");

        mLnrBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.onBackPressed();
            }
        });

        cDepartmentAdapter = new CDepartmentAdapter(mContext , departmentList , this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRedDepartment.setLayoutManager(layoutManager);
        mRedDepartment.setAdapter(cDepartmentAdapter);

        return view;
    }

}
