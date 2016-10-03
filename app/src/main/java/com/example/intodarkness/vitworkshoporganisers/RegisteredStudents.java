package com.example.intodarkness.vitworkshoporganisers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisteredStudents extends AppCompatActivity {
    public static ListView listView;
    public ArrayList<String> rollNoHere = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_students);
        //System.out.println("aavunundo");
        listView = (ListView) findViewById(R.id.registeredStudents);

    }
}
