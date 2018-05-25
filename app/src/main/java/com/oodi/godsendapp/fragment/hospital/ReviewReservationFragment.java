package com.oodi.godsendapp.fragment.hospital;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import  android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oodi.godsend.R;
import com.oodi.godsendapp.activity.MainActivity;
import com.oodi.godsendapp.activity.PaymentActivity;
import com.oodi.godsendapp.fragment.RootFragment;
import com.oodi.godsendapp.fragment.hospital.cunsultation.CSaTReviewFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewReservationFragment extends RootFragment {

    Activity mContext;
    View view ;
//    @BindView(R.id.lnrBack)
//    LinearLayout mLnrBack ;
//    @BindView(R.id.txtHeaderName)
//    TextView mTxtHeaderName;
    @BindView(R.id.appmntFor)
    TextView mappmntFor;
    @BindView(R.id.txtpatName)
    TextView mtxtpatName;
    @BindView(R.id.buttonOk)
    Button mbtnOk;
    public ReviewReservationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_review_reservation, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, view);
      //  mTxtHeaderName.setText("Completed");
        SharedPreferences pref = getActivity().getSharedPreferences("MY" , Context.MODE_PRIVATE);
        String    sername = pref.getString("service_name", "");
        String name= pref.getString("prof_name","");
        mtxtpatName.setText(name);
        mappmntFor.setText(sername);
//        mLnrBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mContext.onBackPressed();
//            }
//        });
mbtnOk.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);

    }
});

        return view;
    }

}
