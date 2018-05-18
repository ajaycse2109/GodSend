package com.oodi.godsendapp.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oodi.godsend.R;
import com.oodi.godsendapp.fragment.appointments.AppointmentsFragment;
import com.oodi.godsendapp.fragment.appointments.CReviewReservationFragment;
import com.oodi.godsendapp.pojo.Appointment;

import java.util.List;

/**
 * Created by pc on 2/23/17.
 */

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyViewHolder>{

    Activity mContext ;
    List<Appointment> appointmentList;
    AppointmentsFragment appointmentsFragment;
    String type ;

    public AppointmentAdapter(Activity mContext, List<Appointment> appointmentList, AppointmentsFragment appointmentsFragment, String type) {
        this.mContext = mContext ;
        this.appointmentList = appointmentList ;
        this.appointmentsFragment = appointmentsFragment;
        this.type = type;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtCancelAppointment , txtMsg , txtStatus , txtTime , txtBook;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtCancelAppointment = itemView.findViewById(R.id.txtCancelAppointment);
            txtMsg = itemView.findViewById(R.id.txtMsg);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtTime = itemView.findViewById(R.id.txtTime);
          //  txtBook = itemView.findViewById(R.id.txtBook);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_appointment, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        if (!type.equals("upcoming")){

            holder.txtCancelAppointment.setText("Contact Hospital");
            holder.txtStatus.setText("Completed at");
            holder.txtTime.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Cancel Appointment");
                builder.setCancelable(true);
                builder.setMessage(mContext.getResources().getString(R.string.canceldailogmsg));
                builder.setPositiveButton("OK", null);
                builder.setNegativeButton("Cancel", null);
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        //return floor.length;
        if (type.equals("upcoming")){
            return 1;
        }else {
            return 2;
        }
    }



}
