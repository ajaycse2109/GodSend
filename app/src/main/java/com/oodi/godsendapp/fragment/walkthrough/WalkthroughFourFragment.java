package com.oodi.godsendapp.fragment.walkthrough;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.oodi.godsend.R;
import com.oodi.godsendapp.activity.MainActivity;
import com.oodi.godsendapp.activity.WalkthroughActivity;
import com.oodi.godsendapp.util.Session;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalkthroughFourFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    public  int EdtNumber=1;
    public  int EdtNumberr=1;
    Activity mContext ;
    View view ;
    Session session;
    @BindView(R.id.spinner)
    Spinner mspinner;
    @BindView(R.id.lnrImg)
    LinearLayout mLnrImg;
    @BindView(R.id.sv)
    ScrollView mSv ;
    @BindView(R.id.hgt)
    EditText mhgt;
    @BindView(R.id.wgt)
    EditText mght;
    @BindView(R.id.EdtHsp11)
    EditText mEdtHsp11;
    @BindView(R.id.EdtHsp22)
    EditText mEdtHsp22;
    @BindView(R.id.EdtHsp33)
    EditText mEdtHsp33;
    @BindView(R.id.EdtHsp44)
    EditText mEdtHsp44;
    @BindView(R.id.EdtHsp55)
    EditText mEdtHsp55;
    @BindView(R.id.txtAddmore2)
    ImageView mtxtAddmore2;
    @BindView(R.id.EdtHp1)
    EditText mEdtHp1;
    @BindView(R.id.EdtHp2)
    EditText mEdtHp2;
    @BindView(R.id.EdtHp3)
    EditText mEdtHp3;
    @BindView(R.id.EdtHp4)
    EditText mEdtHp4;
    @BindView(R.id.EdtHp5)
    EditText mEdtHp5;
    @BindView(R.id.txtAddmore)
    ImageView mtxtAddmore;
    @BindView(R.id.btnFinish)
    Button mBtnFinish ;
    public WalkthroughFourFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_walkthrough_four, container, false);
        mContext = getActivity() ;
        ButterKnife.bind(this , view);
        session = new Session(mContext);

        mLnrImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WalkthroughActivity.mImgBack.setVisibility(View.VISIBLE);

                mLnrImg.setVisibility(View.GONE);
                mSv.setVisibility(View.VISIBLE);
            }
        });

        mBtnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.setLogin(true);
                Intent intent = new Intent(mContext , MainActivity.class);
                startActivity(intent);
            }
        });
        mspinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("A+");
        categories.add("O+");
        categories.add("B+");
        categories.add("AB+");
        categories.add("A-");
        categories.add("O-");
        categories.add("B-");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        mspinner.setAdapter(dataAdapter);
     /*   mght.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mght.setHint("");
                return false;
            }

        });
        mhgt.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mhgt.setHint("");
                return false;
            }

        });
        mEdtHsp11.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHsp11.setHint("");
                return false;
            }

        });
        mEdtHsp22.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHsp22.setHint("");
                return false;
            }

        });
        mEdtHp1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHp1.setHint("");
                return false;
            }

        });
        mEdtHp2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHp2.setHint("");
                return false;
            }

        });
        mEdtHp3.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHp3.setHint("");
                return false;
            }

        });*/
        mtxtAddmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(EdtNumber == 1)
                {
                    mEdtHsp22.setVisibility(View.VISIBLE);
                    EdtNumber ++;
                }
                else if(EdtNumber == 2)
                {
                    mEdtHsp33.setVisibility(View.VISIBLE);
                    EdtNumber ++;
                }
                else if(EdtNumber == 3)
                {
                    mEdtHsp44.setVisibility(View.VISIBLE);
                    EdtNumber ++;
                }
                else if(EdtNumber == 4)
                {
                    mEdtHsp55.setVisibility(View.VISIBLE);
                    EdtNumber ++;
                }
                else
                {
                    Toast.makeText(mContext, "Sorry Limit Exceeds", Toast.LENGTH_SHORT).show();
                }

            }


        });
        mtxtAddmore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(EdtNumberr == 1)
                {
                    mEdtHp2.setVisibility(View.VISIBLE);
                    EdtNumberr ++;
                }
                else if(EdtNumberr == 2)
                {
                    mEdtHp3.setVisibility(View.VISIBLE);
                    EdtNumberr ++;
                }
                else if(EdtNumberr == 3)
                {
                    mEdtHp4.setVisibility(View.VISIBLE);
                    EdtNumberr ++;
                }
                else if(EdtNumberr == 4)
                {
                    mEdtHp5.setVisibility(View.VISIBLE);
                    EdtNumberr ++;
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


        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
       // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

}
