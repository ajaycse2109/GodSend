package com.oodi.godsendapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.oodi.godsend.R;
import com.oodi.godsendapp.activity.MainActivity;
import com.oodi.godsendapp.activity.PaymentActivity;
import com.oodi.godsendapp.fragment.hospital.cunsultation.CScanAndTestDepartmentFragment;
import com.oodi.godsendapp.fragment.hospital.cunsultation.CScanAndTestFragment;
import com.oodi.godsendapp.pojo.Department;
import com.oodi.godsendapp.pojo.SaT;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pc on 2/23/17.
 */

public class CSaTAdapter extends RecyclerView.Adapter<CSaTAdapter.MyViewHolder>{

    Activity mContext ;
    List<SaT> avatarList ;
    CScanAndTestFragment cscanAndTestFragment;
    public CSaTAdapter(FragmentActivity mContext, List<SaT> avatarList) {
        this.mContext = mContext ;
        this.avatarList = avatarList ;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtViewTeam,txtdepname;
         ImageView txtimgdept;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtViewTeam = itemView.findViewById(R.id.txtViewTeam);
            txtdepname = itemView.findViewById(R.id.depname);
txtimgdept=itemView.findViewById(R.id.imgDepart);


            //  txtimgdept = itemView.findViewById(R.id.imgdep);
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
        final SaT saT = avatarList.get(position);
        holder.txtdepname.setText(saT.getDepartment());
       Picasso.with(mContext).load(saT.getImage()).fit().centerCrop().into(holder.txtimgdept);
        holder.txtViewTeam.setText("VIEW SERVICES");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = view.getContext().getSharedPreferences("MY" , Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("depart_name", saT.getDepartment());

               // Log.d("PARAM::ADAP", saT.getDepartment());

                editor.commit();
                MainActivity mainActivity = (MainActivity)mContext;
                FragmentTransaction transaction = mainActivity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.root_hospital, new CScanAndTestDepartmentFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
    @Override
    public int getItemCount() {
        return avatarList.size();
    }
}
