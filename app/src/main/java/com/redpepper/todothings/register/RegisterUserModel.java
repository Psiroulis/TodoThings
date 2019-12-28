package com.redpepper.todothings.register;

class RegisterUserModel implements RegisterUserMVP.Model {

    RegisterUserMVPRepository repository;

    public RegisterUserModel(RegisterUserMVPRepository repository) {

        this.repository = repository;
    }
}
