package com.enfotrix.principalportal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.enfotrix.principalportal.R;
import com.enfotrix.principalportal.adapters.ResultDetailAdapter;
import com.enfotrix.principalportal.models.Student;
import com.enfotrix.principalportal.models.StudentResult;

import java.util.ArrayList;

public class ResultDetailsActivity extends AppCompatActivity {

    ArrayList<StudentResult> studentResultList = new ArrayList<>();
    ArrayList<Student> studentArrayList = new ArrayList<>();
    ResultDetailAdapter resultDetailAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_details);


        init();

        Toast.makeText(this, studentResultList.get(1).getStudentID(), Toast.LENGTH_SHORT).show();


    }

    public void init() {
        studentResultList = getIntent().getParcelableArrayListExtra("studentResultList");
        studentArrayList = getIntent().getParcelableArrayListExtra("studentList");

       /* for (Student student : studentArrayList) {

            Toast.makeText(this, student.getStudentName(), Toast.LENGTH_SHORT).show();
            *//*for (StudentResult studentResult : studentResultList) {
                if (studentResult.getStudentID().equals(student.getStudentId())) {
                    studentResult.setStudentName(student.getStudentName());

                }
            }*//*


        }*/


        recyclerView = findViewById(R.id.rvResult);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultDetailAdapter = new ResultDetailAdapter(studentResultList, studentArrayList);

        recyclerView.setAdapter(resultDetailAdapter);
    }
}