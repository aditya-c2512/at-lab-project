package com.example.campuslink;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class NetworkActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        listView=findViewById(R.id.listview);

        //data entries
        ArrayList<Person> arrayList=new ArrayList<>();

        arrayList.add(new Person(R.drawable.user,"Palak Dhawan"));
        arrayList.add(new Person(R.drawable.user,"Riddhi sharma"));
        arrayList.add(new Person(R.drawable.user,"Aditya Choubey"));
        arrayList.add(new Person(R.drawable.user,"Palak Dhawan"));

        //making custom adapter
        PersonAdapter personAdapter=new PersonAdapter(this,R.layout.list_row,arrayList);

        listView.setAdapter(personAdapter);

    }
}