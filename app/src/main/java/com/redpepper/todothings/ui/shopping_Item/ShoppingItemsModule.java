package com.redpepper.todothings.ui.shopping_Item;

import com.google.firebase.database.FirebaseDatabase;

import dagger.Module;
import dagger.Provides;

@Module
public class ShoppingItemsModule {

    @Provides
    public ShoppingItemsMVP.Presenter providePresenter(ShoppingItemsMVP.Model model){
        return new ShoppingItemsPresenter(model);
    }

    @Provides
    public ShoppingItemsMVP.Model provideModel(ShoppingItemsMVPRepository repository){
        return new ShoppingItemsModel(repository);
    }

    @Provides
    public ShoppingItemsMVPRepository provideRepository(FirebaseDatabase firebaseDatabase){

        return new FirebaseRepository(firebaseDatabase);

    }
}
