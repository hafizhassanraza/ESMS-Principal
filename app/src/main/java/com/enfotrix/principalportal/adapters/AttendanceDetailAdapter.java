package com.enfotrix.principalportal.adapters;

import android.annotation.SuppressLint;
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

public class AttendanceDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Student> studentsList;
    private Context context;

    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEMS = 1;


    public AttendanceDetailAdapter(ArrayList<Student> studentsList, Context context) {
        this.studentsList = studentsList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER){
            View view = LayoutInflater.from(context).inflate(R.layout.attendance_header_item_layout, parent, false);
            return new HeaderViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.attendance_list_item, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if(holder instanceof ItemViewHolder){
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.name.setText(studentsList.get(position).getStudentName());
            itemViewHolder.fathersName.setText(studentsList.get(position).getFatherName());
            itemViewHolder.className.setText(studentsList.get(position).getClassName());
            itemViewHolder.sectionName.setText(studentsList.get(position).getSectionName());
            itemViewHolder.status.setText(studentsList.get(position).getStatus());
            itemViewHolder.contactNumber.setText(studentsList.get(position).getFatherPhoneNumber());
        }

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

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEMS;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView name, fathersName, className, sectionName, status, contactNumber;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvName);
            fathersName = itemView.findViewById(R.id.tvFatherName);
            className = itemView.findViewById(R.id.tvClass);
            sectionName = itemView.findViewById(R.id.tvSection);
            status = itemView.findViewById(R.id.tvStatus);
            contactNumber = itemView.findViewById(R.id.tvContact);

        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
