package com.enfotrix.principalportal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.enfotrix.principalportal.R;
import com.enfotrix.principalportal.adapters.AnnouncementAdapter;
import com.enfotrix.principalportal.databinding.ActivityAnnoucementsBinding;
import com.enfotrix.principalportal.databinding.ActivityFeeDetailsBinding;
import com.enfotrix.principalportal.models.AnnouncementModel;
import com.enfotrix.principalportal.models.Fee;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AnnoucementsActivity extends AppCompatActivity {

    ActivityAnnoucementsBinding binding;
    private List<AnnouncementModel> announcementModelList;
    AnnouncementAdapter adapter;
    FirebaseFirestore firebaseFirestore;
    final static String status = "Pending";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAnnoucementsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        firebaseFirestore = FirebaseFirestore.getInstance();
        announcementModelList = new ArrayList<>();
        binding.rvAnnouncements.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false));


        getAnnouncements();
    }

    private void getAnnouncements() {

        firebaseFirestore.collection("Announcement").whereEqualTo("Status", status).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isComplete()) {
                    announcementModelList = task.getResult().toObjects(AnnouncementModel.class);
                    adapter = new AnnouncementAdapter(announcementModelList, getApplicationContext());
                    binding.rvAnnouncements.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                } else
                    Toast.makeText(AnnoucementsActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}