package com.example.campuslink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    EditText et_name, et_uname, et_email, et_pwd, et_org, et_qual;

    RadioGroup rdg_sex;
    boolean gender;
    CheckBox cb_aiml, cb_bm, cb_uiux, cb_cys, cb_dev, cb_leg, cb_bio, cb_other;

    private DatabaseReference Database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        if(getSupportActionBar() != null) getSupportActionBar().hide();

        et_name = findViewById(R.id.et_name);
        et_uname = findViewById(R.id.et_uname);
        et_email = findViewById(R.id.et_email);
        et_pwd = findViewById(R.id.et_pwd);
        et_org = findViewById(R.id.et_org);
        et_qual = findViewById(R.id.et_qual);

        rdg_sex = findViewById(R.id.rbgrp_sex);
        rdg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = findViewById(i);
                if(rb.getText().equals("Male")) gender = true;
                else gender = false;
            }
        });

        cb_aiml = findViewById(R.id.cb_aiml);
        cb_bm = findViewById(R.id.cb_bm);
        cb_cys = findViewById(R.id.cb_cys);
        cb_uiux = findViewById(R.id.cb_uiux);
        cb_dev = findViewById(R.id.cb_dev);
        cb_leg = findViewById(R.id.cb_leg);
        cb_bio = findViewById(R.id.cb_bio);
        cb_other = findViewById(R.id.cb_other);

        Database = FirebaseDatabase.getInstance("https://campuslink-db-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
    }

    List<String> getExperience()
    {
        List<String> exps = new ArrayList<String>();
        if(cb_aiml.isChecked()) exps.add("AI/ML");
        if(cb_bm.isChecked()) exps.add("Business Management");
        if(cb_cys.isChecked()) exps.add("Cybersecurity");
        if(cb_uiux.isChecked()) exps.add("UI/UX");
        if(cb_dev.isChecked()) exps.add("Development");
        if(cb_leg.isChecked()) exps.add("Legal");
        if(cb_bio.isChecked()) exps.add("Legal");
        if(cb_other.isChecked()) exps.add("Others");

        return exps;
    }
    public void onSubmit(View view)
    {
        // Submit Sign-up form data to backend
        String name = et_name.getText().toString();
        String uname = et_uname.getText().toString();
        String email = et_email.getText().toString();
        String pwd = et_pwd.getText().toString();
        String org = et_org.getText().toString();
        String qual = et_qual.getText().toString();
        List<String> exps = getExperience();

        User user = new User(name,uname,email,pwd,org,qual,gender,exps);

        Database.child("users").child(uname).setValue(user);
        Log.v("SignUpActivity/onSubmit()","Sign-up data sent to backend");
        // Show Toast message denoting sign-up was successful
        Toast.makeText(this, "You have successfully signed up for CampusLink!", Toast.LENGTH_SHORT).show();
        // Navigate to Sign-In Page
        Intent signInIntent = new Intent(getApplicationContext(), SignInActivity.class);
        startActivity(signInIntent);
    }

    public void goSignIn(View view)
    {
        Intent signInIntent = new Intent(getApplicationContext(), SignInActivity.class);
        startActivity(signInIntent);
    }
}