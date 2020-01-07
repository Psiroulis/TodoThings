package com.redpepper.todothings.ui.shopping_Item;

import com.redpepper.todothings.DataModels.Item;

import java.util.List;

import io.reactivex.Maybe;

class ShoppingItemsModel implements ShoppingItemsMVP.Model {

    ShoppingItemsMVPRepository firebaseRepository;

    public ShoppingItemsModel(ShoppingItemsMVPRepository repository) {
        this.firebaseRepository = repository;
    }

    @Override
    public Maybe<Item> storeNewItem(String categoryId, Item item) {
        return this.firebaseRepository.createItem(categoryId, item);
    }

    @Override
    public Maybe<Item> editItem(String categoryId, Item item) {
        return this.firebaseRepository.updateItem(categoryId,item);
    }

    @Override
    public Maybe<Item> deleteItem(String categoryId, Item item) {
        return this.firebaseRepository.deleteItem(categoryId,item);
    }

    @Override
    public Maybe<Item> restoreItem(String categoryId, Item item) {
        return this.firebaseRepository.restoreItem(categoryId,item);
    }

    @Override
    public Maybe<List<Item>> getAllItems(String categoryId) {

        return this.firebaseRepository.getAllCategoryItems(categoryId);
    }


}
