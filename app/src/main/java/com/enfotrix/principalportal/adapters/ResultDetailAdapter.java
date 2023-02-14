package com.enfotrix.principalportal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.principalportal.R;
import com.enfotrix.principalportal.models.Student;
import com.enfotrix.principalportal.models.StudentResult;

import java.util.ArrayList;
import java.util.Objects;

public class ResultDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<StudentResult> studentsResultsList;
    private final ArrayList<Student> studentsList;

    public ResultDetailAdapter(ArrayList<StudentResult> studentsResultsList, ArrayList<Student> studentsList) {
        this.studentsResultsList = studentsResultsList;
        this.studentsList = studentsList;
    }

    private Context context;

    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEMS = 1;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_header_item_layout, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_list_item, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

//        if (Objects.equals(studentsList.get(position).getStudentId(), studentsResultsList.get(position).getStudentID())){
//
//        }

        if (holder instanceof ResultDetailAdapter.ItemViewHolder) {
            ResultDetailAdapter.ItemViewHolder itemViewHolder = (ResultDetailAdapter.ItemViewHolder) holder;
            itemViewHolder.studentName.setText(studentsResultsList.get(position).getStudentName());
            itemViewHolder.fatherName.setText(studentsList.get(position).getFatherName());
            itemViewHolder.studentClass.setText(studentsList.get(position).getClassName());
            itemViewHolder.studentSection.setText(studentsList.get(position).getSectionName());
            itemViewHolder.status.setText(studentsList.get(position).getStatus());
            itemViewHolder.english.setText(studentsResultsList.get(position).getEnglish());
            itemViewHolder.physics.setText(studentsResultsList.get(position).getPhysics());
            itemViewHolder.pakStudy.setText(studentsResultsList.get(position).getPakStudy());
            itemViewHolder.urdu.setText(studentsResultsList.get(position).getUrdu());
            itemViewHolder.islamiyat.setText(studentsResultsList.get(position).getIslamiyat());
            itemViewHolder.math.setText(studentsResultsList.get(position).getMath());
            itemViewHolder.biology.setText(studentsResultsList.get(position).getBiology());
            itemViewHolder.scienceG.setText(studentsResultsList.get(position).getScienceG());
            itemViewHolder.computer.setText(studentsResultsList.get(position).getComputer());
            itemViewHolder.chemistry.setText(studentsResultsList.get(position).getChemistry());
        }
    }

    @Override
    public int getItemCount() {
        return studentsResultsList.size() + studentsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEMS;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView studentName, fatherName, studentClass, studentSection, physics, pakStudy, urdu, islamiyat, math, biology, scienceG, computer, english, chemistry, status;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            studentName = itemView.findViewById(R.id.tvName);
            fatherName = itemView.findViewById(R.id.tvFatherName);
            studentClass = itemView.findViewById(R.id.tvClass);
            studentSection = itemView.findViewById(R.id.tvSection);
            status = itemView.findViewById(R.id.tvStatus);
            physics = itemView.findViewById(R.id.tvPhysics);
            pakStudy = itemView.findViewById(R.id.tvPakStudy);
            urdu = itemView.findViewById(R.id.tvUrdu);
            islamiyat = itemView.findViewById(R.id.tvIslamiyat);
            math = itemView.findViewById(R.id.tvMath);
            biology = itemView.findViewById(R.id.tvBiology);
            scienceG = itemView.findViewById(R.id.tvScienceG);
            computer = itemView.findViewById(R.id.tvComputer);
            english = itemView.findViewById(R.id.tvEnglish);
            chemistry = itemView.findViewById(R.id.tvChemistry);

        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
