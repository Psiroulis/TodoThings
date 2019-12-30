package com.redpepper.todothings.ui.shopping_Item;

import com.redpepper.todothings.DataModels.Item;

import java.util.List;

import io.reactivex.Maybe;

interface ShoppingItemsMVPRepository {

    Maybe<List<Item>> getAllCategoryItems(String categoryid);

    Maybe<Item> createItem(String categoryId, String description, int amount);

    Maybe<Item> updateItem(String categoryId, String id, String description, int amount);

    Maybe<Item> deleteCategory(String categoryId, String itemId);

    Maybe<Item> restoreCategory(String categoryId, Item item);



}
