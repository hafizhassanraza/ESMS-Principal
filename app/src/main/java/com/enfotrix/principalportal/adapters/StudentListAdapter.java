package com.enfotrix.principalportal.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.principalportal.R;
import com.enfotrix.principalportal.activities.SendNotification;
import com.enfotrix.principalportal.models.StudentList;

import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder> {

    private List<StudentList> students;
    private Context context;

    public StudentListAdapter(Context context, List<StudentList> students) {
        this.students = students;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.students_list, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final StudentList student = students.get(position);
        holder.studentName.setText(student.getFirstName() + " " + student.getLastName());
        holder.fatherName.setText(student.getFatherName());
        holder.regNo.setText(student.getRegNumber());
        holder.serialNo.setText(String.valueOf(position + 1));

        holder.sendnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SendNotification.class);
                intent.putExtra("StudentID", student.getStudentId());
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return students.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView studentName, fatherName, regNo, serialNo;
        private Button sendnotification;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.student_name);
            fatherName = itemView.findViewById(R.id.father_name);
            regNo = itemView.findViewById(R.id.reg_no);
            serialNo = itemView.findViewById(R.id.serial_no);
            sendnotification = itemView.findViewById(R.id.btnsendNotification);


        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
