package com.oodi.godsendapp.fragment.hospital;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oodi.godsend.R;
import com.oodi.godsendapp.fragment.RootFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SaTReviewFragment extends RootFragment {

    Activity mContext;
    View view ;

    @BindView(R.id.lnrBack)
    LinearLayout mLnrBack ;
    @BindView(R.id.txtHeaderName)
    TextView mTxtHeaderName;
    @BindView(R.id.btnConfirm)
    Button mBtnConfirm;

    public SaTReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sa_treview, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, view);
        mTxtHeaderName.setText("Completed");

        mLnrBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.onBackPressed();
            }
        });

        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.root_hospital, new PaymentFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return view;
    }

}
