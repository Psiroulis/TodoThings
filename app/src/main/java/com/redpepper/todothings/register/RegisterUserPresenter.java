package com.redpepper.todothings.register;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

class RegisterUserPresenter implements RegisterUserMVP.Presenter {

    RegisterUserMVP.Model model;
    RegisterUserMVP.View view;

    FirebaseAuth mAuth;

    private static final String TAG = "RegisterUserPresenter";

    public RegisterUserPresenter(FirebaseAuth firebaseAuth, RegisterUserMVP.Model model) {

        this.model = model;

        mAuth = firebaseAuth;

    }

    @Override
    public void setView(RegisterUserMVP.View view) {
        this.view = view;
    }

    @Override
    public void checkIfUserIsAlreadyRegister() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){

            view.goToMainActivity();
        }
    }

    @Override
    public void createUserWithEmailAndPassword(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            view.goToMainActivity();

                        }else{

                            Log.d(TAG, "createUserWithEmail:failure", task.getException());
                        }
                    }
                });

        }

    @Override
    public void signOutUser() {
        mAuth.signOut();
    }
}


