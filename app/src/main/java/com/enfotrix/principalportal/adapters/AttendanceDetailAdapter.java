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
import com.enfotrix.principalportal.models.Student;

import java.util.ArrayList;

public class AttendanceDetailAdapter extends RecyclerView.Adapter<AttendanceDetailAdapter.ViewHolder> {

    private ArrayList<Student> studentsList;
    private Context context;

    public AttendanceDetailAdapter(ArrayList<Student> studentsList, Context context) {
        this.studentsList = studentsList;
        this.context = context;
    }

    @NonNull
    @Override
    public AttendanceDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.attendance_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceDetailAdapter.ViewHolder holder, int position) {

        holder.name.setText(studentsList.get(position).getStudentName());
        holder.fathersName.setText(studentsList.get(position).getFatherName());
        holder.className.setText(studentsList.get(position).getClassName());
        holder.sectionName.setText(studentsList.get(position).getSectionName());
        holder.status.setText(studentsList.get(position).getStatus());
        holder.contactNumber.setText(studentsList.get(position).getFatherPhoneNumber());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+studentsList.get(position).getFatherPhoneNumber()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, fathersName, className, sectionName, status, contactNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvName);
            fathersName = itemView.findViewById(R.id.tvFatherName);
            className = itemView.findViewById(R.id.tvClass);
            sectionName = itemView.findViewById(R.id.tvSection);
            status = itemView.findViewById(R.id.tvStatus);
            contactNumber = itemView.findViewById(R.id.tvContact);

        }
    }
}
