package com.example.attendancemanagementfaculty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

        final Switch switch1=findViewById(R.id.switch1);

        Retrofit retrofit = NetworkClient.getRetrofitClient();
        final RequestService requestService=retrofit.create(RequestService.class);
        final Call<Profile> call=requestService.reguestisClass();
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if(response.body().getisClass()==true)
                    switch1.setChecked(true);
                else
                    switch1.setChecked(false);
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
                }
            }
        });
    }
}
