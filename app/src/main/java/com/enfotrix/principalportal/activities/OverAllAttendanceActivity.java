package com.enfotrix.principalportal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.enfotrix.principalportal.adapters.OverAllAttendanceAdapter;
import com.enfotrix.principalportal.databinding.ActivityOverAllAttendanceBinding;
import com.enfotrix.principalportal.models.OverAllAttendance;
import com.enfotrix.principalportal.models.Student;
import com.enfotrix.principalportal.utilities.Constants;
import com.enfotrix.principalportal.utilities.RecyclerViewClickInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;

public class OverAllAttendanceActivity extends AppCompatActivity implements RecyclerViewClickInterface {

    private ActivityOverAllAttendanceBinding binding;
    private ArrayList<OverAllAttendance> overAllAttendances = new ArrayList<>();
    private String selectedDate;
    private OverAllAttendanceAdapter overAllAttendanceAdapter;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOverAllAttendanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        getPreferredInfo();
        setPreferredInfo();
        setListeners();

    }

    void init(){
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    private void setListeners() {
        binding.btnBack.setOnClickListener(view -> onBackPressed());
    }

    private void setPreferredInfo() {
        binding.tvDate.setText(selectedDate);

        overAllAttendanceAdapter = new OverAllAttendanceAdapter(overAllAttendances, this,this);
        binding.rvAttendance.setAdapter(overAllAttendanceAdapter);
        binding.rvAttendance.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false));
        overAllAttendanceAdapter.notifyDataSetChanged();

    }


    private void getPreferredInfo() {
        selectedDate = getIntent().getStringExtra("selectedDate");
        overAllAttendances = getIntent().getParcelableArrayListExtra("overAllAttendanceList");
        updateAttendanceList(overAllAttendances);
    }

    private void updateAttendanceList(ArrayList<OverAllAttendance> overAllAttendances) {
        int totalDetail = 0, presentDetail = 0, absentDetail = 0, leaveDetail = 0, percentDetail;

        for (Iterator<OverAllAttendance> it = overAllAttendances.iterator(); it.hasNext(); ) {
            if (Integer.parseInt(it.next().getTotal()) == 0) {
                it.remove();
            }
        }
        for (int i = 0; i < overAllAttendances.size(); i++) {
            int percent = Integer.parseInt(overAllAttendances.get(i).getPresent()) * 100 / Integer.parseInt(overAllAttendances.get(i).getTotal());
            overAllAttendances.get(i).setPercent(percent + "%");

            totalDetail += Integer.parseInt(overAllAttendances.get(i).getTotal());
            presentDetail += Integer.parseInt(overAllAttendances.get(i).getPresent());
            absentDetail += Integer.parseInt(overAllAttendances.get(i).getAbsent());
            leaveDetail += Integer.parseInt(overAllAttendances.get(i).getLeave());
        }
        percentDetail = presentDetail * 100 / totalDetail ;

        binding.tvTotalDetail.setText(String.valueOf(totalDetail));
        binding.tvPresentDetail.setText(String.valueOf(presentDetail));
        binding.tvAbsentDetail.setText(String.valueOf(absentDetail));
        binding.tvLeaveDetail.setText(String.valueOf(leaveDetail));
        binding.tvPercentDetail.setText(percentDetail+"%");
    }

    private void getRequiredStudents(String className, String sectionId, String sectionName) {

        ArrayList<Student> studentList = new ArrayList<>();
        firebaseFirestore.collection(Constants.KEY_COLLECTION_STUDENTS)
                .whereEqualTo(Constants.KEY_CURRENT_CLASS, className)
                .whereEqualTo(Constants.KEY_CURRENT_SECTION, sectionId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null
                                && task.getResult().getDocuments().size() > 0) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                studentList.add(new Student((documentSnapshot.getString("FirstName") + " " + documentSnapshot.getString("LastName")).trim(),
                                        documentSnapshot.getString("FatherName"),
                                        className,
                                        sectionName,
                                        "",
                                        documentSnapshot.getString("FatherPhoneNumber"),
                                        documentSnapshot.getString("StudentId")));
                            }
                            addAttendanceStatus(studentList);
                        } else {
                            loading(false);
                            Toast.makeText(OverAllAttendanceActivity.this, "No Student Record Found", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loading(false);
                        Toast.makeText(OverAllAttendanceActivity.this, "Couldn't get Students List", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void addAttendanceStatus(ArrayList<Student> studentList) {
        firebaseFirestore.collection(Constants.KEY_COLLECTION_ATTENDANCE)
                .whereEqualTo(Constants.KEY_DATE, binding.tvDate.getText().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (int i = 0; i < studentList.size(); i++) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                if (studentList.get(i).getStudentId().equals(documentSnapshot.getString("StudentID"))) {
                                    studentList.get(i).setStatus(documentSnapshot.getString(Constants.KEY_STATUS));
                                    break;
                                }
                            }
                            if (studentList.get(i).getStatus().equals("")) {
                                //studentList.remove(i);
                                studentList.get(i).setStatus("Unmarked");
                            }
                        }
                        if (studentList.size() > 0) {
                            loading(false);
                            Intent intent = new Intent(OverAllAttendanceActivity.this, AttendanceDetailActivity.class);
                            intent.putExtra("selectedDate", binding.tvDate.getText().toString());
                            intent.putParcelableArrayListExtra("studentList", studentList);
                            startActivity(intent);

                        } else {
                            Toast.makeText(OverAllAttendanceActivity.this, "No Record Found", Toast.LENGTH_SHORT).show();
                            loading(false);
                        }
                    }
                });
    }

    private void loading(boolean flag) {
        if (flag)
            binding.progressBar.setVisibility(View.VISIBLE);
        else binding.progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemClick(int position) {
        loading(true);
        getRequiredStudents(overAllAttendances.get(position).getClass_(),overAllAttendances.get(position).getSectionId(),overAllAttendances.get(position).getSection());
    }
}