package com.example.fitbitgroupk;

// Default libraries
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

// Google sign in library shit

public class GoogleLogin extends AppCompatActivity {

    // Declare each members buttons
    private Button loginButton;
    private GoogleSignInClient gsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a new google sign in object (GSI)
        // This is where we'd add Google Fit permission requests
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsi = GoogleSignIn.getClient(this, gso);

        // Set the loginButton
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionCheck();
            }
        });
    }

    // Permission check method
    public void permissionCheck() {

        // Generate a sign in request with designated intents
        Intent signInIntent = gsi.getSignInIntent();
        startActivityForResult(signInIntent, 12345);

        // do shit with it (still unsure about this part)

    }
}