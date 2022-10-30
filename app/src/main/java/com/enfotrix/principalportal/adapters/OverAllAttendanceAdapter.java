package com.enfotrix.principalportal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.principalportal.R;
import com.enfotrix.principalportal.models.OverAllAttendance;
import com.enfotrix.principalportal.utilities.RecyclerViewClickInterface;

import java.util.ArrayList;

public class OverAllAttendanceAdapter extends RecyclerView.Adapter<OverAllAttendanceAdapter.ViewHolder> {
    private ArrayList<OverAllAttendance> overAllAttendances;
    private Context context;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public OverAllAttendanceAdapter(ArrayList<OverAllAttendance> overAllAttendances, Context context, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.overAllAttendances = overAllAttendances;
        this.context = context;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public OverAllAttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.overall_attendance_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OverAllAttendanceAdapter.ViewHolder holder, int position) {

        holder.class_.setText(overAllAttendances.get(position).getClass_());
        holder.section.setText(overAllAttendances.get(position).getSection());
        holder.total.setText(overAllAttendances.get(position).getTotal());
        holder.present.setText(overAllAttendances.get(position).getPresent());
        holder.absent.setText(overAllAttendances.get(position).getAbsent());
        holder.leave.setText(overAllAttendances.get(position).getLeave());
        holder.percent.setText(overAllAttendances.get(position).getPercent());

    }

    @Override
    public int getItemCount() {
        return overAllAttendances.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView class_,section,total,present,absent,leave,percent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            class_ = itemView.findViewById(R.id.tvClass);
            section = itemView.findViewById(R.id.tvSection);
            total = itemView.findViewById(R.id.tvTotal);
            present = itemView.findViewById(R.id.tvPresent);
            absent = itemView.findViewById(R.id.tvAbsent);
            leave = itemView.findViewById(R.id.tvLeave);
            percent = itemView.findViewById(R.id.tvPercent);

            itemView.setOnClickListener(view -> {
                recyclerViewClickInterface.onItemClick(getAdapterPosition());
            });

        }
    }
}
