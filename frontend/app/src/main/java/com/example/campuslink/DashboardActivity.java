package com.example.campuslink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends AppCompatActivity {

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if(getSupportActionBar() != null) getSupportActionBar().hide();

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
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