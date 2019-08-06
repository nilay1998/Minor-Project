package com.example.attendancemanagement;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
    String arr[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        final TextView percent=findViewById(R.id.percent);
        final TextView total=findViewById(R.id.total);
        final TextView attend=findViewById(R.id.attended);
        final LinearLayout linearLayout=findViewById(R.id.scroll);


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
                arr=response.body().getAttendance();
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
                deno=response.body().getDates().length;
                total.append(String.valueOf(deno));
                double per=((double)attended/(double)deno)*100;
                if(String.valueOf(per).length()>5)
                    percent.append(String.valueOf(per).substring(0,5)+"%");
                else
                    percent.append(String.valueOf(per)+"%");
                for (int i=0;i<response.body().getDates().length;i++)
                {
                    LinearLayout parent = new LinearLayout(getApplicationContext());
                    parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    parent.setOrientation(LinearLayout.HORIZONTAL);

                    TextView date=new TextView(getApplicationContext());
                    TextView pa=new TextView(getApplicationContext());
                    date.setTextColor(Color.BLACK);
                    if(i<9)
                        date.setText(" "+(i+1)+":  ");
                    else
                        date.setText(""+(i+1)+": ");
                    date.append(response.body().getDates()[i]);
                    if(search(response.body().getDates()[i],arr)==-1)
                    {
                        pa.setCompoundDrawablesWithIntrinsicBounds(R.drawable.circle_red,0,0,0);
                        pa.setText("  Absent");
                    }
                    else
                    {
                        pa.setCompoundDrawablesWithIntrinsicBounds(R.drawable.circle_blue,0,0,0);
                        pa.setText("  Present");
                    }
                    date.setId(i);
                    parent.setPadding(28,16,0,16);
                    date.setTextSize(32);
                    date.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                    date.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                    pa.setId(i);
                    pa.setTextColor(Color.BLACK);
                    pa.setPadding(140,0,0,0);
                    pa.setTextSize(32);
                    pa.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                    pa.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    View v = new View(getApplicationContext());
                    v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,5));
                    v.setBackgroundColor(Color.parseColor("#B3B3B3"));
                    parent.addView(date);
                    parent.addView(pa);
                    linearLayout.addView(parent);
                    linearLayout.addView(v);
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {

            }
        });
    }

    int search(String val, String arr[])
    {
        int index = -1;
        for (int i=0;i<arr.length;i++) {
            if (arr[i].equals(val)) {
                index = i;
                break;
            }
        }
        return index;
    }
}
