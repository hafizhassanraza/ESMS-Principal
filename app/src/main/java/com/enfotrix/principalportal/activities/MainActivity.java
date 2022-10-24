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
    private FirebaseFirestore firebaseFirestore;
    private Integer present = 0, absent = 0, leave = 0, totalStudents = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        setPreferredInfo();
        getAttendanceInfo();
        setListeners();

    }

    private void getAttendanceInfo() {
        firebaseFirestore.collection(Constants.KEY_COLLECTION_ATTENDANCE)
                .whereEqualTo(Constants.KEY_DATE, getCurrentDate())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null
                                && task.getResult().getDocuments().size() > 0) {

                            totalStudents = task.getResult().getDocuments().size();

                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                String status = documentSnapshot.getString(Constants.KEY_STATUS);
                                if (status.equals(Constants.KEY_PRESENT)) {
                                    present++;
                                } else if (status.equals(Constants.KEY_ABSENT)) {
                                    absent++;
                                } else {
                                    leave++;
                                }
                            }
                            setAttendancePieChart(present, absent, leave, totalStudents);
                        }
                    }
                });
    }

    private void init() {
        preferenceManager = new PreferenceManager(this);
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    private void setPreferredInfo() {
        binding.actionBarLayout.tvActionBar.setText(preferenceManager.getString(Constants.KEY_NAME).toUpperCase(Locale.ROOT));
    }

    private void setAttendancePieChart(Integer present, Integer absent, Integer leave, Integer totalStudents) {

        binding.pieChart.addPieSlice(
                new PieModel(
                        "Present",
                        present,
                        Color.parseColor("#76DD7B")
                ));
        binding.pieChart.addPieSlice(
                new PieModel(
                        "Absent",
                        absent,
                        Color.parseColor("#EB3F62")));
        binding.pieChart.addPieSlice(
                new PieModel(
                        "Leave",
                        leave,
                        Color.parseColor("#61B8E0")
                ));
        binding.pieChart.startAnimation();

        Integer percentage = present * 100 / totalStudents;
        binding.tvPercentage.setText(String.valueOf(percentage) + "%");
        binding.tvTotalStudents.setText(String.valueOf(totalStudents));
        binding.tvPresent.setText(String.valueOf(present) + " Present");
        binding.tvAbsent.setText(String.valueOf(absent) + " Absent");
        binding.tvLeave.setText(String.valueOf(leave) + " Leave");
    }

    private void setListeners() {
        binding.actionBarLayout.btnLogOut.setOnClickListener(view -> {
            preferenceManager.clear();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

        binding.cardViewAttendance.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,AttendanceActivity.class));
        });
    }

    private String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }
}