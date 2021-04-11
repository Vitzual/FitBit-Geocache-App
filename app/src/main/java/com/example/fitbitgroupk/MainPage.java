package com.example.fitbitgroupk;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Goal;
import com.google.android.gms.fitness.request.GoalsReadRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainPage extends AppCompatActivity implements OnMapReadyCallback {

    // Text variables
    protected TextView stepCount;
    protected TextView stepGoal;
    protected TextView welcome;

    // Google account objects
    protected List<Goal> goals;
    protected GoogleSignInAccount account;

    // Google map object and fragment manager
    private GoogleMap mMap;
    private FragmentManager mFragmentManager;

    private String TAG = "MAIN_PAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        // Grab account object
        account = getIntent().getParcelableExtra("ACCOUNT");
        welcome = findViewById((R.id.accountName));
        welcome.setText(account.getDisplayName());

        // Fitness sign in options
        GoogleSignInOptionsExtension fitnessOptions =
                FitnessOptions.builder()
                        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                        .addDataType(DataType.TYPE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
                        .build();

        account = GoogleSignIn.getAccountForExtension(this, fitnessOptions);

        final Task<List<Goal>> response = Fitness.getGoalsClient(this, account)
                .readCurrentGoals(new GoalsReadRequest.Builder()
                        .addDataType(DataType.TYPE_STEP_COUNT_DELTA)
                        .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                        .build());

        // Create a new thread to request data
        new Thread(new Runnable() {
            @Override

            public void run() {
                try {
                    goals = Tasks.await(response);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();

        // Grab textView objects by ID
        stepCount = findViewById(R.id.stepsToday);
        stepGoal = findViewById(R.id.stepsRemaining);


        // Set textView objects
        // stepCount.setText(goals.get(0).toString());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map); // This returns null, resulting in the map not being loaded
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        setupLayout();

        mFragmentManager = getSupportFragmentManager();
    }

    private void setupLayout() { }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(50.66987170679699, -120.36521149873413);
        mMap.addMarker(new MarkerOptions().position(sydney).title("TRU"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}