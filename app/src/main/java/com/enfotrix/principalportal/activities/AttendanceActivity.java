package com.enfotrix.principalportal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.enfotrix.principalportal.databinding.ActivityAttendanceBinding;
import com.enfotrix.principalportal.models.OverAllAttendance;
import com.enfotrix.principalportal.models.Student;
import com.enfotrix.principalportal.utilities.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.eazegraph.lib.models.PieModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AttendanceActivity extends AppCompatActivity {
    private ArrayList<OverAllAttendance> overAllAttendances;
    private ActivityAttendanceBinding binding;
    private FirebaseFirestore firebaseFirestore;
    final Calendar myCalendar = Calendar.getInstance();
    int overAllPresent,overAllAbsent,overAllLeave,overAllTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAttendanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        setPreferredInfo();
        getAttendanceInfo();
        setListeners();

    }

    private void init() {
        binding.tvCheckDetails.setEnabled(false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        setClassSpinner();
        setSectionSpinner("");
    }

    private void setPreferredInfo() {
        binding.tvDate.setText(getCurrentDate());
    }

    private void setListeners() {

        binding.btnBack.setOnClickListener(view -> onBackPressed());

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateDate();
            }
        };
        binding.tvDate.setOnClickListener(view -> {
            new DatePickerDialog(AttendanceActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });


        binding.btnEnter.setOnClickListener(view -> {
            if (binding.classSpinner.getSelectedItem().toString().equals("Class")) {
                Toast.makeText(this, "Select Class First", Toast.LENGTH_SHORT).show();
            } else {
                if ((binding.sectionSpinner.getAdapter().getCount() > 1) && (binding.sectionSpinner.getSelectedItem().toString().equals("Section"))) {
                    Toast.makeText(this, "Select Section First", Toast.LENGTH_SHORT).show();
                } else {
                    loading(true);
                    getSectionId(binding.classSpinner.getSelectedItem().toString(), binding.sectionSpinner.getSelectedItem().toString());
                }
            }
        });

        binding.tvCheckDetails.setOnClickListener(view -> {
            loading(true);
            getClassesWithSections();
        });
    }

    private void getClassesWithSections() {
        overAllAttendances = new ArrayList<>();
        firebaseFirestore.collectionGroup(Constants.KEY_COLLECTION_SECTIONS)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            if (documentSnapshot.getString("ClassName") == null)
                                continue;
                            overAllAttendances.add(new OverAllAttendance(documentSnapshot.getString("ClassName"),
                                    documentSnapshot.getString("SectionName"),
                                    documentSnapshot.getString("DocID"),
                                    "",
                                    "",
                                    "",
                                    "",
                                    ""));
                        }

                        addOverAllAttendanceStatus(overAllAttendances);
                    }
                });
    }

    private void addOverAllAttendanceStatus(ArrayList<OverAllAttendance> overAllAttendances) {

        firebaseFirestore.collection(Constants.KEY_COLLECTION_ATTENDANCE)
                .whereEqualTo(Constants.KEY_DATE, binding.tvDate.getText().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (int i = 0; i < overAllAttendances.size(); i++) {
                            overAllPresent = 0; overAllAbsent = 0; overAllLeave = 0; overAllTotal = 0;
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                if (overAllAttendances.get(i).getSectionId().equals(documentSnapshot.getString(Constants.KEY_SECTION_ID))) {
                                     overAllTotal++;
                                    if (documentSnapshot.getString(Constants.KEY_STATUS).equals(Constants.KEY_PRESENT)) {
                                        overAllPresent++;
                                    } else if (documentSnapshot.getString(Constants.KEY_STATUS).equals(Constants.KEY_ABSENT)) {
                                        overAllAbsent++;
                                    } else {
                                        overAllLeave++;
                                    }
                                }
                            }

                            //int percent = overAllPresent * 100 / overAllTotal;
                            overAllAttendances.get(i).setTotal(String.valueOf(overAllTotal));
                            overAllAttendances.get(i).setPresent(String.valueOf(overAllPresent));
                            overAllAttendances.get(i).setAbsent(String.valueOf(overAllAbsent));
                            overAllAttendances.get(i).setLeave(String.valueOf(overAllLeave));
                            //overAllAttendances.get(i).setPercent(String.valueOf(percent));

                            if((i+1)==overAllAttendances.size()){
                                loading(false);
                                Intent intent = new Intent(AttendanceActivity.this, OverAllAttendanceActivity.class);
                                intent.putParcelableArrayListExtra("overAllAttendanceList", overAllAttendances);
                                intent.putExtra("selectedDate",binding.tvDate.getText().toString());
                                startActivity(intent);
                            }
                        }

                    }
                });

    }

    private void getSectionId(String className, String sectionName) {
        if (!Objects.equals(sectionName, "Section")) {
            firebaseFirestore.collection(Constants.KEY_COLLECTION_CLASS).document(className).collection(Constants.KEY_COLLECTION_SECTIONS)
                    .whereEqualTo(Constants.KEY_SECTION_NAME, sectionName)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && task.getResult() != null
                                    && task.getResult().getDocuments().size() > 0) {
                                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                String sectionId = documentSnapshot.getId();
                                getRequiredStudents(className, sectionId, sectionName);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            loading(false);
                            Toast.makeText(AttendanceActivity.this, "Couldn't fetch section Id", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            loading(false);
            Toast.makeText(this, "No Section Found", Toast.LENGTH_SHORT).show();
        }
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
                            Toast.makeText(AttendanceActivity.this, "No Student Record Found", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loading(false);
                        Toast.makeText(AttendanceActivity.this, "Couldn't get Students List", Toast.LENGTH_SHORT).show();
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
                            Intent intent = new Intent(AttendanceActivity.this, AttendanceDetailActivity.class);
                            intent.putExtra("selectedDate", binding.tvDate.getText().toString());
                            intent.putParcelableArrayListExtra("studentList", studentList);
                            startActivity(intent);

                        } else {
                            Toast.makeText(AttendanceActivity.this, "No Record Found", Toast.LENGTH_SHORT).show();
                            loading(false);
                        }
                    }
                });
    }

    private void getAttendanceInfo() {
        loading(true);
        firebaseFirestore.collection(Constants.KEY_COLLECTION_ATTENDANCE)
                .whereEqualTo(Constants.KEY_DATE, binding.tvDate.getText().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null
                                && task.getResult().getDocuments().size() > 0) {
                            int present = 0, absent = 0, leave = 0, totalStudents = 0;
                            totalStudents = task.getResult().getDocuments().size();

                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                String status = documentSnapshot.getString(Constants.KEY_STATUS);
                                assert status != null;
                                if (status.equals(Constants.KEY_PRESENT)) {
                                    present++;
                                } else if (status.equals(Constants.KEY_ABSENT)) {
                                    absent++;
                                } else {
                                    leave++;
                                }
                            }
                            loading(false);
                            //binding.btnDetailedAttendance.setEnabled(true);
                            binding.tvCheckDetails.setEnabled(true);
                            setAttendancePieChart(present, absent, leave, totalStudents);
                        } else {
                            loading(false);
                            Toast.makeText(AttendanceActivity.this, "No Attendance for this Date", Toast.LENGTH_SHORT).show();
                            Toast.makeText(AttendanceActivity.this, "Maybe Sunday!!!", Toast.LENGTH_SHORT).show();
                            //binding.btnDetailedAttendance.setEnabled(false);
                        }
                    }
                });
    }

    private void setClassSpinner() {
        ArrayList<String> classes = new ArrayList<>();
        classes.add("Class");
        firebaseFirestore.collection(Constants.KEY_COLLECTION_CLASS).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            classes.add(documentSnapshot.getId());
                        }
                    }
                });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, classes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.classSpinner.setAdapter(adapter);
        binding.classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!classes.get(i).equals("Class")) {
                    setSectionSpinner(classes.get(i));
                } else {
                    setSectionSpinner("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setSectionSpinner(String className) {
        ArrayList<String> sections = new ArrayList<>();
        sections.add("Section");
        if (!className.equals("")) {
            firebaseFirestore.collection(Constants.KEY_COLLECTION_CLASS).document(className).collection(Constants.KEY_COLLECTION_SECTIONS)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                sections.add(documentSnapshot.getString(Constants.KEY_SECTION_NAME));
                            }
                        }
                    });
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, sections);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.sectionSpinner.setAdapter(adapter);
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

        int percentage = present * 100 / totalStudents;
        binding.tvPercentage.setText(String.format(Locale.getDefault(), "%d%%", percentage));
        binding.tvTotalStudents.setText(String.valueOf(totalStudents));
        binding.tvPresent.setText(String.format(Locale.getDefault(), "%d Present", present));
        binding.tvAbsent.setText(String.format(Locale.getDefault(), "%d Absent", absent));
        binding.tvLeave.setText(String.format(Locale.getDefault(), "%d Leave", leave));
    }

    private String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return df.format(c);
    }

    private void updateDate() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.getDefault());
        binding.tvDate.setText(dateFormat.format(myCalendar.getTime()));

        binding.pieChart.clearChart();
        getAttendanceInfo();
    }

    private void loading(boolean flag) {
        if (flag)
            binding.progressBar.setVisibility(View.VISIBLE);
        else binding.progressBar.setVisibility(View.INVISIBLE);
    }
}