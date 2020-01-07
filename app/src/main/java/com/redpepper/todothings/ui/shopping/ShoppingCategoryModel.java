package com.redpepper.todothings.ui.shopping;

import com.redpepper.todothings.DataModels.Category;

import java.util.List;

import io.reactivex.Maybe;

class ShoppingCategoryModel implements ShoppingCategoryMVP.Model {

    ShoppingCategoryMVPRepository firebaseRepository;

    public ShoppingCategoryModel(ShoppingCategoryMVPRepository repository) {

        this.firebaseRepository = repository;
    }


    @Override
    public Maybe<Category> storeNewCategory(String name) {
        return this.firebaseRepository.createCategory(name);
    }

    @Override
    public Maybe<Category> editCategory(Category category) {
        return this.firebaseRepository.updateCategory(category);
    }

    @Override
    public Maybe<Category> deleteCategory(Category category) {
        return this.firebaseRepository.deleteCategory(category);
    }

    @Override
    public Maybe<Category> restoreCategory(Category category) {
        return this.firebaseRepository.restoreCategory(category);
    }

    @Override
    public Maybe<List<Category>> getAllCategories() {

        return this.firebaseRepository.getAllCategories();
    }
}
