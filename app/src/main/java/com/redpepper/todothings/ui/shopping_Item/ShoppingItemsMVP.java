package com.redpepper.todothings.ui.shopping_Item;

public interface ShoppingItemsMVP {

    interface Model{}

    interface View{}

    interface Presenter{
        void setView(ShoppingItemsMVP.View view);
    }

}
