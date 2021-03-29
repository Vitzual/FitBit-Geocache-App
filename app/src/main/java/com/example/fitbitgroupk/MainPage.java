package com.example.fitbitgroupk;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class MainPage extends AppCompatActivity {

    private String TAG = "MAIN_PAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        GoogleSignInAccount account =getIntent().getParcelableExtra("ACCOUNT");
        String personGivenName = account.getGivenName();
        String personFamilyName = account.getFamilyName();
        String personEmail = account.getEmail();
        String personId = account.getId();
        Uri personPhoto = account.getPhotoUrl();


        //
        Log.e(TAG, "MainPage Running");
        Log.e(TAG, personFamilyName.toString());
        Log.e(TAG, personGivenName.toString());
        Log.e(TAG, personEmail.toString());
        Log.e(TAG, personId.toString());

    }
}