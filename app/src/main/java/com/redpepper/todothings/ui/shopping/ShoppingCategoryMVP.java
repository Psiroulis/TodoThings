package com.redpepper.todothings.ui.shopping;

import com.redpepper.todothings.DataModels.Category;

import java.util.List;

import io.reactivex.Maybe;

public interface ShoppingCategoryMVP {

    interface Model {

        Maybe<List<Category>> getAllCategories();

        Maybe<Category> storeNewCategory(String name);

        Maybe<Category> editCategory(String id, String name);

        Maybe<Category> deleteCategory(String id);

        Maybe<Category> restoreCategory(Category category);

    }
    interface View {

        void fillListView(List<Category> itemList);
        void addItemToListView(Category category);
        void editItem(Category category, int position);

    }
    interface Presenter {

        void setView(ShoppingCategoryMVP.View view);

        void createNewCategory(String name);

        void downLoadCategories();

        void deleteCategory(String id);

        void updateCategory(String id, int position, String name);

        void restoreCategory(Category category);

        void rxUnsubscribe();
    }

}
