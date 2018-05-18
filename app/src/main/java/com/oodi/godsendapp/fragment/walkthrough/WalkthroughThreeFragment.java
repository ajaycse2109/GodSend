package com.oodi.godsendapp.fragment.walkthrough;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.oodi.godsend.R;
import com.oodi.godsendapp.activity.MainActivity;
import com.oodi.godsendapp.activity.WalkthroughActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalkthroughThreeFragment extends Fragment {

    Activity mContext ;
    View view ;

    @BindView(R.id.lnrImg)
    LinearLayout mLnrImg;
    @BindView(R.id.sv)
    ScrollView mSv ;
    @BindView(R.id.btnNext)
    Button mBtnNext;

    public WalkthroughThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_walkthrough_three, container, false);
        mContext = getActivity() ;
        ButterKnife.bind(this , view);


        mLnrImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WalkthroughActivity.mImgBack.setVisibility(View.VISIBLE);
                WalkthroughActivity.mTxtSkip.setVisibility(View.VISIBLE);
                mLnrImg.setVisibility(View.GONE);
                mSv.setVisibility(View.VISIBLE);
            }
        });

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WalkthroughActivity.mImgBack.setVisibility(View.VISIBLE);
                WalkthroughActivity.mTxtSkip.setVisibility(View.VISIBLE);

                WalkthroughActivity.mViewpager.setCurrentItem(3);
            }
        });


        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){

            WalkthroughActivity.mTxtSkip.setVisibility(View.INVISIBLE);

            WalkthroughActivity.mImgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WalkthroughActivity.mTxtSkip.setVisibility(View.VISIBLE);

                    WalkthroughActivity.mViewpager.setCurrentItem(1);

                }
            });

            WalkthroughActivity.mTxtSkip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext , MainActivity.class);
                    startActivity(intent);

                }
            });

        }
    }

}
