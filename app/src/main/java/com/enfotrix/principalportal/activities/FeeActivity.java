package com.enfotrix.principalportal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.principalportal.R;
import com.enfotrix.principalportal.adapters.Adapter_Fee;
import com.enfotrix.principalportal.databinding.ActivityFeeBinding;
import com.enfotrix.principalportal.models.Fee;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FeeActivity extends AppCompatActivity {

    ActivityFeeBinding binding;
    private FirebaseFirestore firebaseFirestore;
    int dueAmount, receivedAmount;


    List<Fee> feeArrayList = new ArrayList<>();
    RecyclerView recyclerViewfee;
    Adapter_Fee adapter_fee;
    int pendingamount;
    int approvedamount, partiallyamount, partiallypamount;
    private TextView tv_totalmonth, tv_totalcollection, tv_totalpending, tv_totalpartiallypaid;
    Animation animation;
    RelativeLayout lay_Tcollection, lay_Tpending;
    ImageView img_tcollection, img_tpending, img_tpartiallypaid;
    private int grandTotalRecived, grandtotalPending;

    private TextView txt_totol_full_Recived, txt_totol_partially_Recived, txt_grandTotal, txt_totol_full_Pending, txt_totol_partially_pending, txt_grandTotalpending;
    private TextView txt_detail_feeRecevied, txt_detail_partialy_feeRecevied, txt_detail_ful_feepending, txt_detail_partialy_feepending;

    private int treceive;
    private String receive, pendingfee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseFirestore = FirebaseFirestore.getInstance();

        grandTotalRecived = 0;
        grandtotalPending = 0;
        txt_detail_feeRecevied = findViewById(R.id.txt_detail_feeRecevied);
        txt_detail_partialy_feeRecevied = findViewById(R.id.txt_detail_partialy_feeRecevied);
        txt_detail_ful_feepending = findViewById(R.id.txt_detail_ful_feepending);
        txt_detail_partialy_feepending = findViewById(R.id.txt_detail_partialy_feepending);
        txt_totol_full_Recived = findViewById(R.id.txt_totol_full_Recived);
        txt_totol_partially_Recived = findViewById(R.id.txt_totol_partially_Recived);
        txt_grandTotal = findViewById(R.id.txt_grandTotal);
        txt_totol_full_Pending = findViewById(R.id.txt_totol_full_Pending);
        txt_totol_partially_pending = findViewById(R.id.txt_totol_partially_pending);
        txt_grandTotalpending = findViewById(R.id.txt_grandTotalpending);

        Button btn_transactionDetails = findViewById(R.id.btn_transactionDetails);
        btn_transactionDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FeeActivity.this, FeeDetails.class);
                intent.putExtra("month", getIntent().getStringExtra("month"));
                startActivity(intent);
            }
        });


        txt_detail_feeRecevied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(FeeActivity.this, TotalFeeReceived.class);
                intent.putExtra("month", getIntent().getStringExtra("month"));
                startActivity(intent);

            }
        });
        txt_detail_partialy_feeRecevied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FeeActivity.this, PartiallyPaidFee.class);
                intent.putExtra("month", getIntent().getStringExtra("month"));
                startActivity(intent);


            }
        });
        txt_detail_ful_feepending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(FeeActivity.this, FullFeePending.class);
                intent.putExtra("month", getIntent().getStringExtra("month"));
                startActivity(intent);
            }
        });
        txt_detail_partialy_feepending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FeeActivity.this, PartiallyPaidFee.class);
                intent.putExtra("month", getIntent().getStringExtra("month"));
                startActivity(intent);

            }
        });

        String session = getIntent().getStringExtra("sessions");
        String month = getIntent().getStringExtra("month");


//        Toast.makeText(this, month + "\n" + session, Toast.LENGTH_SHORT).show();

        getmonthfee(getIntent().getStringExtra("month"), getIntent().getStringExtra("sessions"));


    }

    private void getmonthfee(String month, String sessions) {


        pendingamount = 0;
        approvedamount = 0;
        partiallyamount = 0;
        partiallypamount = 0;
        treceive = 0;
//        feeArrayList.clear();

        firebaseFirestore.collection("Fee").whereEqualTo("Month", month).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

//                            Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        if (document.getString("Status").equals("Unpaid")) {

                            String amount = document.getString("PayableAmount");
                            int fee = Integer.parseInt(amount);
                            pendingamount = pendingamount + fee;


//                                    Toast.makeText(getApplicationContext(), "pending " + pendingamount, Toast.LENGTH_SHORT).show();

                        }

                        if (document.getString("Status").equals("Paid")) {

                            String amount = document.getString("ReceivedAmount");
                            int fee = Integer.parseInt(amount);


                            approvedamount = approvedamount + fee;


                        }

                        if (document.getString("Status").equals("PartiallyPaid")) {

                            receive = document.getString("ReceivedAmount");
                            pendingfee = document.getString("PayableAmount");


                            if (receive != null) {
                                int fee = Integer.parseInt(receive);
                                int pfee = Integer.parseInt(pendingfee);
                                pfee = pfee - fee;
                                partiallyamount = partiallyamount + fee;
                                partiallypamount = partiallypamount + pfee;
                            }

                        }

                    }

                    String totalpending = String.valueOf(pendingamount);
                    String totalapproved = String.valueOf(approvedamount);


                    /////////////////PENDING///////////////////////////
                    ///////////////////////////////////////////////////////////////////////////////////

                    ///approvedamount// full recived fee
                    txt_totol_full_Recived.setText(approvedamount + "/-PKR");
                    // partiallyamount // partially recived amount
                    txt_totol_partially_Recived.setText(partiallyamount + "/-PKR");
                    grandTotalRecived = grandTotalRecived + (approvedamount + partiallyamount);
                    txt_grandTotal.setText(grandTotalRecived + "/-PKR");


                    /////////////////PENDING///////////////////////////
                    ///////////////////////////////////////////////////////////////////////////////////

                    //pendingamount /// full fee pending variable
                    txt_totol_full_Pending.setText(pendingamount + "/-PKR");
                    txt_totol_partially_pending.setText(partiallypamount + "/-PKR");
                    grandtotalPending = grandtotalPending + (pendingamount + partiallypamount);
                    txt_grandTotalpending.setText(grandtotalPending + "/-PKR");
                            /*if (receive != null) {
                                String partialamount = String.valueOf(partiallyamount);
                                tv_totalpartiallypaid.setText(partialamount);
                            } else {
                                tv_totalpartiallypaid.setText("N.A");
                            }*/

//                            Model_Fee model_fee = new Model_Fee();
//                            model_fee.setMonth(month);
//                            model_fee.setCollection(totalapproved);
//                            model_fee.setPending(totalpending);
//                            feeArrayList.add(model_fee);
//
//                            adapter_fee = new Adapter_Fee(getApplicationContext(), feeArrayList);
//                            recyclerViewfee.setAdapter(adapter_fee);
//                            adapter_fee.notifyDataSetChanged();

                }
            }
        });

    }


}
