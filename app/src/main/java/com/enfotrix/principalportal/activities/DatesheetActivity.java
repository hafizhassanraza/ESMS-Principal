package com.enfotrix.principalportal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.enfotrix.principalportal.R;
import com.enfotrix.principalportal.adapters.Adapter_DateSheet;
import com.enfotrix.principalportal.models.Class;
import com.enfotrix.principalportal.models.Model_DateSheet;
import com.enfotrix.principalportal.models.Section;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DatesheetActivity extends AppCompatActivity {

    List<Model_DateSheet> dateSheetArrayList;

    private RecyclerView rv_dateSheet;

    FirebaseFirestore db; // Firestore Instance
    private List<Class> classes;
    private List<Section> sections;
    public static String CLASS_NAME;
    public static String SECTION_ID;

    private String sessionname, exam, type;

    private Spinner spinClasses, spinSections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datesheet);


        init();
        fetchClasses();
    }

    private void fetchClasses() {
        db.collection("Class")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            classes.clear();
                            classes = task.getResult().toObjects(Class.class);
                            classes.add(0, new Class("Select Class"));
                            initClassesSpinner();

                        } else {
                            Toast.makeText(DatesheetActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DatesheetActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fetchSections(String className) {
        db.collection("Class").document(className)
                .collection("Sections")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            sections.clear();

                            if (task.getResult().size() > 0) {
                                sections = task.getResult().toObjects(Section.class);
                                sections.add(0, new Section("Select Section"));

                            } else {
                                Toast.makeText(DatesheetActivity.this, "No Sections for this class", Toast.LENGTH_SHORT).show();
                            }

                            initSectionsSpinner();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DatesheetActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initClassesSpinner() {
        ArrayAdapter classesAdapter
                = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, classes);


        spinClasses.setAdapter(classesAdapter);
        spinClasses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) view).setTextColor(getResources().getColor(R.color.hint));
                if (spinClasses.getSelectedItemId() != 0) {
                    fetchSections(spinClasses.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void initSectionsSpinner() {
        ArrayAdapter sectionsAdapter
                = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, sections);


        spinSections.setAdapter(sectionsAdapter);
        spinSections.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                getDatesheet(adapterView.getItemAtPosition(i).toString());

                ((TextView) view).setTextColor(getResources().getColor(R.color.hint));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    // Initialize all the views and variables
    public void init() {
        db = FirebaseFirestore.getInstance();
        spinClasses = findViewById(R.id.spin_class);
        spinSections = findViewById(R.id.spin_sections);

        dateSheetArrayList = new ArrayList<>();

        rv_dateSheet = findViewById(R.id.rv_dateSheet);
        rv_dateSheet.setHasFixedSize(true);
        rv_dateSheet.setHasFixedSize(true);
        rv_dateSheet.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        classes = new ArrayList<>();
        sections = new ArrayList<>();


        sessionname = getIntent().getStringExtra("session");
        type = getIntent().getStringExtra("type");
        exam = getIntent().getStringExtra("exam");
    }


    private void getDatesheet(String sectionname) {

        db.collection("DateSheet").whereEqualTo("Exam", exam).whereEqualTo("Session", sessionname)
                .whereEqualTo("SectionName", sectionname)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            dateSheetArrayList = task.getResult().toObjects(Model_DateSheet.class);
                        }

                        Collections.sort(dateSheetArrayList, new Comparator<Model_DateSheet>() {
                            @Override
                            public int compare(Model_DateSheet feedback, Model_DateSheet t1) {
                                return feedback.getDate().trim().compareToIgnoreCase(t1.getDate().trim());
                            }
                        });

                        Adapter_DateSheet adapterDateSheet = new Adapter_DateSheet(getApplicationContext(), dateSheetArrayList);
                        rv_dateSheet.setAdapter(adapterDateSheet);
                        adapterDateSheet.notifyDataSetChanged();

                    }

                });


    }

}