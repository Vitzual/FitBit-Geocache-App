package com.example.fitbitgroupk;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class MainPage extends AppCompatActivity {

    // Text variables
    protected TextView stepCount;
    protected TextView stepGoal;
    protected TextView welcome;

    GoogleSignInAccount account;

    private String TAG = "MAIN_PAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        // Grab account object
        account = getIntent().getParcelableExtra("ACCOUNT");

        // Grab textView objects by ID
        stepCount = findViewById(R.id.stepsToday);
        stepGoal = findViewById(R.id.stepsRemaining);
        welcome = findViewById((R.id.accountName));

        // Set textView objects
        // stepCount = something;
        // stepGoal = something;
        welcome.setText(account.getDisplayName());

    }
}