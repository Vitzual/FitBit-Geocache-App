package com.example.fitbitgroupk;

// Default libraries

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

// Core component's (namely for permissions)
// Google authentication components

// Login class
public class GoogleLogin extends AppCompatActivity {

    // Declare login button
    private Button loginButton;

    // Some good shit right here
    private GoogleSignInClient gsi;
    GoogleSignInAccount account;
    private String TAG = "GoogleLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "Google Login on create starts");
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
                googleSignIn();
            }
        });
    }

    // Generate a sign in request with designated intents
    public void googleSignIn() {
        Intent signInIntent = gsi.getSignInIntent();
        startActivityForResult(signInIntent, 12345);
    }

    // Permission check method
    // 12345 = Google Account RQC
    // 123 = Android Activity RQC
    public void permissionCheck() {

        // Check that the permission from manifest file was met
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Missing required permissions!", Toast.LENGTH_LONG).show();

            // Request for the required permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                    123);
        } else {
            gotoProfile();
        }

    }

    // Method override that gets called when the user either accepts or denies a permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        // Code Analysis might hate this, but we need this for the future when more permissions may
        // need to be requested and accessed
        switch (requestCode) {
            case 123:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    gotoProfile();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
        }
    }

    // After signing in, store information or return a sign in failure message
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==12345){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result); // It's ok if it's null
        }
    }

    // Determine if the sign in was successful
    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            account = result.getSignInAccount(); // Store account details
            permissionCheck();
        }else{
            Toast.makeText(getApplicationContext(),"Please sign in again!",Toast.LENGTH_LONG).show();
        }
    }

    // Gives activities permission to stored credentials
    private void gotoProfile(){

        // Start activity
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}