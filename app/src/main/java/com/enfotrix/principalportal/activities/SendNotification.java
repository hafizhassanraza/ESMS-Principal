package com.enfotrix.principalportal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.enfotrix.principalportal.R;
import com.enfotrix.principalportal.adapters.AdapterNotification;
import com.enfotrix.principalportal.models.NotificationModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendNotification extends AppCompatActivity {
    List<NotificationModel> listNotification = new ArrayList<>();
    private FirebaseFirestore firestore;
    private EditText textNotification, headingNotification;
    private Button submitNotification;
    private AdapterNotification adapterNotification;
    RecyclerView recNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);

        init();
        fetchFeedback();

        submitNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkEmpty())
                    addFeedback();
            }
        });

    }

    public void init() {
        textNotification = findViewById(R.id.text_feedback);
        headingNotification = findViewById(R.id.feedback_heading);
        submitNotification = findViewById(R.id.btn_submit_feedback);
        firestore = FirebaseFirestore.getInstance();

        recNotification = findViewById(R.id.list_Feedback);
        recNotification.setHasFixedSize(true);
        recNotification.setLayoutManager(new LinearLayoutManager(this));

    }


    private void fetchFeedback() {


        firestore.collection("Notifications").whereEqualTo("StudentID", getIntent().getStringExtra("StudentID"))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isComplete()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                NotificationModel notificationModel = new NotificationModel(
                                        documentSnapshot.getString("Data"),
                                        documentSnapshot.getTimestamp("Date"),
                                        documentSnapshot.getString("Heading"),
                                        documentSnapshot.getString("StudentID"),
                                        documentSnapshot.getString("Status"));

                                listNotification.add(notificationModel);
                            }


                            adapterNotification = new AdapterNotification(listNotification);
                            recNotification.setAdapter(adapterNotification);
                            adapterNotification.notifyDataSetChanged();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SendNotification.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

    }


    private void addFeedback() {


        Map<String, Object> map = new HashMap<>();
        map.put("Data", textNotification.getText().toString());
        map.put("Timestamp", getDate());
        map.put("StudentID", getIntent().getStringExtra("StudentID"));
        map.put("Heading", headingNotification.getText().toString());
        map.put("Status", "Unseen" );

        firestore.collection("Notifications")
                .add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        textNotification.setText(null);
                        headingNotification.setText(null);
                        Toast.makeText(SendNotification.this, "Notification Submitted Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SendNotification.this, e.toString(), Toast.LENGTH_SHORT).show();

                    }
                });
    }


    private Timestamp getDate() {
        Date date = new Date();
        Timestamp timestamp2 = new Timestamp(date.getTime());
        return timestamp2;
    }

    private boolean checkEmpty() {
        Boolean isEmpty = false;
        if (textNotification.getText().toString().trim().isEmpty())
            Toast.makeText(getApplicationContext(), "Please write Notification", Toast.LENGTH_SHORT).show();
        else isEmpty = true;
        return isEmpty;
    }


}