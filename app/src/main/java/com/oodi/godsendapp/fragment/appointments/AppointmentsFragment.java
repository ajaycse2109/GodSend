package com.oodi.godsendapp.fragment.appointments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.oodi.godsend.R;
import com.oodi.godsendapp.activity.AmbulanceActivity;
import com.oodi.godsendapp.adapter.AppointmentAdapter;
import com.oodi.godsendapp.fragment.RootFragment;
import com.oodi.godsendapp.pojo.Appointment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentsFragment extends RootFragment {

    Activity mContext;
    View view ;

    List<Appointment> mUpcomingList = new ArrayList<>();
    AppointmentAdapter mUpcomingAppointmentAdapter ;

    List<Appointment> mCompletedList = new ArrayList<>();
    AppointmentAdapter mCompletedAppointmentAdapter ;

    @BindView(R.id.btnNewAppointment)
    Button mBtnNewAppointment;
    @BindView(R.id.recUpcoming)
    RecyclerView mRecUpcoming;
    @BindView(R.id.recCompleted)
    RecyclerView mRecCompleted;

    public AppointmentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_appointments, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, view);

        mUpcomingAppointmentAdapter = new AppointmentAdapter(mContext , mUpcomingList , this , "upcoming");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecUpcoming.setLayoutManager(layoutManager);
        mRecUpcoming.setAdapter(mUpcomingAppointmentAdapter);

        mCompletedAppointmentAdapter = new AppointmentAdapter(mContext , mCompletedList , this , "completed");

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(mContext);
        mRecCompleted.setLayoutManager(layoutManager1);
        mRecCompleted.setAdapter(mCompletedAppointmentAdapter);

        mBtnNewAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext , AmbulanceActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
