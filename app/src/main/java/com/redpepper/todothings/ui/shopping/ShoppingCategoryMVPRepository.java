package com.redpepper.todothings.ui.shopping;

import com.redpepper.todothings.DataModels.Category;

import java.util.List;

import io.reactivex.Maybe;

interface ShoppingCategoryMVPRepository {

    Maybe<Category> createCategory(String name);
    Maybe<Category> updateCategory(Category category);
    Maybe<Category> deleteCategory(Category category);
    Maybe<Category> restoreCategory(Category category);

    Maybe<List<Category>> getAllCategories();
}
