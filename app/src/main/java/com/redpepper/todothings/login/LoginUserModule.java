package com.redpepper.todothings.login;

import com.google.firebase.auth.FirebaseAuth;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginUserModule {

    @Provides
    public LoginUserMVP.Presenter providePresenter(FirebaseAuth firebaseAuth,LoginUserMVP.Model model){
        return new LoginUserPresenter(firebaseAuth,model);
    }

    @Provides
    public LoginUserMVP.Model provideModel(LoginUserMVPRepository repository){
        return new LoginUserModel(repository);
    }

    @Provides
    public LoginUserMVPRepository provideRepository(){
        return new LoginUserRepository();
    }
}
