package com.enfotrix.principalportal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.principalportal.R;
import com.enfotrix.principalportal.models.Model_DateSheet;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class Adapter_DateSheet extends RecyclerView.Adapter<Adapter_DateSheet.ViewHolders> {
    Context context;
    List<Model_DateSheet> dateSheetArrayList;

    public Adapter_DateSheet(Context context, List<Model_DateSheet> dateSheetArrayList) {
        this.context = context;
        this.dateSheetArrayList = dateSheetArrayList;
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_datesheet, parent, false);
        ViewHolders viewHolder = new ViewHolders(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolders holder, int position) {
        final Model_DateSheet model_dateSheet = dateSheetArrayList.get(position);

        String date = model_dateSheet.getDate();
        holder.tv_subName.setText(model_dateSheet.getSubjectName());
        holder.tv_day.setText(date.substring(0, 2));
        holder.tv_month.setText(date.substring(3, 6));
        holder.tv_classSection.setText(model_dateSheet.getClassName());
        holder.tv_classsession.setText(model_dateSheet.getSectionName());
    }

    @Override
    public int getItemCount() {
        return dateSheetArrayList.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {

        TextView tv_day, tv_month, tv_subName, tv_classSection, tv_classsession;

        public ViewHolders(@NonNull @NotNull View itemView) {
            super(itemView);

            tv_month = itemView.findViewById(R.id.tv_month);
            tv_subName = itemView.findViewById(R.id.tv_subName);
            tv_classsession = itemView.findViewById(R.id.tv_classsession);
            tv_classSection = itemView.findViewById(R.id.tv_classSection);
            tv_day = itemView.findViewById(R.id.tv_day);
            tv_month = itemView.findViewById(R.id.tv_month);

        }
    }
}
