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
import com.oodi.godsendapp.adapter.BillingPendingAdapter;
import com.oodi.godsendapp.fragment.RootFragment;
import com.oodi.godsendapp.pojo.Pending;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillingFragment extends RootFragment {

    Activity mContext;
    View view ;

    List<Pending> pendingList = new ArrayList<>();
    BillingPendingAdapter billingPendingAdapter;

    List<Pending> completedList = new ArrayList<>();
    BillingPendingAdapter billingCompletedAdapter;

    @BindView(R.id.lnrBack)
    LinearLayout mLnrBack ;
    @BindView(R.id.txtHeaderName)
    TextView mTxtHeaderName;
    @BindView(R.id.recPending)
    RecyclerView mRecPending;
    @BindView(R.id.recCompleted)
    RecyclerView mRecCompleted;

    public BillingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_billing, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, view);
        mTxtHeaderName.setText("Billing");

        mLnrBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.onBackPressed();
            }
        });

        billingPendingAdapter = new BillingPendingAdapter(mContext , pendingList , this , "pending");

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mRecPending.setLayoutManager(mLayoutManager);
        mRecPending.setAdapter(billingPendingAdapter);

        billingCompletedAdapter = new BillingPendingAdapter(mContext , completedList , this , "completed");

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(mContext);
        mRecCompleted.setLayoutManager(mLayoutManager1);
        mRecCompleted.setAdapter(billingCompletedAdapter);

        return view;
    }

}
