package com.example.attendancemanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Attendance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        Bundle extras=getIntent().getExtras();
        String userName=extras.getString("name");
        String userEmail=extras.getString("email");
        String rollNumber=extras.getString("rollNumber");
    }
}
