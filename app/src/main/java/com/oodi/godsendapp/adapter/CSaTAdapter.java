package com.oodi.godsendapp.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oodi.godsend.R;
import com.oodi.godsendapp.fragment.hospital.cunsultation.CScanAndTestDepartmentFragment;
import com.oodi.godsendapp.fragment.hospital.cunsultation.CScanAndTestFragment;
import com.oodi.godsendapp.pojo.SaT;

import java.util.List;

/**
 * Created by pc on 2/23/17.
 */

public class CSaTAdapter extends RecyclerView.Adapter<CSaTAdapter.MyViewHolder>{

    Activity mContext ;
    List<SaT> avatarList ;
    int last_position = 0;
    CScanAndTestFragment scanAndTestFragment;

    public CSaTAdapter(Activity mContext, List<SaT> avatarList, CScanAndTestFragment scanAndTestFragment) {
        this.mContext = mContext ;
        this.avatarList = avatarList ;
        this.scanAndTestFragment = scanAndTestFragment;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtViewTeam;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtViewTeam = itemView.findViewById(R.id.txtViewTeam);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_sat, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.txtViewTeam.setText("VIEW SERVICES");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = scanAndTestFragment.getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.root_hospital, new CScanAndTestDepartmentFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
       // return avatarList.size();
        return 3;

    }



}
