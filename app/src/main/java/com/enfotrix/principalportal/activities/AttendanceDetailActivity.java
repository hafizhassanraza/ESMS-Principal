package com.enfotrix.principalportal.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.principalportal.adapters.AttendanceDetailAdapter;
import com.enfotrix.principalportal.databinding.ActivityAttendanceDetailBinding;
import com.enfotrix.principalportal.models.Student;

import java.util.ArrayList;

public class AttendanceDetailActivity extends AppCompatActivity {
    private ActivityAttendanceDetailBinding binding;
    private ArrayList<Student> studentList = new ArrayList<>();
    private AttendanceDetailAdapter attendanceDetailAdapter;
    private String selectedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAttendanceDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        setListeners();
        setPreferredInfo();
    }

    private void init(){
        selectedDate = getIntent().getStringExtra("selectedDate");
        studentList = getIntent().getParcelableArrayListExtra("studentList");

    }

    private void setListeners() {
        binding.btnBack.setOnClickListener(view -> onBackPressed());
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setPreferredInfo() {
        binding.tvDate.setText(selectedDate);

        attendanceDetailAdapter = new AttendanceDetailAdapter(studentList,this);
        binding.rvAttendance.setAdapter(attendanceDetailAdapter);
        binding.rvAttendance.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.VERTICAL,false));
        attendanceDetailAdapter.notifyDataSetChanged();
    }
}