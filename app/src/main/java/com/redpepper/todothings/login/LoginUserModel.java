package com.redpepper.todothings.login;

class LoginUserModel implements LoginUserMVP.Model {

    LoginUserMVPRepository repository;

    public LoginUserModel(LoginUserMVPRepository repository) {
        this.repository = repository;
    }
}
