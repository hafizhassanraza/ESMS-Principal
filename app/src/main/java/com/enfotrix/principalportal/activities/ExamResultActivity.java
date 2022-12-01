package com.enfotrix.principalportal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.enfotrix.principalportal.adapters.OverAllAttendanceAdapter;
import com.enfotrix.principalportal.adapters.OverAllResultsAdapter;
import com.enfotrix.principalportal.databinding.ActivityExamResultBinding;
import com.enfotrix.principalportal.models.OverAllResult;
import com.enfotrix.principalportal.models.StudentResult;
import com.enfotrix.principalportal.utilities.Constants;
import com.enfotrix.principalportal.utilities.RecyclerViewClickInterface;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ExamResultActivity extends AppCompatActivity implements RecyclerViewClickInterface {

    private ActivityExamResultBinding binding;
    private FirebaseFirestore firebaseFirestore;
    private ArrayList<OverAllResult> overAllResults;
    private ArrayList<StudentResult> studentResultsList;
    private String examID;
    private OverAllResultsAdapter overAllResultsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExamResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        getClassesWithSections();
//        getOverAllStudentsResults();


    }

    private void recyclerViewInIt() {
        overAllResultsAdapter = new OverAllResultsAdapter(overAllResults, this);
        binding.rvAttendance.setAdapter(overAllResultsAdapter);
        binding.rvAttendance.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false));
        overAllResultsAdapter.notifyDataSetChanged();
        loading(false);
    }

    private void getOverAllStudentsResults() {
        for (int i = 0; i < overAllResults.size(); i++) {
            int totalStudents = 0, passStudents = 0, failStudents = 0;
            for (int j = 0; j < studentResultsList.size(); j++) {
                if (overAllResults.get(i).getSectionID().equals(studentResultsList.get(j).getSectionID())) {
                    totalStudents++;
                    if (studentResultsList.get(j).getStatus().equals("Pass")) {
                        passStudents++;
                    } else {
                        failStudents++;
                    }
                }
            }
//            float percetage = passStudents * 100 / totalStudents;
            overAllResults.get(i).setTotalStudents(String.valueOf(totalStudents));
            overAllResults.get(i).setPassStudents(String.valueOf(passStudents));
            overAllResults.get(i).setFailStudents(String.valueOf(failStudents));
            overAllResults.get(i).setPercentage(String.valueOf(0));
        }
        recyclerViewInIt();
    }

    private void init() {
        loading(true);
        firebaseFirestore = FirebaseFirestore.getInstance();
        overAllResults = new ArrayList<>();
        studentResultsList = new ArrayList<>();

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

        firebaseFirestore.collectionGroup(Constants.KEY_COLLECTION_SECTIONS)
                .get()
                .addOnCompleteListener(task -> {
//                    ArrayList<String> studentsID = new ArrayList<>();
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        if (documentSnapshot.getString("ClassName") == null)
                            continue;
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
//                        Log.d("TAG", "getStudentResult: " + task1.getResult().getDocuments().size());
                        overAllResults.add(
                                new OverAllResult(
                                        documentSnapshot.getString("ClassName"),
                                        documentSnapshot.getString("SectionName"),
                                        documentSnapshot.getString("DocID"),
                                        "",
                                        "",
                                        "",
                                        ""
                                )
                        );
                        for (DocumentSnapshot documentSnapshot1 : task1.getResult()) {
//                                        studentsID.add(documentSnapshot1.getString("StudentId"));
                            getStudentResult(documentSnapshot1);
                        }
                    }
                });

    }

    private void getStudentResult(DocumentSnapshot documentSnapshot1) {
        //Log.d("TAG", "getStudentResult: " + documentSnapshot1.getString("StudentId"));
        firebaseFirestore.collection("Result")
                .whereEqualTo("Exam", examID)
                .whereEqualTo("StudentId", documentSnapshot1.getString("StudentId"))
                .get()
                .addOnCompleteListener(task2 -> {

//                    Log.d("TAG", "getStudentResult: " + task2.getResult().size());
                    /*Log.d("TAG", "getStudentResult: " + documentSnapshot1.getString("CurrentClass") + " "
                            + documentSnapshot1.getString("FirstName") + " "
                            + documentSnapshot1.getString("StudentId") + " " + task2.getResult().size());*/

                    if (task2.isSuccessful() && task2.getResult() != null
                            && task2.getResult().getDocuments().size() > 0) {

//                        Log.d("TAG", "getStudentResult: " + documentSnapshot1.getString("CurrentClass") + " "
//                                + documentSnapshot1.getString("FirstName") + " "
//                                + documentSnapshot1.getString("StudentId") + " " + task2.getResult().size() + "  " + i++);

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
                        //Log.d("TAG", "getStudentResult: "+ totalObtainedMarks +"   " + i++);
                    } else {
                        //Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
                        /*Log.d("TAG", "getStudentResult: " + documentSnapshot1.getString("CurrentClass") + " "
                        + documentSnapshot1.getString("FirstName") + " "
                        + documentSnapshot1.getString("StudentId"));*/

                    }
                    getOverAllStudentsResults();
                });
    }

    @Override
    public void onItemClick(int position) {

    }

    private void loading(boolean flag) {
        if (flag)
            binding.progressBar.setVisibility(View.VISIBLE);
        else {
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.viewDetails.setVisibility(View.VISIBLE);
            binding.layoutDetails.setVisibility(View.VISIBLE);
            binding.rvAttendance.setVisibility(View.VISIBLE);
            binding.layoutTotalStudentsDetail.setVisibility(View.VISIBLE);
            binding.viewDetails2.setVisibility(View.VISIBLE);
        }
    }

}