package com.enfotrix.principalportal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.enfotrix.principalportal.R;
import com.enfotrix.principalportal.adapters.Adapter_PartiallyFee;
import com.enfotrix.principalportal.adapters.Adapter_TotalFee;
import com.enfotrix.principalportal.models.Fee;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PartiallyPaidFee extends AppCompatActivity {

    List<Fee> feeArrayList = new ArrayList<>();
    RecyclerView recyclerViewfee;
    private FirebaseFirestore firestore;
    private static final String checkstatus = "PartiallyPaid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partially_paid_fee);

        firestore = FirebaseFirestore.getInstance();

        recyclerViewfee = findViewById(R.id.list_partiallyfeestudent);
        recyclerViewfee.setHasFixedSize(true);
        recyclerViewfee.setLayoutManager(new LinearLayoutManager(this));


        fetchfee();


    }

    private void fetchfee() {


        feeArrayList.clear();
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