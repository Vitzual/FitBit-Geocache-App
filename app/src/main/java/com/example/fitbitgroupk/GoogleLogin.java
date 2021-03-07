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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

// Core component's (namely for permissions)
// Google authentication components

// Login class
public class GoogleLogin extends AppCompatActivity {

    // Declare login button
    private Button loginButton;

    // Some good shit right here
    private GoogleSignInClient gsi;
    private GoogleSignInAccount account;
    private String TAG = "GoogleLogin";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "Google Login on create starts");
        // Create a new google sign in object (GSI)
        // This is where we'd add Google Fit permission requests


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) //default_web_client string is our Web application type client ID is your backend server's OAuth 2.0 client ID
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

        mAuth = FirebaseAuth.getInstance(); //creates a variable of the FirebaseAuth object using getInstance()
    }

    //This method is a shortcut if there is already a user signed in it will return the user, otherwise it is null
    //Not currently using it to do anything but we can implement it later.
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //if (currentUser != null)
        //        passUserToMain(currentUser); etc ??
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
        if (requestCode == 12345) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                //As of right here i can access the google account
                Log.d(TAG, "User: \t"+ account.getGivenName()) ;
                Log.d(TAG, "firebaseAuthWithGoogle: " + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.w(TAG, "Google Sign in Failed - Rob you loser"); //personal motivation to keep me going
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            //the user variable is what we can use to access the Firebase user, Not sure about accessing it as a Google account yet though
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d(TAG, "Email:\t" + user.getEmail() + "\nName:\t" + user.getDisplayName() + "\nPhone:\t" + user.getPhoneNumber());

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }
    //Doing nothing yet
    // Gives activities permission to stored credentials
    private void gotoProfile() {

        // Start activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}//end class
