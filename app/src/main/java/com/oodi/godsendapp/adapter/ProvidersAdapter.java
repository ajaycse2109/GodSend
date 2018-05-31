package com.oodi.godsendapp.adapter;

import android.app.Activity;
import android.content.Context;
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

import com.oodi.godsend.R;
import com.oodi.godsendapp.activity.MainActivity;
import com.oodi.godsendapp.fragment.hospital.HospitalFragment;
import com.oodi.godsendapp.fragment.hospital.cunsultation.CScanAndTestDepartmentFragment;
import com.oodi.godsendapp.fragment.hospital.cunsultation.CScanAndTestFragment;
import com.oodi.godsendapp.pojo.Providers;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by pc on 2/23/17.
 */

public class ProvidersAdapter extends RecyclerView.Adapter<ProvidersAdapter.MyViewHolder>{

    Activity mContext ;
    List<Providers> avatarList ;
    CScanAndTestFragment scanAndTestFragment ;
    public ProvidersAdapter(FragmentActivity mContext, List<Providers> avatarList) {
        this.mContext = mContext ;
        this.avatarList = avatarList ;
      //  this.scanAndTestFragment = scanAndTestFragment;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtViewTeam,txtDistance,txtTimes;//txtdepname;
        ImageView txtimg;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtViewTeam = itemView.findViewById(R.id.txtNearestName);
           // txtdepname = itemView.findViewById(R.id.depname);
              txtimg = itemView.findViewById(R.id.imgNearest);
              txtDistance = itemView.findViewById(R.id.txtDist);
              txtTimes = itemView.findViewById(R.id.txtTime);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_providerlist, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Providers saT = avatarList.get(position);
       // holder.txtViewTeam.setText("VIEW SERVICES");
      //  holder.txtdepname.setText(saT.getDepartment());
        holder.txtViewTeam.setText(saT.get_hospitalName());
        holder.txtDistance.setText(saT.getDistance()+"km");
holder.txtTimes.setText(saT.getTime());
Picasso.with(mContext).load(saT.getLogo()).fit().centerCrop().into(holder.txtimg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedpreferences = view.getContext().getSharedPreferences("MY" , Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("providerId", saT.getProviderid());
                editor.putString("provider_name", saT.get_hospitalName());
                editor.putString("provider_logo",saT.getLogo());
               // Log.d("PARAM::ADAP", saT.getDepartment());

                editor.commit();
                MainActivity ma = (MainActivity)mContext;
                FragmentTransaction transaction = ma.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.root_hospital, new CScanAndTestFragment());
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
