package com.redpepper.todothings.ui.shopping;

import com.google.firebase.database.FirebaseDatabase;

import dagger.Module;
import dagger.Provides;

@Module
public class ShoppingCategoryModule {

    @Provides
    public ShoppingCategoryMVP.Presenter providePresenter(FirebaseDatabase firebaseDatabase,ShoppingCategoryMVP.Model model){
        return new ShoppingCategoryPresenter(firebaseDatabase, model);
    }

    @Provides
    public ShoppingCategoryMVP.Model provideModel(ShoppingCategoryMVPRepository repository){
        return new ShoppingCategoryModel(repository);
    }

    @Provides
    public ShoppingCategoryMVPRepository provideRepository(){
        return new ShoppingCategoryRepository();
    }
}
