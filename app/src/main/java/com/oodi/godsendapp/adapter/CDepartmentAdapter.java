package com.oodi.godsendapp.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oodi.godsend.R;
import com.oodi.godsendapp.fragment.hospital.cunsultation.CScanAndTestDepartmentFragment;
import com.oodi.godsendapp.fragment.hospital.cunsultation.CScanAndTestDetailsFragment;
import com.oodi.godsendapp.pojo.Department;

import java.util.List;

/**
 * Created by pc on 2/23/17.
 */

public class CDepartmentAdapter extends RecyclerView.Adapter<CDepartmentAdapter.MyViewHolder>{

    Activity mContext ;
    List<Department> departmentList ;
    int last_position = 0;
    CScanAndTestDepartmentFragment cScanAndTestDepartmentFragment;

    public CDepartmentAdapter(Activity mContext, List<Department> departmentList, CScanAndTestDepartmentFragment cScanAndTestDepartmentFragment) {
        this.mContext = mContext ;
        this.departmentList = departmentList ;
        this.cScanAndTestDepartmentFragment = cScanAndTestDepartmentFragment;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_department, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = cScanAndTestDepartmentFragment.getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.root_hospital, new CScanAndTestDetailsFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
       // return departmentList.size();
        return 3;

    }



}
