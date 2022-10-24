package com.enfotrix.principalportal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.enfotrix.principalportal.R;
import com.enfotrix.principalportal.databinding.ActivityAttendanceBinding;

public class AttendanceActivity extends AppCompatActivity {

    private ActivityAttendanceBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAttendanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}