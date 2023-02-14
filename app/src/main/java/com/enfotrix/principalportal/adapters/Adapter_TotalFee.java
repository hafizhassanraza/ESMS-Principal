package com.enfotrix.principalportal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.principalportal.R;
import com.enfotrix.principalportal.models.Fee;

import java.util.List;

public class Adapter_TotalFee extends RecyclerView.Adapter<Adapter_TotalFee.ImageViewHolder> {
    Context context;
    List<Fee> list;

    public Adapter_TotalFee(Context context, List<Fee> list) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.feename, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        final Fee presentStudent = list.get(position);

        holder.tv_name.setText(presentStudent.getStudentName());
        holder.tv_reg.setText(presentStudent.getRegNumber());
        holder.amount.setText(presentStudent.getReceivedAmount());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_reg, amount;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_reg = itemView.findViewById(R.id.tv_reg);
            amount = itemView.findViewById(R.id.amount);
        }
    }
}
