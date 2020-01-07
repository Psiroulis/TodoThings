package com.redpepper.todothings.ui.shopping_Item;

import android.util.Log;

import com.redpepper.todothings.DataModels.Item;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

class ShoppingItemsPresenter implements ShoppingItemsMVP.Presenter {

    ShoppingItemsMVP.Model model;
    ShoppingItemsMVP.View view;

    CompositeDisposable subscription;

    public ShoppingItemsPresenter(ShoppingItemsMVP.Model model) {

        this.model = model;
        this.subscription = new CompositeDisposable();
    }

    @Override
    public void setView(ShoppingItemsMVP.View view) {
        this.view = view;
    }

    @Override
    public void createNewItem(String categoryId, Item item) {
        subscription.add(model.storeNewItem(categoryId, item)
                .subscribe(
                        itemA -> view.addItemToListView(itemA),
                        throwable -> Log.e("RxFirebaseSample", throwable.toString())
                ));
    }




    @Override
    public void downLoadItems(String categoryId) {

        List<Item> itemsList = new ArrayList<>();

        subscription.add(model.getAllItems(categoryId)
                .subscribe(items -> {

                            for (Item item : items) {
                                Log.d("blepo", "item id = " + item.getId());
                                itemsList.add(item);
                            }

                            view.fillListView(itemsList);

                        },

                        throwable -> Log.e("RxFirebaseSample", throwable.toString())
                ));
    }

    @Override
    public void deleteItem(String categoryId, Item item) {

        subscription.add(model.deleteItem(categoryId,item)
        .subscribe(
                itemA -> Log.d("RxFirebaseSample", "item: " + itemA),
                throwable -> Log.e("RxFirebaseSample", throwable.toString())
        ));
    }

    @Override
    public void updateItem(String categoryId, Item item, int position) {

        subscription.add(model.editItem(categoryId,item)
        .subscribe(
                itemA -> view.editItem(itemA, position),
                throwable -> Log.e("RxFirebaseSample", throwable.toString())
        ));
    }

    @Override
    public void restoreItem(String categoryId, Item item) {
        subscription.add(model.restoreItem(categoryId,item)
                .subscribe(category1 -> {
                            Log.d("RxFirebaseSample", "category: " + item);
                        },throwable -> {
                            Log.e("RxFirebaseSample", throwable.toString());
                        }
                ));
    }

    @Override
    public void rxUnsubscribe() {
        if (subscription != null) {
            if (!subscription.isDisposed()) {
                subscription.dispose();
            }
        }
    }
}
