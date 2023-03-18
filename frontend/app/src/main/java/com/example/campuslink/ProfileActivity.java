package com.example.campuslink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {
    private String userId;
    TextView tv_name, tv_org;

    private DatabaseReference Database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if(getSupportActionBar() != null) getSupportActionBar().hide();

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        tv_name = findViewById(R.id.user_name);
        tv_org = findViewById(R.id.tv_org);

        Database = FirebaseDatabase.getInstance("https://campuslink-db-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        Database.child("users").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful())
                {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else
                {
                    User user = task.getResult().getValue(User.class);
                    tv_name.setText(user.full_name);
                    tv_org.setText(user.org);
                }
            }
        });
    }

    public void goCollab(View view)
    {
        Intent collabIntent = new Intent(getApplicationContext(), CollabMailActivity.class);
        startActivity(collabIntent);
    }

    public void goHome(View view)
    {
        Intent dashboardIntent = new Intent(getApplicationContext(), DashboardActivity.class);
        dashboardIntent.putExtra("userId",userId);
        startActivity(dashboardIntent);
    }

    public void goNetwork(View view)
    {
        Intent networkIntent = new Intent(getApplicationContext(), NetworkActivity.class);
        networkIntent.putExtra("userId",userId);
        startActivity(networkIntent);
    }

    public void goAddPost(View view)
    {
        Intent addPostIntent = new Intent(getApplicationContext(), AddPostActivity.class);
        addPostIntent.putExtra("userId",userId);
        startActivity(addPostIntent);
    }

    public void goProfile(View view)
    {
        Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
        profileIntent.putExtra("userId",userId);
        startActivity(profileIntent);
    }
}