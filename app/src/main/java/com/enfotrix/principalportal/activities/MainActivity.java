package com.enfotrix.principalportal.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.enfotrix.principalportal.R;
import com.enfotrix.principalportal.adapters.ApprovedAnnouncementAdapter;
import com.enfotrix.principalportal.adapters.sliderAdapter;
import com.enfotrix.principalportal.databinding.ActivityMainBinding;
import com.enfotrix.principalportal.databinding.DialogExamResultBinding;
import com.enfotrix.principalportal.models.AnnouncementModel;
import com.enfotrix.principalportal.models.SliderItem;
import com.enfotrix.principalportal.utilities.Constants;
import com.enfotrix.principalportal.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PreferenceManager preferenceManager;
    private FirebaseFirestore firebaseFirestore;
    private ArrayList<String> sessionList = new ArrayList<>();
    private ArrayList<String> examTypeList = new ArrayList<>();


    private String txtsession, txtexamtype, txtType, txtExam;
    private ArrayList<String> sessions;
    private ArrayList<String> examtype;
    private ArrayList<String> examctg;

    private ArrayList<String> classes = new ArrayList<>();
    private ArrayList<String> sections = new ArrayList<>();

    private String txtmonth;
    final static String status = "Approved";
    private List<AnnouncementModel> announcementModelList = new ArrayList<>();
    private ApprovedAnnouncementAdapter adapter;
    String dateSelected, sessionSelected = "2022";// = String.valueOf(dayOfMonth)

    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();


    public static String CLASS_NAME;
    public static String SECTION_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        fetchsession();
        fetchclasses();
        setPreferredInfo();
        setListeners();
        getAnnouncements();
        sliderSet();

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

        sections.add(0, "Select Section");
        classes.add(0, "Select Class");

        sessions = new ArrayList<>();
        examtype = new ArrayList<>();
        examctg = new ArrayList<>();

        viewPager2 = findViewById(R.id.viewpager_imageslider);


        binding.rvAnnouncements.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false));

    }

    private Runnable sliderRunnabler = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);


        }
    };

    public void sliderSet() {
        List<SliderItem> sliderItemList = new ArrayList<>();
        sliderItemList.add(new SliderItem(R.drawable.ad_one));
        sliderItemList.add(new SliderItem(R.drawable.ad_two));

        viewPager2.setAdapter(new sliderAdapter(sliderItemList, viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnabler);
                sliderHandler.postDelayed(sliderRunnabler, 3000);
            }
        });

    }


    private void setPreferredInfo() {
        binding.actionBarLayout.tvActionBar.setText(preferenceManager.getString(Constants.KEY_NAME).toUpperCase(Locale.ROOT));
    }

    private void setListeners() {
        binding.btnLogOut.setOnClickListener(view -> {
            preferenceManager.clear();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

        binding.cardViewAttendance.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AttendanceActivity.class));
        });
        binding.cardViewFee.setOnClickListener(view -> {
            monthdialog();

        });
        binding.cardViewExamResults.setOnClickListener(view -> showResultDialog());


        binding.cardViewAnnouncements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AnnoucementsActivity.class));

            }
        });

        binding.cardViewNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentsClassDialog();
            }
        });

        binding.cardDatesheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDatesheet();

            }
        });
    }


    public void showDialogDatesheet() {
        txtType = "";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.result_dialog, null);

        AppCompatSpinner spinner = view.findViewById(R.id.txtsession);
        AppCompatSpinner spnType = view.findViewById(R.id.text_type);
        AppCompatSpinner spnExam = view.findViewById(R.id.text_exam);
        AppCompatButton btn_getResult = view.findViewById(R.id.btn_getResult);


        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.dropdown_session, sessions);
        spinner.setAdapter(arrayAdapter);


       /* ArrayAdapter Adapter = new ArrayAdapter(getContext(), R.layout.dropdown_examtype, examtype);
        spinner1.setAdapter(Adapter);*/
//        text_examtype.setText(Adapter.getItem(0).toString(), false);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                txtsession = adapterView.getItemAtPosition(i).toString();

                if (txtsession != null && txtType != null)
                    fetchresult(spnExam, txtsession, txtType);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });
        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                txtType = adapterView.getItemAtPosition(i).toString();

                if (txtsession != null && txtType != null) {

                    fetchresult(spnExam, txtsession, txtType);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });


        spnExam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                txtExam = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn_getResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                    if (!txtExam.isEmpty() || txtExam != null) {
                        Intent resultIntent = new Intent(MainActivity.this, DatesheetActivity.class);


                        resultIntent.putExtra("session", txtsession);
                        resultIntent.putExtra("type", txtType);
                        resultIntent.putExtra("exam", txtExam);
                        startActivity(resultIntent);
                    } else
                        Toast.makeText(getApplicationContext(), "Please Select Exam!", Toast.LENGTH_SHORT).show();
                }


//                Toast.makeText(getContext(), classid + "\n" + classgrade, Toast.LENGTH_SHORT).show();

            }
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();


    }


    private void fetchresult(AppCompatSpinner spinner, String sessionstxt, String type) {

        examtype.clear();
        spinner.setAdapter(null);
        //Toast.makeText(getContext(), sessionstxt, Toast.LENGTH_SHORT).show();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        firebaseFirestore.collection("Exams").whereEqualTo("ExamCtg", type).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            examtype.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                if (document.getString("ExamCtg").equals(type)) {
                                    examtype.add(document.getString("ExamName"));
                                    ArrayAdapter Adapter = new ArrayAdapter(getApplicationContext(), R.layout.dropdown_examtype, examtype);
                                    Adapter.notifyDataSetChanged();
                                    spinner.setAdapter(Adapter);
                                }
                            }
                            progressDialog.dismiss();

                        }
                    }
                });

    }


    private void StudentsClassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.classes_spinner_layout, null);
        AppCompatSpinner spinner = view.findViewById(R.id.spinnerclasses);
        AppCompatSpinner spinner1 = view.findViewById(R.id.spinnersections);
        Button button = view.findViewById(R.id.getStudents);

        spinner1.setVisibility(View.INVISIBLE);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.dropdown_session, classes);
//        autoCompletetxt.setText(arrayAdapter.getItem(0).toString(), false);
        spinner.setAdapter(arrayAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, StudentsList.class);
                        intent.putExtra("Class", parent.getItemAtPosition(position).toString());
                        startActivity(intent);


                    }
                });
                // fetchSections(parent.getItemAtPosition(position).toString());
               /* ArrayAdapter arrayAdapter1 = new ArrayAdapter(getApplicationContext(), R.layout.dropdown_session, sections);
                spinner1.setAdapter(arrayAdapter1);*/


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void monthdialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.fee_dialog, null);

        AppCompatSpinner spinner = view.findViewById(R.id.autoCompletetxt);
        AppCompatSpinner autoCompletetxtmonth = view.findViewById(R.id.autoCompletetxtmonth);
        AppCompatButton btn_login = view.findViewById(R.id.btn_login);
        btn_login.setText("Get Students");

        /////////////////// session
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.dropdown_session, sessions);
//        autoCompletetxt.setText(arrayAdapter.getItem(0).toString(), false);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txtsession = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ////////////////////// month

        String[] monthNames = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};

        ArrayAdapter month = new ArrayAdapter(getApplicationContext(), R.layout.dropdown_session, monthNames);
//        autoCompletetxtmonth.setText(month.getItem(0).toString(), false);
        autoCompletetxtmonth.setAdapter(month);

        autoCompletetxtmonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txtmonth = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FeeActivity.class);
                intent.putExtra("sessions", txtsession);
                intent.putExtra("month", txtmonth);
                startActivity(intent);
            }
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

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
            public void onNothingSelected(AdapterView<?> parent) {
            }
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

    private void fetchclasses() {
        firebaseFirestore.collection("Class").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        classes.add(documentSnapshot.getId());
                    }
                }
            }
        });
    }

    private void fetchSections(String className) {

        firebaseFirestore.collection("Class").document(className)
                .collection("Sections")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                sections.add(snapshot.getString("SectionName"));

                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private ArrayList<String> fetchsession() {


        firebaseFirestore.collection("Session").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                sessions.add(document.getId());
                            }
                        }
                    }
                });

        return sessions;
    }

    private void getAnnouncements() {

        firebaseFirestore.collection("Announcement").whereEqualTo("Status", status).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isComplete()) {
                    announcementModelList = task.getResult().toObjects(AnnouncementModel.class);
                    adapter = new ApprovedAnnouncementAdapter(announcementModelList, getApplicationContext());
                    binding.rvAnnouncements.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else
                    Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}