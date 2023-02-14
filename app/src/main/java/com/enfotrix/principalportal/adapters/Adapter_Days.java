package com.enfotrix.principalportal.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.principalportal.R;
import com.enfotrix.principalportal.models.Model_Days;

import java.util.List;

public class Adapter_Days extends RecyclerView.Adapter<Adapter_Days.ViewHolder> {
    private List<Model_Days> model_Days;
    Context context;

    public Adapter_Days(Context context, List<Model_Days> list_Days) {
        this.context = context;
        this.model_Days = list_Days;

    }

    // RecyclerView recyclerView;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_months, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Model_Days current_Days = model_Days.get(position);
        holder.txt_date.setText(model_Days.get(position).getDays());

        holder.txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                holder.txt_date.setTypeface(boldTypeface);
            }
        });

    }

    @Override
    public int getItemCount() {
        return model_Days.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView txt_date;


        public ViewHolder(View itemView) {
            super(itemView);
            this.txt_date = (TextView) itemView.findViewById(R.id.txt_days);


        }

    }
}
