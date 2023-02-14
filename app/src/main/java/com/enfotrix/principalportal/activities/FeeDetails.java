package com.enfotrix.principalportal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.enfotrix.principalportal.adapters.Adapter_Fee;
import com.enfotrix.principalportal.databinding.ActivityFeeDetailsBinding;
import com.enfotrix.principalportal.models.Fee;
import com.enfotrix.principalportal.models.Model_AmountDetail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FeeDetails extends AppCompatActivity {

    private FirebaseFirestore firestore;

    private String tempDate = "0/0/0";

    private String month = "01";
    private String date;
    private int tempSum = 0;
    ActivityFeeDetailsBinding binding;

    List<Model_AmountDetail> list_details = new ArrayList<Model_AmountDetail>();
    RecyclerView rec_transactionDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeeDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firestore = FirebaseFirestore.getInstance();


        getFee();


    }

    private void getFee() {
        firestore.collection("Fee").whereEqualTo("Month", getIntent().getStringExtra("month")).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()) {

                                                   for (int i = 1; i < 32; i++) {

                                                       String tempday;
                                                       if (i < 10)
                                                           tempday = "0" + i;

                                                       else tempday = "" + i;

                                                       tempSum = 0;
                                                       for (QueryDocumentSnapshot document : task.getResult()) {

                                                           if (document.getString("Status").equals("Paid") || document.getString("Status").equals("PartiallyPaid")) {

                                                               String tempdate = document.getString("DateCollect");
                                                               if (tempdate != null) {
                                                                   String day = tempdate.substring(0, 2);
                                                                   //Toast.makeText(FeeDetails.this, day, Toast.LENGTH_SHORT).show();

                                                                   if (day.equals(tempday)) {
                                                                       //Toast.makeText(Activity_Fee_Details.this, day+" "+tempday, Toast.LENGTH_SHORT).show();
                                                                       String amount = document.getString("ReceivedAmount");
                                                                       int fee = Integer.parseInt(amount);


                                                                       tempSum = tempSum + fee;
                                                                       date = tempdate;

                                                                       //  Toast.makeText(FeeDetails.this, tempSum+"", Toast.LENGTH_SHORT).show();
                                                                   }
                                                               }


                                                           }

                                                       }

                                                       /// one day income for one itration

                                                       if (tempSum != 0) {

                                                           binding.txtAmount.setText(tempSum+"PKR");
                                                           binding.txtTransectionDate.setText(date);
                                                       }


                                                   }


                                               }
                                           }
                                       }
                );


    }
}