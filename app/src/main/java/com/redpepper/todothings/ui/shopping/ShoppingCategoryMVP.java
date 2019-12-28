package com.redpepper.todothings.ui.shopping;

import com.redpepper.todothings.DataModels.Category;

import java.util.List;

public interface ShoppingCategoryMVP {

    interface Model {}
    interface View {

        void updateListView(List<Category> itemList);

    }
    interface Presenter {

        void setView(ShoppingCategoryMVP.View view);

        void createNewCategory(String name);

        void downLoadCategories();

        void deleteCategory(String id);
    }

}
