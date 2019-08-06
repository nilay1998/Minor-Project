package com.example.attendancemanagementfaculty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.attendancemanagementfaculty.R;
import com.example.attendancemanagementfaculty.Reterofit.AllItems;
import com.example.attendancemanagementfaculty.Reterofit.MyPojo;
import com.example.attendancemanagementfaculty.Reterofit.NetworkClient;
import com.example.attendancemanagementfaculty.Reterofit.RequestService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Attendance extends AppCompatActivity {

    ArrayList<AllItems> arrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        final RecyclerView recyclerView=findViewById(R.id.recyclerView);

        Retrofit retrofit = NetworkClient.getRetrofitClient();
        final RequestService requestService=retrofit.create(RequestService.class);
        Call<MyPojo> call=requestService.getStudents();
        call.enqueue(new Callback<MyPojo>() {
            @Override
            public void onResponse(Call<MyPojo> call, Response<MyPojo> response) {
                if(response.body().getStatus().equals("1"))
                {
                    arrayList= new ArrayList<>(Arrays.asList(response.body().getAllItems()));
                    Collections.sort(arrayList,AllItems.StuRollno);
                    final RecyclerViewAdapter recyclerViewAdapter=new RecyclerViewAdapter(arrayList);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
            }

            @Override
            public void onFailure(Call<MyPojo> call, Throwable t) {
                Toast.makeText(Attendance.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.e("Error",t.getMessage());
            }
        });
    }
}
