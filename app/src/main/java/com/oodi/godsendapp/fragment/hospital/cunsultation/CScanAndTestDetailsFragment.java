package com.oodi.godsendapp.fragment.hospital.cunsultation;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oodi.godsend.R;
import com.oodi.godsendapp.adapter.DateSelectionAdapter;
import com.oodi.godsendapp.fragment.RootFragment;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CScanAndTestDetailsFragment extends RootFragment {

    Activity mContext;
    View view;
    private int mYear, mMonth, mDay;
    android.app.AlertDialog alertDialog ;

    String[] mTimeSlotList = {"09:00-09:30",
            "09:30-10:00",
            "10:00-10:30",
            "10:30-11:00",
            "11:00-11:30",
            "11:30-12:00",
            "12:00-12:30",
            "12:30-01:00",
            "01:00-01:30",
            "01:30-02:00",
            "02:00-02:30",
            "02:30-03:00",
            "03:00-03:30",
            "03:30-04:00",
            "04:00-04:30",
            "04:30-05:00",
            "05:00-05:30",
            "05:30-06:00"};
    DateSelectionAdapter mDateSelectionAdapter ;

    @BindView(R.id.lnrBack)
    LinearLayout mLnrBack;
    @BindView(R.id.txtHeaderName)
    TextView mTxtHeaderName;
    @BindView(R.id.btnConfirm)
    Button mBtnConfirm;
    @BindView(R.id.txtDepartment)
    TextView mTxtDepartment;
    @BindView(R.id.txtDate)
    TextView mTxtDate;
    @BindView(R.id.txtTime)
    TextView mTxtTime;

    public CScanAndTestDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_scan_and_test_details, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, view);
        mTxtHeaderName.setText("Book Appointment");

        mLnrBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.onBackPressed();
            }
        });

        mTxtDepartment.setVisibility(View.VISIBLE);

        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.root_hospital, new CSaTReviewFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        mTxtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                String day = "";
                                String month = "";

                                if (dayOfMonth == 1) {
                                    day = String.valueOf("01");
                                } else if (dayOfMonth == 2) {
                                    day = String.valueOf("02");
                                } else if (dayOfMonth == 3) {
                                    day = String.valueOf("03");
                                } else if (dayOfMonth == 4) {
                                    day = String.valueOf("04");
                                } else if (dayOfMonth == 5) {
                                    day = String.valueOf("05");
                                } else if (dayOfMonth == 6) {
                                    day = String.valueOf("06");
                                } else if (dayOfMonth == 7) {
                                    day = String.valueOf("07");
                                } else if (dayOfMonth == 8) {
                                    day = String.valueOf("08");
                                } else if (dayOfMonth == 9) {
                                    day = String.valueOf("09");
                                } else {
                                    day = String.valueOf(dayOfMonth);
                                }

                                if (monthOfYear == 0) {
                                    month = String.valueOf("01");
                                } else if (monthOfYear == 1) {
                                    month = String.valueOf("02");
                                } else if (monthOfYear == 2) {
                                    month = String.valueOf("03");
                                } else if (monthOfYear == 3) {
                                    month = String.valueOf("04");
                                } else if (monthOfYear == 4) {
                                    month = String.valueOf("05");
                                } else if (monthOfYear == 5) {
                                    month = String.valueOf("06");
                                } else if (monthOfYear == 6) {
                                    month = String.valueOf("07");
                                } else if (monthOfYear == 7) {
                                    month = String.valueOf("08");
                                } else if (monthOfYear == 8) {
                                    month = String.valueOf("09");
                                } else {
                                    month = String.valueOf(monthOfYear + 1);
                                }

                                //DOB = year + "-" + month + "-" + day;
                                mTxtDate.setText(day + "-" + month + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        mTxtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeList();
            }
        });

        LocalBroadcastManager.getInstance(mContext).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

        return view;
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!intent.hasExtra("item_name")){
            }
            else {
                String item_name = intent.getStringExtra("item_name");

                mTxtTime.setText(item_name);

                try {
                    alertDialog.dismiss();
                }catch (Exception e){
                }
            }
        }
    };

    public void timeList(){

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View promptsView = layoutInflater.inflate(R.layout.date_selection_popup, null);

        final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(mContext);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setCancelable(true);
        alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(true);

        final TextView Update = (TextView) promptsView.findViewById(R.id.btn_confirm);

        final RecyclerView recyclerView = (RecyclerView) promptsView.findViewById(R.id.list);

        mDateSelectionAdapter = new DateSelectionAdapter(getActivity() , mTimeSlotList , this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mDateSelectionAdapter);

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


}
