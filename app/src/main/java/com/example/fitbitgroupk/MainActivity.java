package com.example.fitbitgroupk;

// Default libraries

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        Log.e(TAG, "account =  " + account);
        if(account == null)
            launchLogin();
        else
            launchMainPage(account);

    }//end create

    private void launchMainPage(GoogleSignInAccount account) {
        Log.e(TAG, "launch main page is called");
        Intent intent = new Intent(this, MainPage.class);
        intent.putExtra("ACCOUNT", account);
        startActivity(intent);
    }

    public void launchLogin(){
        Log.e(TAG, "launch login is called");
        Intent intent = new Intent(this, GoogleLogin.class);
        startActivity(intent);
    }
}// end class