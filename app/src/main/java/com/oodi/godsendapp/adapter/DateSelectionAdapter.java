package com.oodi.godsendapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oodi.godsend.R;
import com.oodi.godsendapp.fragment.hospital.cunsultation.CScanAndTestDetailsFragment;

/**
 * Created by pc on 2/23/17.
 */

public class DateSelectionAdapter extends RecyclerView.Adapter<DateSelectionAdapter.MyViewHolder>{

    Activity mContext ;
    String[] mTimeSlotList;
    CScanAndTestDetailsFragment cScanAndTestDetailsFragment;

    public DateSelectionAdapter(Activity mContext, String[] mTimeSlotList, CScanAndTestDetailsFragment cScanAndTestDetailsFragment) {
        this.mContext = mContext ;
        this.mTimeSlotList = mTimeSlotList ;
        this.cScanAndTestDetailsFragment = cScanAndTestDetailsFragment;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtTime;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtTime = itemView.findViewById(R.id.txtTime);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_dateselection, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = wayFindingFragment.getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.root_hospital, new CScanAndTestDetailsFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("custom-message");
                intent.putExtra("item_name" , mTimeSlotList[position]);
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
            }
        });

        holder.txtTime.setText(mTimeSlotList[position]);

    }

    @Override
    public int getItemCount() {
        return mTimeSlotList.length;
    }



}
