package com.redpepper.todothings.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        Intent intent = null;

        if(currentUser != null){

            intent = new Intent(this,MainActivity.class);

        }else{

            intent = new Intent(this, LoginUserActivity.class);

        }

        startActivity(intent);

        SplashActivity.this.finish();

    }
}
