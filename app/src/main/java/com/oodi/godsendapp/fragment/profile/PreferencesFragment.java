package com.oodi.godsendapp.fragment.profile;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oodi.godsend.R;
import com.oodi.godsendapp.fragment.RootFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * A simple {@link Fragment} subclass.
 */
public class PreferencesFragment extends RootFragment {
    Activity mcontext;
    View view;
    @BindView(R.id.lnrBack)
    LinearLayout mLnrBack ;
    @BindView(R.id.txtHeaderName)
    TextView mTxtHeaderName;
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
    public  int EdtNumber=1;
    Activity activity = getActivity();
    public PreferencesFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mcontext = getActivity();
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_preferences, container, false);
        ButterKnife.bind(this , view);
        mTxtHeaderName.setText("Preferences");

        mLnrBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcontext.onBackPressed();
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
        mEdtHsp5.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtHsp5.setHint("");
                return false;
            }

        });
        mtxtAddmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EdtNumber == 1) {
                    mEdtHsp2.setVisibility(View.VISIBLE);
                    EdtNumber++;
                } else if (EdtNumber == 2) {
                    mEdtHsp3.setVisibility(View.VISIBLE);
                    EdtNumber++;
                } else if (EdtNumber == 3) {
                    mEdtHsp4.setVisibility(View.VISIBLE);
                    EdtNumber++;
                } else if (EdtNumber == 4) {
                    mEdtHsp5.setVisibility(View.VISIBLE);
                    EdtNumber++;
                } else {
                    Toast.makeText(mcontext, "Sorry Limit Exceeds", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}
