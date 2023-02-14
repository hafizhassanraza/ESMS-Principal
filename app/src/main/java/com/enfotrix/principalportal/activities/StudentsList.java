package com.enfotrix.principalportal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.enfotrix.principalportal.R;
import com.enfotrix.principalportal.adapters.StudentListAdapter;
import com.enfotrix.principalportal.models.Student;
import com.enfotrix.principalportal.models.StudentList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StudentsList extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private List<StudentList> students;
    RecyclerView recyclerView;
    StudentListAdapter adapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        init();
        getStudents();

    }

    public void init() {

        firebaseFirestore = FirebaseFirestore.getInstance();
        students = new ArrayList<>();
        adapter = new StudentListAdapter(StudentsList.this, students);
        recyclerView = findViewById(R.id.rv_students_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


    }

    public void getStudents() {

        firebaseFirestore.collection("Students").whereEqualTo("CurrentClass", getIntent().getStringExtra("Class")).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isComplete()) {
                            students = task.getResult().toObjects(StudentList.class);
                            recyclerView.setAdapter(new StudentListAdapter(StudentsList.this, students));
                        }
                    }

                });

    }
}