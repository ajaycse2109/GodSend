package com.oodi.godsendapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oodi.godsend.R;
import com.oodi.godsendapp.activity.MainActivity;
import com.oodi.godsendapp.fragment.hospital.cunsultation.CScanAndTestDepartmentFragment;
import com.oodi.godsendapp.fragment.hospital.cunsultation.CScanAndTestDetailsFragment;
import com.oodi.godsendapp.pojo.Department;
import com.oodi.godsendapp.pojo.SaT;
import com.oodi.godsendapp.util.AppUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by pc on 2/23/17.
 */

public class CDepartmentAdapter extends RecyclerView.Adapter<CDepartmentAdapter.MyViewHolder>{

    Activity mContext ;
    List<Department> departmentList ;
    int last_position = 0;
    CScanAndTestDepartmentFragment cScanAndTestDepartmentFragment;

    public CDepartmentAdapter(Activity mContext, List<Department> departmentList) {
        this.mContext = mContext ;
        this.departmentList = departmentList ;
        this.cScanAndTestDepartmentFragment = cScanAndTestDepartmentFragment;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtname,txtPrice;
        ImageView imgServ;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtname = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtprice);
imgServ=itemView.findViewById(R.id.imgService);
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
        final Department department = departmentList.get(position);
        holder.txtname.setText(department.getName());
        holder.txtPrice.setText(department.getPrice());
        Picasso.with(mContext).load(department.getImage()).fit().centerCrop().into(holder.imgServ);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedpreferences = mContext.getSharedPreferences("MY" , Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("services_id", department.getId());
                editor.putString("service_name",department.getName());
                editor.putString("service_type",department.getService_type());
                editor.putString("service_img",department.getImage());
                editor.putString("service_price",department.getPrice());
                editor.commit();
                MainActivity mainActivity = (MainActivity)mContext;
                FragmentTransaction transaction = mainActivity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.root_hospital, new CScanAndTestDetailsFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
    @Override
    public int getItemCount() {
        return departmentList.size();
    }
}
