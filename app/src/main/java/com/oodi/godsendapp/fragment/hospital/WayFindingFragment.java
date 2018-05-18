package com.oodi.godsendapp.fragment.hospital;


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
import com.oodi.godsendapp.adapter.FloorAdapter;
import com.oodi.godsendapp.fragment.RootFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class WayFindingFragment extends RootFragment {

    Activity mContext;
    View view ;

    String[] floor = {"3","2","1","G"};
    FloorAdapter floorAdapter;

    @BindView(R.id.lnrBack)
    LinearLayout mLnrBack ;
    @BindView(R.id.txtHeaderName)
    TextView mTxtHeaderName;
    @BindView(R.id.recFloor)
    RecyclerView mRecFloor;

    public WayFindingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_way_finding, container, false);

        mContext = getActivity();
        ButterKnife.bind(this, view);
        mTxtHeaderName.setText("Hospital Wayfinding");

        mLnrBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.onBackPressed();
            }
        });

        floorAdapter = new FloorAdapter(mContext , floor , this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecFloor.setLayoutManager(layoutManager);
        mRecFloor.setAdapter(floorAdapter);

        return view;
    }

}
