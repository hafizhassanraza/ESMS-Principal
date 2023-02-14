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
import com.enfotrix.principalportal.models.Model_AmountDetail;

import java.util.List;

public class Adapter_Fee extends RecyclerView.Adapter<Adapter_Fee.ImageViewHolder> {

    Context context;
    List<Model_AmountDetail> list;

    public Adapter_Fee(Context context, List<Model_AmountDetail> list) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_fee_details, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        final Model_AmountDetail presentStudent = list.get(position);

        holder.txt_amount.setText(presentStudent.getAmount());
        holder.txt_date.setText(presentStudent.getDate());
        holder.txt_detail_feeRecevied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView txt_date, txt_amount,txt_detail_feeRecevied;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_date = itemView.findViewById(R.id.txt_transection_date);
            txt_amount = itemView.findViewById(R.id.txt_amount);
            txt_detail_feeRecevied = itemView.findViewById(R.id.txt_detail_feeRecevied);
        }
    }
}