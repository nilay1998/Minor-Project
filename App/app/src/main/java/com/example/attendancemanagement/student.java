package com.example.attendancemanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class student extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        TextView textView=findViewById(R.id.oops);
        final Intent intent = new Intent(this, Attendance.class);
        final Bundle extras = getIntent().getExtras();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }
}
