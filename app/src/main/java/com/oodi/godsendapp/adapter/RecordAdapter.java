package com.oodi.godsendapp.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oodi.godsend.R;
import com.oodi.godsendapp.fragment.records.RecordFragment;
import com.oodi.godsendapp.pojo.Records;

import java.util.List;

/**
 * Created by pc on 2/23/17.
 */
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

        public MyViewHolder(View itemView) {
            super(itemView);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_records, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = recordFragment.getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.root_hospital, new CScanAndTestDetailsFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });*/

    }

    @Override
    public int getItemCount() {
        //return floor.length;
        return 2;
    }

}
