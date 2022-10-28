package com.enfotrix.principalportal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.enfotrix.principalportal.adapters.AttendanceDetailAdapter;
import com.enfotrix.principalportal.databinding.ActivityAttendanceDetailBinding;
import com.enfotrix.principalportal.models.Student;

import java.util.ArrayList;

public class AttendanceDetailActivity extends AppCompatActivity {
    private ActivityAttendanceDetailBinding binding;
    private ArrayList<Student> studentList = new ArrayList<Student>();
    private AttendanceDetailAdapter attendanceDetailAdapter;
    private String selectedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAttendanceDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        setPreferredInfo();
    }

    private void setPreferredInfo() {
        binding.tvDate.setText(selectedDate);

        attendanceDetailAdapter = new AttendanceDetailAdapter(studentList,this);
        binding.rvAttendance.setAdapter(attendanceDetailAdapter);
        binding.rvAttendance.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.VERTICAL,false));
        attendanceDetailAdapter.notifyDataSetChanged();
    }

    private void init(){
        binding.layoutAttendanceDetails.setVisibility(View.VISIBLE);
        selectedDate = getIntent().getStringExtra("selectedDate");
        studentList = getIntent().getParcelableArrayListExtra("studentList");

    }
}