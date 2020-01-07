package com.redpepper.todothings.ui.shopping;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

import com.redpepper.todothings.DataModels.Category;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

class ShoppingCategoryPresenter implements ShoppingCategoryMVP.Presenter {

    ShoppingCategoryMVP.Model model;
    ShoppingCategoryMVP.View view;

    CompositeDisposable subscription;

    public ShoppingCategoryPresenter(FirebaseDatabase firebaseDatabase, ShoppingCategoryMVP.Model model) {

        this.model = model;

        this.subscription = new CompositeDisposable();
    }

    @Override
    public void setView(ShoppingCategoryMVP.View view) {
        this.view = view;
    }

    @Override
    public void createNewCategory(String name) {

        subscription.add(model.storeNewCategory(name)
                .subscribe(category -> {
                            view.addItemToListView(category);
                        }, throwable -> {
                            Log.e("RxFirebaseSample", throwable.toString());
                        }

                ));
    }

    @Override
    public void downLoadCategories() {

        List<Category> categoryList = new ArrayList<>();

        subscription.add(model.getAllCategories()
                .subscribe(categories -> {

                    for (Category category : categories) {
                        categoryList.add(category);
                    }

                    view.fillListView(categoryList);

                }, throwable -> {
                    Log.e("RxFirebaseSample", throwable.toString());
                }));

    }

    @Override
    public void updateCategory(Category category, int position) {

        subscription.add(model.editCategory(category)
                .subscribe(newCategory -> {
                            view.editItem(newCategory, position);

                        }, throwable -> {

                            Log.e("RxFirebaseSample", throwable.toString());

                        }

                ));
    }

    @Override
    public void deleteCategory(Category category) {

        subscription.add(model.deleteCategory(category)
                .subscribe(category1 -> {
                            Log.d("RxFirebaseSample", "category: " + category1);
                        }, throwable -> {
                            Log.e("RxFirebaseSample", throwable.toString());
                        }
                ));
    }

    @Override
    public void restoreCategory(Category category) {

        subscription.add(model.restoreCategory(category)
        .subscribe(category1 -> {
                    Log.d("RxFirebaseSample", "category: " + category);
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


