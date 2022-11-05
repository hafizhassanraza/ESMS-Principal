package com.enfotrix.principalportal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.enfotrix.principalportal.databinding.ActivityExamResultBinding;
import com.enfotrix.principalportal.models.OverAllAttendance;
import com.enfotrix.principalportal.models.OverAllResults;
import com.enfotrix.principalportal.models.StudentResult;
import com.enfotrix.principalportal.utilities.Constants;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Iterator;

public class ExamResultActivity extends AppCompatActivity {

    private ActivityExamResultBinding binding;
    private FirebaseFirestore firebaseFirestore;
    private ArrayList<OverAllResults> overAllResults;
    private ArrayList<StudentResult> studentResultsList;
    private String examID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExamResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        getClassesWithSections();
        getOverAllStudentsResults();
    }

    private void getOverAllStudentsResults() {
        for (int i=0; i <overAllResults.size(); i++) {
            int totalStudents = 0, passStudents = 0, failStudents = 0;
            for (int j=0; j <studentResultsList.size(); j++) {
                if (overAllResults.get(i).getSectionID().equals(studentResultsList.get(j).getSectionID())){
                    totalStudents++;
                    if (studentResultsList.get(j).getStatus().equals("Pass")) {
                        passStudents++;
                    } else {
                        failStudents++;
                    }
                }
            }
            float percetage = passStudents * 100 / totalStudents;
            overAllResults.get(i).setTotalStudents(String.valueOf(totalStudents));
            overAllResults.get(i).setPassStudents(String.valueOf(passStudents));
            overAllResults.get(i).setFailStudents(String.valueOf(failStudents));
            overAllResults.get(i).setPercentage(String.valueOf(percetage));
            Log.d("TAG", "getOverAllStudentsResults: " + overAllResults.get(i).getTotalStudents());
        }
    }

    private void init() {
        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Exams")
                .whereEqualTo("ExamName", getIntent().getStringExtra("ExamName"))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        examID = task.getResult().getDocuments().get(0).getString("ID");
                    }
                });
    }

    private void getClassesWithSections() {
        overAllResults = new ArrayList<>();
        firebaseFirestore.collectionGroup(Constants.KEY_COLLECTION_SECTIONS)
                .get()
                .addOnCompleteListener(task -> {
//                    ArrayList<String> studentsID = new ArrayList<>();
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        if (documentSnapshot.getString("ClassName") == null)
                            continue;
                        overAllResults.add(
                                new OverAllResults(
                                        documentSnapshot.getString("ClassName"),
                                        documentSnapshot.getString("SectionName"),
                                        documentSnapshot.getString("DocID"),
                                        "",
                                        "",
                                        "",
                                        ""
                                )
                        );
                        getRequiredStudents(documentSnapshot);

                    }


                });
    }

    private void getRequiredStudents(DocumentSnapshot documentSnapshot) {
        firebaseFirestore.collection("Students")
                .whereEqualTo("CurrentSection", documentSnapshot.getString("DocID"))
                .get()
                .addOnCompleteListener(task1 -> {
                    if (task1.getResult().getDocuments().size() != 0) {
                        for (DocumentSnapshot documentSnapshot1: task1.getResult()) {
//                                        studentsID.add(documentSnapshot1.getString("StudentId"));
                            getStudentResult(documentSnapshot1);
                        }
//                        Log.d("TAG", "getStudentResult: " + task1.getResult().getDocuments().size());
                    }
                });
    }

    private void getStudentResult(DocumentSnapshot documentSnapshot1) {
        Log.d("TAG", "getStudentResult: " + documentSnapshot1.getString("StudentId"));
        firebaseFirestore.collection("Result")
                .whereEqualTo("Exam", examID)
                .whereEqualTo("StudentId", documentSnapshot1.getString("StudentId"))
                .get()
                .addOnCompleteListener(task2 -> {
//                    Log.d("TAG", "getStudentResult: " + task2.getResult().getDocuments().size());
                    if(task2.isSuccessful() && task2.getResult()!=null
                            && task2.getResult().getDocuments().size() > 0) {
                        DocumentSnapshot snapshot = task2.getResult().getDocuments().get(0);
                        StudentResult studentResult = new StudentResult();
                        studentResult.setStudentID(snapshot.getString("StudentId"));
                        studentResult.setSectionID(snapshot.getString("SectionID"));
                        int totalObtainedMarks = 0;
                        for (DocumentSnapshot documentSnapshot2 : task2.getResult()) {
                            String obtainMarks = documentSnapshot2.getString("ObtainMarks");
                            switch (documentSnapshot2.getString("SubjectName")) {
                                case "Physics":
                                    studentResult.setPhysics(obtainMarks);
                                    totalObtainedMarks += Integer.parseInt(obtainMarks);
                                    break;
                                case "Pak-Study":
                                    studentResult.setPakStudy(obtainMarks);
                                    totalObtainedMarks += Integer.parseInt(obtainMarks);
                                    break;
                                case "Urdu":
                                    studentResult.setUrdu(obtainMarks);
                                    totalObtainedMarks += Integer.parseInt(obtainMarks);
                                    break;
                                case "Islamiyat":
                                    studentResult.setIslamiyat(obtainMarks);
                                    totalObtainedMarks += Integer.parseInt(obtainMarks);
                                    break;
                                case "Math":
                                    studentResult.setMath(obtainMarks);
                                    totalObtainedMarks += Integer.parseInt(obtainMarks);
                                    break;
                                case "Biology":
                                    studentResult.setBiology(obtainMarks);
                                    totalObtainedMarks += Integer.parseInt(obtainMarks);
                                    break;
                                case "Science(G)":
                                    studentResult.setScienceG(obtainMarks);
                                    totalObtainedMarks += Integer.parseInt(obtainMarks);
                                    break;
                                case "Computer":
                                    studentResult.setComputer(obtainMarks);
                                    totalObtainedMarks += Integer.parseInt(obtainMarks);
                                    break;
                                case "English":
                                    studentResult.setEnglish(obtainMarks);
                                    totalObtainedMarks += Integer.parseInt(obtainMarks);
                                    break;
                                case "Chemistry":
                                    studentResult.setChemistry(obtainMarks);
                                    totalObtainedMarks += Integer.parseInt(obtainMarks);
                                    break;
                            }
                        }
                        float percentage = (totalObtainedMarks * 100) / (100 * task2.getResult().getDocuments().size());
                        studentResult.setStatus(
                                ((percentage >= 40) ? "Pass" : "Fail")
                        );
                        studentResultsList.add(studentResult);
                    } else {
                        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}