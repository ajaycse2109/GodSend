package com.oodi.godsendapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oodi.godsend.R;
import com.oodi.godsendapp.fragment.hospital.cunsultation.CScanAndTestDetailsFragment;
import com.oodi.godsendapp.fragment.records.RecordFragment;
import com.oodi.godsendapp.pojo.Records;
import java.util.List;



public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.MyViewHolder>{

    Activity mContext ;
    List<Records> mRecordsList;
    RecordFragment recordFragment;

    public RecordAdapter(Activity mContext, List<Records> mRecordsList, RecordFragment recordFragment) {
        this.mContext = mContext ;
        this.mRecordsList = mRecordsList ;
        this.recordFragment = recordFragment;
    }

    public RecordAdapter(Activity mContext, List<Records> mRecordsList) {
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
      //TextView txtViewTitle;
        public MyViewHolder(View itemView) {
            super(itemView);
           // txtViewTitle = itemView.findViewById(R.id.attachfilenames);
        }
    }

    @Override
    public RecordAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_records, parent, false);

        return new RecordAdapter.MyViewHolder(itemView);
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
      /*  Records itm = mRecordsList.get(position);
        String s = itm.getRecordfile();
        System.out.println(s);
        holder.txtViewTitle.setText("  " + s);*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
FragmentTransaction transaction = recordFragment.getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.root_hospital, new CScanAndTestDetailsFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
*/

            }
        });

    }

    @Override
    public int getItemCount() {
        //return floor.length;
        return 2;
    }

}
