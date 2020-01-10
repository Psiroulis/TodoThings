package com.redpepper.todothings.login;

import com.google.firebase.auth.AuthCredential;

public interface LoginUserMVP {

    interface Model{

    }

    interface View{

        void goToMainActivity();

        void goToRegisterUserActivity();
    }

    interface Presenter{

        void setView(LoginUserMVP.View view);

        void loginUser(AuthCredential credential);
    }
}
