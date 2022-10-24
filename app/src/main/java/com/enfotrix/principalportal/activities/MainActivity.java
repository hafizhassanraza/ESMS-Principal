package com.enfotrix.principalportal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.enfotrix.principalportal.R;
import com.enfotrix.principalportal.databinding.ActivityMainBinding;
import com.enfotrix.principalportal.utilities.Constants;
import com.enfotrix.principalportal.utilities.PreferenceManager;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        preferenceManager = new PreferenceManager(this);
        setContentView(binding.getRoot());

        setPreferredInfo();
        setListeners();
    }

    private void setPreferredInfo() {
        binding.actionBarLayout.tvActionBar.setText(preferenceManager.getString(Constants.KEY_NAME).toUpperCase(Locale.ROOT));
    }

    private void setListeners() {
        binding.actionBarLayout.btnLogOut.setOnClickListener(view -> {
            preferenceManager.clear();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        });
    }
}