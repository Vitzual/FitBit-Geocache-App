package com.example.fitbitgroupk;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Goal;
import com.google.android.gms.fitness.request.GoalsReadRequest;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainPage extends AppCompatActivity {

    // Text variables
    protected TextView stepCount;
    protected TextView stepGoal;
    protected TextView welcome;

    // Google account objects
    protected List<Goal> goals;
    protected GoogleSignInAccount account;

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
        // stepCount.setText(goals.get(0).getObjectiveType());
        // stepGoal = something;


    }
}