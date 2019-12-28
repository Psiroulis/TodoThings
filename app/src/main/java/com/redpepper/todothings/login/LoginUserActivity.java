package com.redpepper.todothings.login;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

import com.redpepper.todothings.MainActivity;
import com.redpepper.todothings.R;
import com.redpepper.todothings.register.RegisterUserActivity;
import com.redpepper.todothings.root.App;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginUserActivity extends AppCompatActivity implements LoginUserMVP.View {

    @BindView(R.id.loginEmailEdtx)
    EditText emailEdt;

    @BindView(R.id.loginPasswordEdtx)
    EditText passwordEdt;

    @Inject
    LoginUserMVP.Presenter presenter;

    @Inject
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        ButterKnife.bind(this);

        ((App) getApplication()).getComponent().inject(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.setView(this);
    }

    @OnClick(R.id.loginBtn)
    void loginBtnIsPressed(){

        presenter.loginUser(emailEdt.getText().toString(), passwordEdt.getText().toString());

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
}
