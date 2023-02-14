package com.enfotrix.principalportal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.principalportal.R;
import com.enfotrix.principalportal.models.AnnouncementModel;
import com.enfotrix.principalportal.models.Fee;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {

    private List<AnnouncementModel> announcementModels;
    private Context context;
    final static String approveAnn = "Approved";
    FirebaseFirestore firebaseFirestor = FirebaseFirestore.getInstance();

    public AnnouncementAdapter(List<AnnouncementModel> announcementModels, Context context) {
        this.context = context;
        this.announcementModels = announcementModels;
    }

    @NonNull
    @Override
    public AnnouncementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.announcement_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementAdapter.ViewHolder holder, int position) {

        AnnouncementModel announcementModel = announcementModels.get(position);

        holder.announcementTitle.setText(announcementModel.getAnnouncement());
        holder.announcementDate.setText(announcementModel.getDate());
        holder.announcementStatus.setText(announcementModel.getStatus());

        holder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestor.collection("Announcement").document(announcementModel.getID()).update("Status", approveAnn).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "Approved", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return announcementModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView announcementTitle, announcementDate, announcementStatus;
        Button approve;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            announcementTitle = itemView.findViewById(R.id.titleannouncement);
            announcementDate = itemView.findViewById(R.id.dateannouncement);
            announcementStatus = itemView.findViewById(R.id.announcementStatus);
            approve = itemView.findViewById(R.id.approveAnnouncement);


        }
    }

}
