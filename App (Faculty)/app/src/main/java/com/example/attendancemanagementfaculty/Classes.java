package com.example.attendancemanagementfaculty;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendancemanagementfaculty.Reterofit.MyPojo;
import com.example.attendancemanagementfaculty.Reterofit.NetworkClient;
import com.example.attendancemanagementfaculty.Reterofit.Profile;
import com.example.attendancemanagementfaculty.Reterofit.RequestService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Classes extends AppCompatActivity {

    public static int numberOfClasses;
    Boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);

        Retrofit retrofit = NetworkClient.getRetrofitClient();
        final RequestService requestService=retrofit.create(RequestService.class);

        final TextView classNumber=findViewById(R.id.classNo);

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

        final Switch switch1=findViewById(R.id.switch1);


        final Call<Profile> call=requestService.reguestisClass();
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if(response.body().getisClass()==true)
                {
                    switch1.setChecked(true);
                    switch1.setText("ON");
                }
                else
                {
                    switch1.setChecked(false);
                    switch1.setText("OFF");
                }
                classNumber.setText(String.valueOf(response.body().getDates().length));
                numberOfClasses=Integer.parseInt(classNumber.getText().toString());
                Log.e("HAHA", "numberOfClasses: " + numberOfClasses);
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {

            }
        });

        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state=switch1.isChecked();
                if(state==true)
                {
                    switch1.setText("ON");
                }
                else
                    switch1.setText("OFF");
                Call<Profile> call1=requestService.updateClass(state);
                call1.enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        Toast.makeText(Classes.this,""+response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        Log.e("HAHA", "onResponse: "+response.body().getMessage() );
                        if(response.body().getStatus().equals("0"))
                        {
                            switch1.setText("OFF");
                            switch1.setChecked(false);
                            state=false;
                            Log.e("HAHA", "onResponse: 1)Class added " +state );
                        }
                        if(state == true)
                        {
                            Call<Profile> call2=requestService.addClass(1);
                            call2.enqueue(new Callback<Profile>() {
                                @Override
                                public void onResponse(Call<Profile> call, Response<Profile> response) {
                                    Log.e("HAHA", "onResponse: 3)Class added " +state);
                                }

                                @Override
                                public void onFailure(Call<Profile> call, Throwable t) {

                                }
                            });
                            classNumber.setText(String.valueOf(Integer.parseInt(classNumber.getText().toString())+1));
                            numberOfClasses=Integer.parseInt(classNumber.getText().toString());
                            Log.e("HAHA", "numberOfClasses: " + numberOfClasses);
                        }
                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {

                    }
                });

                //Log.e("HAHA", "onResponse: 2)Class added " +state);

            }
        });

        final EditText edittext = new EditText(getApplicationContext());
        edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
        classNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edittext.getParent() != null) {
                    ((ViewGroup)edittext.getParent()).removeView(edittext); // <- fix
                }
                edittext.setText(String.valueOf(Integer.parseInt(classNumber.getText().toString())));
                edittext.setSelection(edittext.getText().length());
                edittext.setSelectAllOnFocus(true);
                final AlertDialog.Builder builder = new AlertDialog.Builder(Classes.this);
                builder.setView(edittext,50,0,50,0);
                builder.setMessage("Number of Classes")
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Call<Profile> call3=requestService.addClass(Integer.parseInt(edittext.getText().toString()) - Integer.parseInt(classNumber.getText().toString()));
                                Log.e("HAHA", "numberOfClasses: " + edittext.getText().toString());
                                Log.e("HAHA", "numberOfClasses: " + classNumber.getText().toString());
                                classNumber.setText(String.valueOf(Integer.parseInt(edittext.getText().toString())));
                                numberOfClasses=Integer.parseInt(classNumber.getText().toString());
                                call3.enqueue(new Callback<Profile>() {
                                    @Override
                                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                                        Log.e("HAHA", "onResponse: Class added" +response.body().getMessage());
                                    }

                                    @Override
                                    public void onFailure(Call<Profile> call, Throwable t) {

                                    }
                                });
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                // Create the AlertDialog object and return it
                builder.show();
            }
        });
    }
}
