package com.enfotrix.principalportal.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.principalportal.R;
import com.enfotrix.principalportal.models.NotificationModel;

import java.util.List;

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.ViewHolder> {
    private List<NotificationModel> notificationModels;


    // RecyclerView recyclerView;
    public AdapterNotification(List<NotificationModel> model_Feedback) {
        this.notificationModels = model_Feedback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_feedback, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final NotificationModel currentNotification = notificationModels.get(position);
        holder.txt_name.setText(currentNotification.getHeading());
        holder.txt_notifi.setText(currentNotification.getData());

        if (currentNotification.getStatus().equals("Unseen")) {
            holder.layout_notification.setBackgroundColor(Color.parseColor("#ADD8E6"));
        }

    }

    @Override
    public int getItemCount() {
        return notificationModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView txt_name, txt_notifi;
        RelativeLayout layout_notification;

        public ViewHolder(View itemView) {
            super(itemView);
            this.txt_name = (TextView) itemView.findViewById(R.id.txt_header_notify);
            this.txt_notifi = (TextView) itemView.findViewById(R.id.txt_notifi);
            layout_notification = itemView.findViewById(R.id.layout_Notify);
        }

    }
}


