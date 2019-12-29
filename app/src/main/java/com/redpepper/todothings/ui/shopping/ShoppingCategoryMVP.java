package com.redpepper.todothings.ui.shopping;

import com.redpepper.todothings.DataModels.Category;

import java.util.List;

public interface ShoppingCategoryMVP {

    interface Model {}
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
    }

}
