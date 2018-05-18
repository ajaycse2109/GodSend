package com.oodi.godsendapp.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oodi.godsend.R;
import com.oodi.godsendapp.fragment.hospital.BillingFragment;
import com.oodi.godsendapp.pojo.Pending;

import java.util.List;

/**
 * Created by pc on 2/23/17.
 */

public class BillingPendingAdapter extends RecyclerView.Adapter<BillingPendingAdapter.MyViewHolder>{

    Activity mContext ;
    List<Pending> pendingList ;
    int last_position = 0;
    BillingFragment billingFragment;
    String status;

    public BillingPendingAdapter(Activity mContext, List<Pending> pendingList, BillingFragment billingFragment, String status) {
        this.mContext = mContext ;
        this.pendingList = pendingList ;
        this.billingFragment = billingFragment;
        this.status = status ;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtSkip , txtPay;
        LinearLayout lnrRoot;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtSkip = itemView.findViewById(R.id.txtSkip);
            txtPay = itemView.findViewById(R.id.txtPay);
            lnrRoot = itemView.findViewById(R.id.lnrRoot);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_pending, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        if (status.equals("pending")){
            if (position == 1){
                holder.txtSkip.setVisibility(View.GONE);
                holder.txtPay.setTextColor(Color.RED);
                holder.txtPay.setText("PROCESSING");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.txtPay.setBackground(mContext.getResources().getDrawable(R.drawable.grey_bg));
                }
            }
        }else {
            holder.txtSkip.setVisibility(View.GONE);
            holder.txtPay.setText("PRINT INVOICE");
            holder.txtPay.setTextColor(mContext.getResources().getColor(R.color.grey_text));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.txtPay.setBackground(mContext.getResources().getDrawable(R.drawable.grey_bg));
                holder.lnrRoot.setBackground(null);
            }

        }



/*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = billingFragment.getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.root_hospital, new ScanAndTestDetailsFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
*/

    }

    @Override
    public int getItemCount() {
       // return pendingList.size();
        return 2;

    }



}
