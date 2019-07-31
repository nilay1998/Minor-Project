package com.example.attendancemanagementfaculty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.attendancemanagementfaculty.Reterofit.Attendance;

public class Classes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);

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
