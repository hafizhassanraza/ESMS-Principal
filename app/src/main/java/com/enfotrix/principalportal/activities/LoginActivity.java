package com.enfotrix.principalportal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.enfotrix.principalportal.R;
import com.enfotrix.principalportal.databinding.ActivityLoginBinding;
import com.enfotrix.principalportal.utilities.Constants;
import com.enfotrix.principalportal.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseFirestore firebaseFirestore;
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        setListeners();


    }

    private void init(){
        firebaseFirestore = FirebaseFirestore.getInstance();
        preferenceManager = new PreferenceManager(this);
    }

    private void setListeners() {
        binding.btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateDetails()){
                    loginUser();
                }
            }
        });
    }

    private void loginUser() {
        loading(true);
        firebaseFirestore.collection(Constants.KEY_COLLECTION_ADMIN)
                .whereEqualTo(Constants.KEY_NAME,binding.etUserName.getText().toString().trim())
                .whereEqualTo(Constants.KEY_PASSWORD,binding.etPassword.getText().toString().trim())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful() && task.getResult()!=null
                                && task.getResult().getDocuments().size() > 0){

                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            preferenceManager.putString(Constants.KEY_NAME, documentSnapshot.getString(Constants.KEY_NAME));
                            preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else{
                            loading(false);
                            Toast.makeText(LoginActivity.this, "Unable To LogIn", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateDetails() {
        if(binding.etUserName.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter User Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(binding.etUserName.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        else return true;
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.btnLogIn.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.btnLogIn.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
}