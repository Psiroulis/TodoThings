package com.redpepper.todothings.ui.shopping_Item;

import com.redpepper.todothings.DataModels.Item;

import java.util.List;

import io.reactivex.Maybe;

public interface ShoppingItemsMVP {

    interface Model {

        Maybe<Item> storeNewItem(String categoryId, Item item);

        Maybe<Item> editItem(String categoryId, Item item);

        Maybe<Item> deleteItem(String categoryId, Item item);

        Maybe<Item> restoreItem(String categoryId, Item item);

        Maybe<List<Item>> getAllItems(String categoryId);

    }

    interface View {

        void fillListView(List<Item> itemsList);
        void addItemToListView(Item item);
        void editItem(Item item, int position);

    }

    interface Presenter {

        void setView(ShoppingItemsMVP.View view);

        void createNewItem(String categoryId, Item item);

        void downLoadItems(String categoryId);

        void deleteItem(String categoryId, Item item);

        void updateItem(String categoryId, Item item, int position);

        void restoreItem(String categoryId, Item item);

        void rxUnsubscribe();
    }

}
