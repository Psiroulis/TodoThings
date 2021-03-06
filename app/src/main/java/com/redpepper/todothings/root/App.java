package com.redpepper.todothings.root;

import android.app.Application;

import com.redpepper.todothings.login.LoginUserModule;
import com.redpepper.todothings.register.RegisterUserModule;
import com.redpepper.todothings.ui.shopping.ShoppingCategoryModule;
import com.redpepper.todothings.ui.shopping_Item.ShoppingItemsModule;

public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .registerUserModule(new RegisterUserModule())
                .loginUserModule(new LoginUserModule())
                .shoppingCategoryModule(new ShoppingCategoryModule())
                .shoppingItemsModule(new ShoppingItemsModule())
                .build();

        component.inject(this);

    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
