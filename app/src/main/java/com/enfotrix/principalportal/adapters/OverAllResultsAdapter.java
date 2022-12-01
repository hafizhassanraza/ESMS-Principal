package com.enfotrix.principalportal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.principalportal.R;
import com.enfotrix.principalportal.databinding.OverallResultListItemBinding;
import com.enfotrix.principalportal.models.OverAllResult;
import com.enfotrix.principalportal.utilities.RecyclerViewClickInterface;

import java.util.ArrayList;

public class OverAllResultsAdapter extends RecyclerView.Adapter<OverAllResultsAdapter.ViewHolder> {
    private ArrayList<OverAllResult> overAllResults;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public OverAllResultsAdapter(ArrayList<OverAllResult> overAllResults, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.overAllResults = overAllResults;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public OverAllResultsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(OverallResultListItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull OverAllResultsAdapter.ViewHolder holder, int position) {

        holder.binding.tvClass.setText(overAllResults.get(position).getClassName());
        holder.binding.tvSection.setText(overAllResults.get(position).getSectionName());
        holder.binding.tvTotal.setText(overAllResults.get(position).getTotalStudents());
        holder.binding.tvPass.setText(overAllResults.get(position).getPassStudents());
        holder.binding.tvFail.setText(overAllResults.get(position).getFailStudents());
        String percentage;
        if (Integer.parseInt(overAllResults.get(position).getTotalStudents()) == 0)
            percentage = "0";
        else
            percentage = String.valueOf(Integer.parseInt(overAllResults.get(position).getPassStudents()) * 100 / Integer.parseInt(overAllResults.get(position).getTotalStudents()));
        holder.binding.tvPercent.setText(percentage);

    }

    @Override
    public int getItemCount() {
        return overAllResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        OverallResultListItemBinding binding;

        public ViewHolder(@NonNull OverallResultListItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

//            binding.getRoot().setOnClickListener(view -> {
//                recyclerViewClickInterface.onItemClick(getAdapterPosition());
//            });

        }
    }
}
