package com.redpepper.todothings.ui.shopping_Item;

class ShoppingItemsPresenter implements ShoppingItemsMVP.Presenter {

    ShoppingItemsMVP.Model model;
    ShoppingItemsMVP.View view;

    public ShoppingItemsPresenter(ShoppingItemsMVP.Model model) {

        this.model = model;

    }

    @Override
    public void setView(ShoppingItemsMVP.View view) {
        this.view = view;
    }
}
