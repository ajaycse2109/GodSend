package com.oodi.godsendapp.adapter;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oodi.godsend.R;
import com.oodi.godsendapp.activity.MainActivity;
import com.oodi.godsendapp.fragment.hospital.cunsultation.CScanAndTestDetailsFragment;
import com.oodi.godsendapp.fragment.records.AddHealthRecordFragment;
import com.oodi.godsendapp.fragment.records.RecordFragment;
import com.oodi.godsendapp.pojo.Records;
import com.oodi.godsendapp.pojo.SaT;

import java.util.List;
public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.MyViewHolder>{

    Activity mContext ;
    List<Records> mRecordsList;
    RecordFragment recordFragment;
    public RecordAdapter(Activity mContext, List<Records> mRecordsList) {
        this.mContext = mContext ;
        this.mRecordsList = mRecordsList ;
        this.recordFragment = recordFragment;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtViewTitle;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtViewTitle = itemView.findViewById(R.id.attachfilenames);
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
        final Records records = mRecordsList.get(position);
        holder.txtViewTitle.setText(records.getFname());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = view.getContext().getSharedPreferences("MY" , Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                Log.d("PARA-DESV", ""+records.getDescription());
                editor.putString("file_name", records.getFname());
                editor.putString("des", records.getDescription());
                editor.putString("record_type", records.getRecordtype());
                Log.d("PARAM::ADAP",  records.getFname());
                editor.commit();

                MainActivity mainActivity = (MainActivity)mContext;
                FragmentTransaction transaction = mainActivity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.root_records, new AddHealthRecordFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
    @Override
    public int getItemCount() {
        return mRecordsList.size();
    }

}
