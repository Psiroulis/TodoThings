package com.redpepper.todothings.ui.shopping;

class ShoppingCategoryModel implements ShoppingCategoryMVP.Model {

    ShoppingCategoryMVPRepository repository;

    public ShoppingCategoryModel(ShoppingCategoryMVPRepository repository) {

        this.repository = repository;
    }
}
