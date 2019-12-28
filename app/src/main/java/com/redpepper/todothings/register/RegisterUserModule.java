package com.redpepper.todothings.register;

import com.google.firebase.auth.FirebaseAuth;

import dagger.Module;
import dagger.Provides;

@Module
public class RegisterUserModule {

    @Provides
    public RegisterUserMVP.Presenter providePresenter(FirebaseAuth firebaseauth, RegisterUserMVP.Model model){
        return new RegisterUserPresenter(firebaseauth, model);
    }

    @Provides
    public RegisterUserMVP.Model provideModel(RegisterUserMVPRepository repository){
        return new RegisterUserModel(repository);
    }

    @Provides
    public RegisterUserMVPRepository provideReposirory(){
        return new RegisterUserRepository();
    }
}
