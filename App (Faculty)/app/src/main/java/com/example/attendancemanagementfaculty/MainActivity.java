package com.example.attendancemanagementfaculty;

import android.animation.Animator;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendancemanagementfaculty.Reterofit.NetworkClient;
import com.example.attendancemanagementfaculty.Reterofit.Profile;
import com.example.attendancemanagementfaculty.Reterofit.RequestService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    private View login_view;
    private View register_view;
    private ImageView bookIconImageView;
    private TextView bookITextView;
    private ProgressBar loadingProgressBar;
    private RelativeLayout rootView, afterAnimationView;

    @Override
    public void onBackPressed() {
        setContentView(login_view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        login_view=getLayoutInflater().inflate(R.layout.activity_main,null);
        register_view=getLayoutInflater().inflate(R.layout.register,null);
        setContentView(login_view);

        initViews();

        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                bookITextView.setVisibility(GONE);
                loadingProgressBar.setVisibility(GONE);
                rootView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorSplashText));
                bookIconImageView.setImageResource(R.drawable.iiitm);
                startAnimation();
            }
        }.start();

        final Intent intent = new Intent(this, Classes.class);

        Retrofit retrofit = NetworkClient.getRetrofitClient();
        final RequestService requestService=retrofit.create(RequestService.class);

        final ProgressBar progressBar=(ProgressBar)findViewById(R.id.loading_spinner);
        progressBar.setVisibility(View.GONE);
        final EditText edittext_email = (EditText) findViewById(R.id.email);
        final EditText edittext_password = (EditText) findViewById(R.id.password);
        Button button_login = (Button) findViewById(R.id.login);

        button_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                progressBar.setVisibility(View.VISIBLE);
                Call<Profile> call=requestService.loginUser(edittext_email.getText().toString(),edittext_password.getText().toString());
                //Call call=requestService.loginUser("nilaygupta1998@gmail.com","nilay");
                call.enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this,""+response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        if(response.body().getStatus().equals("1"))
                        {
                            intent.putExtra("name",response.body().getName());
                            intent.putExtra("email",response.body().getEmail());
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                        Log.e("Error",t.getMessage());
                    }
                });
            }
        });

        Button button_signup = (Button) findViewById(R.id.signup);
        button_signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                //setContentView(R.layout.register);

                setContentView(register_view);

                final EditText edittext_name = (EditText) findViewById(R.id.nameq);
                final EditText edittext_email = (EditText) findViewById(R.id.remail);
                final EditText edittext_password = (EditText) findViewById(R.id.rpassword);


                Button button_register=(Button) findViewById(R.id.register);
                button_register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        setContentView(login_view);
                        progressBar.setVisibility(View.VISIBLE);
                        Call<Profile> call=requestService.createUser(
                                edittext_name.getText().toString(),
                                edittext_email.getText().toString(),
                                edittext_password.getText().toString());

                        call.enqueue(new Callback<Profile>() {
                            @Override
                            public void onResponse(Call<Profile> call, Response<Profile> response) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this,""+response.body().getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Profile> call, Throwable t) {
                                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }

    private void initViews() {
        bookIconImageView = findViewById(R.id.bookIconImageView);
        bookITextView = findViewById(R.id.bookITextView);
        loadingProgressBar = findViewById(R.id.loadingProgressBar);
        rootView = findViewById(R.id.rootView);
        afterAnimationView = findViewById(R.id.afterAnimationView);
    }


    private void startAnimation() {
        ViewPropertyAnimator viewPropertyAnimator = bookIconImageView.animate();
        viewPropertyAnimator.x(470f);
        viewPropertyAnimator.y(100f);
        viewPropertyAnimator.setDuration(1000);
        viewPropertyAnimator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                afterAnimationView.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
