package com.example.campuslink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddPostActivity extends AppCompatActivity {

    String userId = "admin";
    EditText et_proj, et_desc, et_dur;
    RadioGroup rdg_stat;
    boolean stat;
    CheckBox cb_aiml, cb_bm, cb_uiux, cb_cys, cb_dev, cb_leg, cb_bio, cb_other;
    private DatabaseReference Database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        if(getSupportActionBar() != null) getSupportActionBar().hide();

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        et_proj = findViewById(R.id.et_proj);
        et_desc = findViewById(R.id.et_desc);
        et_dur = findViewById(R.id.et_duration);

        rdg_stat = findViewById(R.id.rdgrp_stat);
        rdg_stat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = findViewById(i);
                if(rb.getText().equals("Completed")) stat = true;
                else stat = false;
            }
        });

        cb_aiml = findViewById(R.id.cb_aiml2);
        cb_bm = findViewById(R.id.cb_bm2);
        cb_cys = findViewById(R.id.cb_cys2);
        cb_uiux = findViewById(R.id.cb_uiux2);
        cb_dev = findViewById(R.id.cb_dev2);
        cb_leg = findViewById(R.id.cb_leg2);
        cb_bio = findViewById(R.id.cb_bio2);
        cb_other = findViewById(R.id.cb_other2);

        Database = FirebaseDatabase.getInstance("https://campuslink-db-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
    }

    List<String> getDomains()
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
    public void onPost(View view)
    {
        String project_name = et_proj.getText().toString();
        String desc = et_desc.getText().toString();
        int dur = Integer.parseInt(et_dur.getText().toString());
        List<String> dom = getDomains();

        ProjectPost projectPost = new ProjectPost(userId,project_name,desc,stat,dom,dur);

        String project_key = Database.child("posts").push().getKey();
        Database.child("posts").child(project_key).setValue(projectPost);
        Toast.makeText(this, "Project Posted!", Toast.LENGTH_SHORT).show();
    }
}