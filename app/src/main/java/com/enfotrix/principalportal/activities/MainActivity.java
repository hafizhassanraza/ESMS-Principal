package com.enfotrix.principalportal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.enfotrix.principalportal.R;
import com.enfotrix.principalportal.databinding.ActivityMainBinding;
import com.enfotrix.principalportal.utilities.Constants;
import com.enfotrix.principalportal.utilities.PreferenceManager;

import org.eazegraph.lib.models.PieModel;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        preferenceManager = new PreferenceManager(this);
        setContentView(binding.getRoot());

        setPreferredInfo();
        setListeners();
    }

    private void setPreferredInfo() {
        binding.actionBarLayout.tvActionBar.setText(preferenceManager.getString(Constants.KEY_NAME).toUpperCase(Locale.ROOT));
        setAttendancePieChart();
    }

    private void setAttendancePieChart() {
        binding.pieChart.addPieSlice(
                new PieModel(
                        "Present",
                        60,
                        Color.parseColor("#76DD7B")
                ));
        binding.pieChart.addPieSlice(
                new PieModel(
                        "Absent",
                        20,
                        Color.parseColor("#EB3F62")));
        binding.pieChart.addPieSlice(
                new PieModel(
                        "Leave",
                        20,
                        Color.parseColor("#61B8E0")
                ));
        binding.pieChart.startAnimation();
    }

    private void setListeners() {
        binding.actionBarLayout.btnLogOut.setOnClickListener(view -> {
            preferenceManager.clear();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        });
    }
}