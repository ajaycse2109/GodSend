package com.oodi.godsendapp.fragment.hospital;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oodi.godsend.R;
import com.oodi.godsendapp.adapter.SaTAdapter;
import com.oodi.godsendapp.fragment.RootFragment;
import com.oodi.godsendapp.pojo.SaT;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScanAndTestFragment extends RootFragment {

    Activity mContext;
    View view ;

    List<SaT> saTList = new ArrayList<>();
    SaTAdapter saTAdapter;

    @BindView(R.id.lnrBack)
    LinearLayout mLnrBack ;
    @BindView(R.id.txtHeaderName)
    TextView mTxtHeaderName;
    @BindView(R.id.recSaT)
    RecyclerView mRecSaT;
    @BindView(R.id.edtSearch)
    EditText mEdtSearch ;
    public ScanAndTestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_scan_and_test, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, view);
        mTxtHeaderName.setText("Scans and Tests");

        mLnrBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.onBackPressed();
            }
        });
        saTAdapter = new SaTAdapter(mContext , saTList , this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mRecSaT.setLayoutManager(mLayoutManager);
        mRecSaT.setAdapter(saTAdapter);
        mEdtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener(){

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if(arg1 == EditorInfo.IME_ACTION_DONE)
                {


                    if (saTList.isEmpty()){

                        Toast.makeText(mContext, "Please try again",Toast.LENGTH_LONG).show();

                    }else {

                     //   saTAdapter = new SaTAdapter(mContext);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                        mRecSaT.setLayoutManager(mLayoutManager);
                        mRecSaT.setAdapter(saTAdapter);

                    }



                }
                return false;
            }

        });
        return view;
    }

}
