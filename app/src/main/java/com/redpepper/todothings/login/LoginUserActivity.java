package com.redpepper.todothings.login;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import com.redpepper.todothings.MainActivity;
import com.redpepper.todothings.R;
import com.redpepper.todothings.register.RegisterUserActivity;
import com.redpepper.todothings.root.App;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginUserActivity extends AppCompatActivity implements LoginUserMVP.View {

    @BindView(R.id.loginEmailEdtx)
    EditText emailEdt;

    @BindView(R.id.fb_login_Button)
    Button fbLoginBtn;

    @BindView(R.id.loginPasswordEdtx)
    EditText passwordEdt;

    @Inject
    LoginUserMVP.Presenter presenter;

    @Inject
    FirebaseDatabase database;

    @Inject
    FirebaseAuth mAuth;

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        ButterKnife.bind(this);

        ((App) getApplication()).getComponent().inject(this);

        callbackManager = CallbackManager.Factory.create();


        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        AuthCredential credential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());

                        presenter.loginUser(credential);

                    }

                    @Override
                    public void onCancel() {
                        Log.d("blepo", "facebook:onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d("blepo", "facebook:onError", exception);
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.setView(this);

    }

    @OnClick(R.id.loginBtn)
    void loginBtnIsPressed(){

        AuthCredential credential = EmailAuthProvider.getCredential(emailEdt.getText().toString(), passwordEdt.getText().toString());

        presenter.loginUser(credential);

    }

    @OnClick(R.id.fb_login_Button)
    void fbLoginBtnIsPressed(){

        LoginManager.getInstance().logInWithReadPermissions(LoginUserActivity.this, Arrays.asList("email","public_profile","user_friends"));


    }

    @Override
    public void goToMainActivity() {

        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);

        LoginUserActivity.this.finish();
    }

    @Override
    public void goToRegisterUserActivity() {

        Intent intent = new Intent(this, RegisterUserActivity.class);

        startActivity(intent);

        LoginUserActivity.this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }
}
