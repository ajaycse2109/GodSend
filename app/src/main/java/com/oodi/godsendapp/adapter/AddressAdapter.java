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
import com.oodi.godsendapp.pojo.SaT;

import java.util.List;

/**
 * Created by pc on 2/23/17.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder>{

    Activity mContext ;
    List<Address> mAddressList;
    ProfileFragment profileFragment;

    public AddressAdapter(Activity mContext,  List<Address> mAddressList) {
        this.mContext = mContext ;
        this.mAddressList = mAddressList ;
        this.profileFragment = profileFragment;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mmobtxt,maddtxt;

        public MyViewHolder(View itemView) {
            super(itemView);

            mmobtxt = itemView.findViewById(R.id.mobtxt);
            maddtxt = itemView.findViewById(R.id.addtxt);

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

        final Address address = mAddressList.get(position);
        holder.mmobtxt.setText(address.getPhone());
        holder.maddtxt.setText(address.getAddress());
    }

    @Override
    public int getItemCount() {
        return mAddressList.size();
        //return 3;
    }



}
