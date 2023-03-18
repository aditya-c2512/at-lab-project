package com.example.campuslink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CollabMailActivity extends AppCompatActivity {

    String userId;
    String toId = "nk_cubing@gmail.com";
    EditText et_pname, et_fname, et_rwork;
    CheckBox cb_aiml, cb_bm, cb_uiux, cb_cys, cb_dev, cb_leg, cb_bio, cb_other;
    private DatabaseReference Database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collab_mail);

        if(getSupportActionBar() != null) getSupportActionBar().hide();

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        toId = intent.getStringExtra("toId");

        et_pname = findViewById(R.id.et_pname);
        et_fname = findViewById(R.id.et_fname);
        et_rwork = findViewById(R.id.et_rwork);

        cb_aiml = findViewById(R.id.cb_aiml3);
        cb_bm = findViewById(R.id.cb_bm3);
        cb_cys = findViewById(R.id.cb_cys3);
        cb_uiux = findViewById(R.id.cb_uiux3);
        cb_dev = findViewById(R.id.cb_dev3);
        cb_leg = findViewById(R.id.cb_leg3);
        cb_bio = findViewById(R.id.cb_bio3);
        cb_other = findViewById(R.id.cb_other3);

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

    public void GenerateMail(String key)
    {
        try {
            URL url = new URL("http://localhost:3000/generate?mail_key="+key);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output;
            while ((output = br.readLine()) != null) {
                Log.d("CollabMailActivity/GenerateMail","output");
            }

            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onSend(View view)
    {
        String project_name = et_pname.getText().toString();
        String fname = et_fname.getText().toString();
        String relevant_work = et_rwork.getText().toString();
        List<String> skills = getDomains();

        CollabMail cmail = new CollabMail(toId, fname, project_name, relevant_work, skills);
        String cmail_key = Database.child("collab").push().getKey();
        Database.child("collab").child(cmail_key).setValue(cmail);

        Database.child("collab_keys").setValue(cmail_key);

//        GenerateMail(cmail_key);

        Toast.makeText(this, "Mail Submitted!", Toast.LENGTH_SHORT).show();
    }
}