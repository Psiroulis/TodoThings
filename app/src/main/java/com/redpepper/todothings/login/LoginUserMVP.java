package com.redpepper.todothings.login;

public interface LoginUserMVP {

    interface Model{

    }

    interface View{

        void goToMainActivity();

        void goToRegisterUserActivity();
    }

    interface Presenter{

        void setView(LoginUserMVP.View view);

        void loginUser(String email, String password);
    }
}
