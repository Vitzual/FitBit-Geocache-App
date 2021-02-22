package com.example.fitbitgroupk;

// Default libraries

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

// Google sign in library shit

public class MainActivity extends AppCompatActivity {

    // Declare each members buttons
    private Button loginButton;
    private GoogleSignInClient gsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account == null)
            launchLogin();


    }//end create

    public void launchLogin(){
        Intent intent = new Intent(this, GoogleLogin.class);
        startActivity(intent);
    }
}// end class