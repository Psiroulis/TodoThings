package com.redpepper.todothings.root;

import android.app.Application;

import com.redpepper.todothings.login.LoginUserActivity;
import com.redpepper.todothings.login.LoginUserModule;
import com.redpepper.todothings.register.RegisterUserActivity;
import com.redpepper.todothings.register.RegisterUserModule;
import com.redpepper.todothings.ui.shopping.CategoryFragment;
import com.redpepper.todothings.ui.shopping.ShoppingCategoryModule;
import com.redpepper.todothings.ui.shopping_Item.ShoppingItemsFragment;
import com.redpepper.todothings.ui.shopping_Item.ShoppingItemsModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        RegisterUserModule.class,
        LoginUserModule.class,
        ShoppingCategoryModule.class,
        ShoppingItemsModule.class
})

public interface ApplicationComponent {

    void inject (Application application);

    void inject (RegisterUserActivity target);

    void inject (LoginUserActivity target);

    void inject (CategoryFragment target);

    void inject (ShoppingItemsFragment target);
}
