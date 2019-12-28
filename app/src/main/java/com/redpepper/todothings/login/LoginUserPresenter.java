package com.redpepper.todothings.login;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

class LoginUserPresenter implements LoginUserMVP.Presenter {

    LoginUserMVP.Model model;
    LoginUserMVP.View view;

    FirebaseAuth mAuth;

    private static final String TAG = "LoginUserPresenter";

    public LoginUserPresenter(FirebaseAuth firebaseAuth, LoginUserMVP.Model model) {

        this.mAuth = firebaseAuth;
        this.model = model;
    }


    @Override
    public void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            view.goToMainActivity();

                        }else{

                            Log.d(TAG, "LoginUserWithEmail:failure", task.getException());

                            view.goToRegisterUserActivity();
                        }
                    }
                });
    }

    @Override
    public void setView(LoginUserMVP.View view) {
        this.view = view;
    }
}
