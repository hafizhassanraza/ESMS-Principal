package com.enfotrix.principalportal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.enfotrix.principalportal.R;
import com.enfotrix.principalportal.databinding.ActivityMainBinding;
import com.enfotrix.principalportal.utilities.Constants;
import com.enfotrix.principalportal.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.eazegraph.lib.models.PieModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        setPreferredInfo();
        setListeners();
    }


    private void init() {
        preferenceManager = new PreferenceManager(this);
    }

    private void setPreferredInfo() {
        binding.actionBarLayout.tvActionBar.setText(preferenceManager.getString(Constants.KEY_NAME).toUpperCase(Locale.ROOT));
    }

    private void setListeners() {
        binding.actionBarLayout.btnLogOut.setOnClickListener(view -> {
            preferenceManager.clear();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

        binding.cardViewAttendance.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AttendanceActivity.class));
        });

        binding.cardViewExamResults.setOnClickListener(
                view -> startActivity(new Intent(MainActivity.this, ExamResultActivity.class))
        );
    }
}