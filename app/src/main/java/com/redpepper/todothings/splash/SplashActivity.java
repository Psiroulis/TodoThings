package com.redpepper.todothings.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.redpepper.todothings.MainActivity;
import com.redpepper.todothings.R;
import com.redpepper.todothings.login.LoginUserActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(isNetworkAvailable()){

            FirebaseAuth mAuth = FirebaseAuth.getInstance();

            FirebaseUser currentUser = mAuth.getCurrentUser();

            Intent intent;

            if(currentUser != null){

                intent = new Intent(this,MainActivity.class);

            }else{

                intent = new Intent(this, LoginUserActivity.class);

            }

            startActivity(intent);

            SplashActivity.this.finish();

        }

        if(!isNetworkAvailable()){

            //todo: Dialog for not available internet connection

        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
