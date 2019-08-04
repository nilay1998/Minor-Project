package com.example.attendancemanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.attendancemanagement.Reterofit.NetworkClient;
import com.example.attendancemanagement.Reterofit.Profile;
import com.example.attendancemanagement.Reterofit.RequestService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Attendance extends AppCompatActivity {
    int attended;
    int deno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        TextView percent=findViewById(R.id.percent);
        final TextView total=findViewById(R.id.total);
        final TextView attend=findViewById(R.id.attended);


        Bundle extras=getIntent().getExtras();
        String userName=extras.getString("name");
        String userEmail=extras.getString("email");
        String rollNumber=extras.getString("rollNumber");


        Retrofit retrofit = NetworkClient.getRetrofitClient();
        final RequestService requestService=retrofit.create(RequestService.class);
        Call<Profile> call=requestService.reguestId(userEmail);
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                attended=response.body().getAttendance().length;
                attend.append(String.valueOf(attended));
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {

            }
        });

        Call<Profile> call1=requestService.reguestisClass();
        call1.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                int deno=response.body().getClasses();
                total.append(String.valueOf(deno));
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {

            }
        });
    }
}
