package com.oodi.godsendapp.fragment.walkthrough;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.oodi.godsend.R;
import com.oodi.godsendapp.activity.MainActivity;
import com.oodi.godsendapp.activity.WalkthroughActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalkthroughTwoFragment extends Fragment {

    Activity mContext ;
    View view ;
public   int EdtNumber=1;
    @BindView(R.id.lnrImg)
    LinearLayout mLnrImg;
    @BindView(R.id.sv)
    ScrollView mSv ;
    @BindView(R.id.btnNext)
    Button mBtnNext;
 @BindView(R.id.txtAddmore)
    TextView mtxtAddmore;
@BindView(R.id.EdtHsp1)
    EditText mEdtHsp1;
    @BindView(R.id.EdtHsp2)
    EditText mEdtHsp2;
    @BindView(R.id.EdtHsp3)
    EditText mEdtHsp3;
    @BindView(R.id.EdtHsp4)
    EditText mEdtHsp4;
    @BindView(R.id.EdtHsp5)
    EditText mEdtHsp5;
    public WalkthroughTwoFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_walkthrough_two, container, false);
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
        mEdtHsp1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHsp1.setHint("");
                return false;
            }

        });
        mEdtHsp2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHsp2.setHint("");
                return false;
            }

        });
        mEdtHsp3.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHsp3.setHint("");
                return false;
            }

        });
        mEdtHsp4.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHsp4.setHint("");
                return false;
            }

        });
        mEdtHsp4.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHsp4.setHint("");
                return false;
            }

        });
 mtxtAddmore.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        if(EdtNumber == 1)
        {
            mEdtHsp2.setVisibility(View.VISIBLE);
            EdtNumber ++;
        }
        else
        if(EdtNumber == 2)
        {
            mEdtHsp3.setVisibility(View.VISIBLE);
            EdtNumber ++;
        }
        else if(EdtNumber == 3)
        {
            mEdtHsp4.setVisibility(View.VISIBLE);
            EdtNumber ++;
        }
        else if(EdtNumber == 4)
        {
            mEdtHsp5.setVisibility(View.VISIBLE);
            EdtNumber ++;
        }
        else
        {
            Toast.makeText(mContext, "Sorry Limit Exceeds", Toast.LENGTH_SHORT).show();
        }

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

                    WalkthroughActivity.mViewpager.setCurrentItem(0);

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
