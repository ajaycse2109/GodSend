package com.oodi.godsendapp.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oodi.godsend.R;
import com.oodi.godsendapp.fragment.profile.ProfileFragment;
import com.oodi.godsendapp.pojo.Address;

import java.util.List;

/**
 * Created by pc on 2/23/17.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder>{

    Activity mContext ;
    List<Address> mAddressList;
    ProfileFragment profileFragment;

    public AddressAdapter(Activity mContext,  List<Address> mAddressList, ProfileFragment profileFragment) {
        this.mContext = mContext ;
        this.mAddressList = mAddressList ;
        this.profileFragment = profileFragment;
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
                .inflate(R.layout.view_address, parent, false);

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
        //return mAddressList.size();
        return 3;
    }



}
