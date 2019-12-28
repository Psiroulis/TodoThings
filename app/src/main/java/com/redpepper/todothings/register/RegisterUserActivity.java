package com.redpepper.todothings.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;


import com.redpepper.todothings.MainActivity;
import com.redpepper.todothings.R;
import com.redpepper.todothings.root.App;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterUserActivity extends AppCompatActivity implements RegisterUserMVP.View{

    @BindView(R.id.registerUSerEmailEdt)
    EditText emailEdt;

    @BindView(R.id.registerUserPasswordEdt)
    EditText passwordEdt;

    @Inject RegisterUserMVP.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        ButterKnife.bind(this);

        ((App) getApplication()).getComponent().inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.setView(this);

        presenter.checkIfUserIsAlreadyRegister();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @OnClick(R.id.registerBtn)
    void registerButtonIsPressed(){
        presenter.createUserWithEmailAndPassword(emailEdt.getText().toString(), passwordEdt.getText().toString());
    }

    @Override
    public void goToMainActivity() {

        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);

        RegisterUserActivity.this.finish();

    }


}
