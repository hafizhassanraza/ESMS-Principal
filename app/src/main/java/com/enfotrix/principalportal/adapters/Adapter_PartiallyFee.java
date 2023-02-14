package com.enfotrix.principalportal.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.principalportal.R;
import com.enfotrix.principalportal.models.Fee;

import java.util.List;

public class Adapter_PartiallyFee extends RecyclerView.Adapter<Adapter_PartiallyFee.ImageViewHolder> {
    Context context;
    List<Fee> list;

    public Adapter_PartiallyFee(Context context, List<Fee> list) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.partiallyfee, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        final Fee presentStudent = list.get(position);

        holder.tv_name.setText(presentStudent.getStudentName());
        holder.tv_reg.setText(presentStudent.getRegNumber());
        holder.amount.setText(presentStudent.getReceivedAmount());
        holder.txt_pending.setText(presentStudent.getPayableAmount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String contact = "+92 340 9009191"; // use country code with your phone number
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + presentStudent.getPhoneNumber()));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_reg, amount, txt_pending;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_reg = itemView.findViewById(R.id.tv_reg);
            amount = itemView.findViewById(R.id.amount);
            txt_pending = itemView.findViewById(R.id.txt_pending);
        }
    }
}
