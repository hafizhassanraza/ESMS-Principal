package com.enfotrix.principalportal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.enfotrix.principalportal.R;
import com.enfotrix.principalportal.adapters.Adapter_Days;
import com.enfotrix.principalportal.adapters.Adapter_TotalFee;
import com.enfotrix.principalportal.models.Fee;
import com.enfotrix.principalportal.models.Model_Days;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TotalFeeReceived extends AppCompatActivity {


    RecyclerView recyclerViewfee, rec_days;
    private FirebaseFirestore firestore;
    List<Fee> feeArrayList = new ArrayList<>();
    List<Model_Days> dayslist = new ArrayList<>();
    private static final String checkstatus = "Paid";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_fee_received);

        init();
        fetchdays();
        getFee();
    }

    public void init() {

        firestore = FirebaseFirestore.getInstance();
        recyclerViewfee = findViewById(R.id.list_feestudent);
        recyclerViewfee.setHasFixedSize(true);
        recyclerViewfee.setLayoutManager(new LinearLayoutManager(this));


        rec_days = findViewById(R.id.rec_days);
        rec_days.setHasFixedSize(true);
        rec_days.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


    }

    private void fetchdays() {


        Model_Days model_day = new Model_Days("All");
        dayslist.add(model_day);
        for (int i = 1; i < 32; i++) {
            Model_Days model_days = new Model_Days(i + "");
            dayslist.add(model_days);
        }
        Adapter_Days adapter_days = new Adapter_Days(getApplicationContext(), dayslist);
        rec_days.setAdapter(adapter_days);
        //rec_days.notify();

    }

    private void getFee() {
        Toast.makeText(this, getIntent().getStringExtra("Month"), Toast.LENGTH_SHORT).show();
        firestore.collection("Fee").whereEqualTo("Status", checkstatus).whereEqualTo("Month", getIntent().getStringExtra("month")).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    feeArrayList = task.getResult().toObjects(Fee.class);

                    Adapter_TotalFee adapterTotalFee = new Adapter_TotalFee(getApplicationContext(), feeArrayList);
                    recyclerViewfee.setAdapter(adapterTotalFee);
                    adapterTotalFee.notifyDataSetChanged();
                }


            }
        });


    }
}