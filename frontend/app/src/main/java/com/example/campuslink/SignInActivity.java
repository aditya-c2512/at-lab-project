package com.example.campuslink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    private EditText et_uname, et_pwd;

    private DatabaseReference Database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        if(getSupportActionBar() != null) getSupportActionBar().hide();

        et_uname = findViewById(R.id.et_cred1);
        et_pwd = findViewById(R.id.et_cred2);

        Database = FirebaseDatabase.getInstance("https://campuslink-db-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
    }

    public void onSignIn(View view)
    {
        String userId = et_uname.getText().toString();
        Database.child("users").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task)
            {
                if (!task.isSuccessful())
                {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else
                {
                    User user = task.getResult().getValue(User.class);
                    if(user != null && user.pwd.equals(et_pwd.getText().toString()))
                    {
                        // User exists
                        Toast.makeText(SignInActivity.this, "User Successfully Signed In", Toast.LENGTH_SHORT).show();
                        Intent dashboardIntent = new Intent(getApplicationContext(), DashboardActivity.class);
                        dashboardIntent.putExtra("userId",user.username);
                        startActivity(dashboardIntent);
                    }
                    else
                    {
                        // User does not exist OR Incorrect Password
                        Toast.makeText(SignInActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        );
    }

    public void goSignUp(View view)
    {
        Intent signUpIntent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(signUpIntent);
    }
}