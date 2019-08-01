package com.example.attendancemanagementfaculty;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.attendancemanagementfaculty.Reterofit.MyPojo;
import com.example.attendancemanagementfaculty.Reterofit.NetworkClient;
import com.example.attendancemanagementfaculty.Reterofit.Profile;
import com.example.attendancemanagementfaculty.Reterofit.RequestService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Classes extends AppCompatActivity {

    public int numberOfClasses;

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
                    switch1.setChecked(true);
                else
                    switch1.setChecked(false);
                classNumber.setText(String.valueOf(response.body().getClasses()));
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
                Boolean state=switch1.isChecked();
                Call<Profile> call1=requestService.updateClass(state);
                call1.enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        Log.e("HAHA", "onResponse: "+response.body().getMessage() );
                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {

                    }
                });

                if(state == true)
                {
                    Call<Profile> call2=requestService.addClass(1);
                    call2.enqueue(new Callback<Profile>() {
                        @Override
                        public void onResponse(Call<Profile> call, Response<Profile> response) {
                            Log.e("HAHA", "onResponse: Class added" );
                        }

                        @Override
                        public void onFailure(Call<Profile> call, Throwable t) {

                        }
                    });
                    classNumber.setText(String.valueOf(Integer.parseInt(classNumber.getText().toString())+1));
                    numberOfClasses=Integer.parseInt(classNumber.getText().toString())+1;
                    Log.e("HAHA", "numberOfClasses: " + numberOfClasses);
                }
            }
        });

        final EditText edittext = new EditText(getApplicationContext());
        classNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittext.setText(String.valueOf(Integer.parseInt(classNumber.getText().toString())));
                edittext.setSelection(edittext.getText().length());
                edittext.setSelectAllOnFocus(true);
                AlertDialog.Builder builder = new AlertDialog.Builder(Classes.this);
                builder.setView(edittext,50,0,50,0);
                builder.setMessage("Number of Classes")
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                classNumber.setText(String.valueOf(Integer.parseInt(edittext.getText().toString())));
                                numberOfClasses=Integer.parseInt(classNumber.getText().toString());
                                Log.e("HAHA", "numberOfClasses: " + numberOfClasses);
                                Call<Profile> call3=requestService.addClass(Integer.parseInt(edittext.getText().toString()) -Integer.parseInt(classNumber.getText().toString()));
                                call3.enqueue(new Callback<Profile>() {
                                    @Override
                                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                                        Log.e("HAHA", "onResponse: Class added" );
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
                builder.show()  ;
            }
        });
    }
}
