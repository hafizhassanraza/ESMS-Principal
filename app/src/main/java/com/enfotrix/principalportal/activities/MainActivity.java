package com.enfotrix.principalportal.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.enfotrix.principalportal.databinding.ActivityMainBinding;
import com.enfotrix.principalportal.databinding.DialogExamResultBinding;
import com.enfotrix.principalportal.utilities.Constants;
import com.enfotrix.principalportal.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PreferenceManager preferenceManager;
    private FirebaseFirestore firebaseFirestore;
    private ArrayList<String> sessionList = new ArrayList<>();
    private ArrayList<String> examTypeList = new ArrayList<>();

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
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Session")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        for (DocumentSnapshot documentSnapshot :
                                task.getResult()) {
                            sessionList.add(documentSnapshot.getString("SessionName"));
                        }
                    }
                });
        examTypeList.add("Select Exam Type");
        examTypeList.add("General Exam");
        examTypeList.add("Phase Test");
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

        binding.cardViewExamResults.setOnClickListener(view -> showResultDialog());
    }

    private void showResultDialog() {
        Dialog dialog = new Dialog(this);

        DialogExamResultBinding examResultBinding = DialogExamResultBinding.inflate(getLayoutInflater());
        dialog.setContentView(examResultBinding.getRoot());

        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        setSpinner(examResultBinding.spSession, sessionList);

        setSpinner(examResultBinding.spSelectExamType, examTypeList);
        examResultBinding.spSelectExamType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getSelectedItemPosition() == 0) {
                    setExamNameSpinner(examResultBinding.spSelectExam, "");
                } else {
                    setExamNameSpinner(examResultBinding.spSelectExam, parent.getSelectedItem().toString());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        examResultBinding.btnContinue.setOnClickListener(v -> {
            if (examResultBinding.spSelectExamType.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "Select Exam Type First!", Toast.LENGTH_SHORT).show();
            } else {
                if (examResultBinding.spSelectExam.getAdapter().getCount() > 1
                        && examResultBinding.spSelectExam.getSelectedItemPosition() == 0) {
                    Toast.makeText(this, "Select Exam First!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, ExamResultActivity.class);
                    intent.putExtra("ExamName", examResultBinding.spSelectExam.getSelectedItem().toString());
                    startActivity(intent);
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    private void setExamNameSpinner(Spinner spSelectExam, @NonNull String examType) {
        ArrayList<String> examNameList = new ArrayList<>();
        examNameList.add("Select Exam");

        if (!examType.equals("")) {
            firebaseFirestore.collection("Exams")
                    .whereEqualTo("ExamCtg", examType)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            for (DocumentSnapshot documentSnapshot :
                                    task.getResult()) {
                                examNameList.add(documentSnapshot.getString("ExamName"));
                            }
                        }
                    });
        }
        setSpinner(spSelectExam, examNameList);
    }

    private void setSpinner(@NonNull Spinner spinner, ArrayList<String> list) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_spinner_item,
                list
        );
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

}