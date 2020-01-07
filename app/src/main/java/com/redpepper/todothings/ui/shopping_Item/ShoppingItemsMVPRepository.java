package com.redpepper.todothings.ui.shopping_Item;

import com.redpepper.todothings.DataModels.Item;

import java.util.List;

import io.reactivex.Maybe;

interface ShoppingItemsMVPRepository {

    Maybe<List<Item>> getAllCategoryItems(String categoryid);

    Maybe<Item> createItem(String categoryId, Item item);

    Maybe<Item> updateItem(String categoryId, Item item);

    Maybe<Item> deleteItem(String categoryId, Item item);

    Maybe<Item> restoreItem(String categoryId, Item item);

}
