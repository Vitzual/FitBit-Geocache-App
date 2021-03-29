package com.example.fitbitgroupk;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class MainPage extends AppCompatActivity {

    // Step text
    TextView stepsToday;
    TextView stepsGoal;
    TextView signedInAs;

    // Google account object
    GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        // Grab the account that was last logged in
        account = getIntent().getParcelableExtra("ACCOUNT");

        // Set step text
        signedInAs.setText(account.getDisplayName());
    }


}