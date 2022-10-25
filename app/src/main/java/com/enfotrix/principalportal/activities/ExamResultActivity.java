package com.enfotrix.principalportal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.enfotrix.principalportal.R;
import com.enfotrix.principalportal.databinding.ActivityExamResultBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class ExamResultActivity extends AppCompatActivity {

    private ActivityExamResultBinding binding;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExamResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        getExamResults();
    }

    private void init() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    private void getExamResults() {
//        firebaseFirestore.collection("Result")
//                .get()

    }
}