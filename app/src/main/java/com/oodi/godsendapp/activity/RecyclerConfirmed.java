/*
package com.oodi.godsendapp.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.therightdoctors.dpmpatient.R.id.button_pay;


public class RecyclerConfirmed extends RecyclerView.Adapter<RecyclerConfirmed.ViewHolder> {

    String pid, did;
    private Context mContext;
    private List<Item> itemList;

    public RecyclerConfirmed(Context mContext, List<Item> itemList) {
        this.mContext = mContext;
        this.itemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.confirmedlist, parent, false), mContext, itemList);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item itm = itemList.get(position);
        pid = itm.getPatient_id();
        did = itm.getDoc_id();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
        String payment=itm.getPayment();
      
        Date d = null;
        try {
            d = sdf.parse(itm.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedTime = output.format(d);
        String u = "Your consultation with " + itm.getDocname() + " on " + formattedTime + " at " + itm.getTime();
        holder.pconfirmconsult.setText(u);
        String s2 = "Type: " + itm.getType() + " Consult" + "   Fee:" + itm.getFee();
        holder.vdcons.setText(s2);
        if(payment.equals("done")){
            holder.pay.setVisibility(View.GONE);

        }
        

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView pconfirmconsult, vdcons;
        Button pay;
        Context mContext;
        List<Item> itemList;

        public ViewHolder(View itemView, final Context mContext, List<Item> itemList) {
            super(itemView);
            this.mContext = mContext;
            this.itemList = itemList;
            pconfirmconsult = (TextView) itemView.findViewById(R.id.pconfirmconsult);
            vdcons = (TextView) itemView.findViewById(R.id.vdcons);
            pay = (Button) itemView.findViewById(button_pay);
            pay.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Item item = this.itemList.get(position);
            pid = item.getPatient_id();
            did = item.getDoc_id();
            String bid = item.getBid();
            String fee = item.getFee();
            Context con = v.getContext();
            Intent intent = new Intent(con, PaymentActivity.class);
            intent.putExtra("pid", pid);
            intent.putExtra("did", did);
            intent.putExtra("bid", bid);
            intent.putExtra("fee", fee);
            intent.putExtra("docname", item.getDocname());
            String payment=item.getPayment();
            con.startActivity(intent);


        }
    }
}
*/
