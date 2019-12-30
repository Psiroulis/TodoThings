package com.redpepper.todothings.ui.shopping_Item;

class ShoppingItemsModel implements ShoppingItemsMVP.Model {

    ShoppingItemsMVPRepository repository;

    public ShoppingItemsModel(ShoppingItemsMVPRepository repository) {
        this.repository = repository;
    }
}
