package com.oodi.godsendapp.adapter;

import android.app.Activity;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oodi.godsend.R;
import com.oodi.godsendapp.fragment.hospital.WayFindingFragment;

/**
 * Created by pc on 2/23/17.
 */

public class FloorAdapter extends RecyclerView.Adapter<FloorAdapter.MyViewHolder>{

    Activity mContext ;
    String[] floor;
    WayFindingFragment wayFindingFragment;

    public FloorAdapter(Activity mContext, String[] floor, WayFindingFragment wayFindingFragment) {
        this.mContext = mContext ;
        this.floor = floor ;
        this.wayFindingFragment = wayFindingFragment;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtFloor;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtFloor = itemView.findViewById(R.id.txtFloor);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_floor, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.txtFloor.setText(floor[position]);

        if (position+1 == floor.length){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.txtFloor.setBackgroundColor(mContext.getResources().getColor(R.color.bg));
                holder.txtFloor.setTextColor(mContext.getResources().getColor(R.color.white));
            }
        }

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

    }

    @Override
    public int getItemCount() {
        return floor.length;
    }



}
