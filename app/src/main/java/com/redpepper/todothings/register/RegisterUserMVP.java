package com.redpepper.todothings.register;

public interface RegisterUserMVP {

    interface Model{}

    interface View{
        void goToMainActivity();
    }

    interface Presenter{

        void checkIfUserIsAlreadyRegister();
        void createUserWithEmailAndPassword(String email, String password);
        void setView(RegisterUserMVP.View view);
        void signOutUser();
    }
}
