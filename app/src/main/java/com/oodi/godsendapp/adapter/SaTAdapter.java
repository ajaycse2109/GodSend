package com.oodi.godsendapp.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oodi.godsend.R;
import com.oodi.godsendapp.fragment.hospital.ScanAndTestDetailsFragment;
import com.oodi.godsendapp.fragment.hospital.ScanAndTestFragment;
import com.oodi.godsendapp.pojo.SaT;

import java.util.List;

/**
 * Created by pc on 2/23/17.
 */

public class SaTAdapter extends RecyclerView.Adapter<SaTAdapter.MyViewHolder>{

    Activity mContext ;
    List<SaT> avatarList ;
    int last_position = 0;
    ScanAndTestFragment scanAndTestFragment;

    public SaTAdapter(Activity mContext, List<SaT> avatarList, ScanAndTestFragment scanAndTestFragment) {
        this.mContext = mContext ;
        this.avatarList = avatarList ;
        this.scanAndTestFragment = scanAndTestFragment;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {



        public MyViewHolder(View itemView) {
            super(itemView);



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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = scanAndTestFragment.getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.root_hospital, new ScanAndTestDetailsFragment());
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
