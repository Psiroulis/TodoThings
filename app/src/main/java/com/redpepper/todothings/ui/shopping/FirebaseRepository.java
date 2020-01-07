package com.redpepper.todothings.ui.shopping;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.redpepper.todothings.DataModels.Category;

import java.util.List;

import durdinapps.rxfirebase2.DataSnapshotMapper;
import durdinapps.rxfirebase2.RxFirebaseDatabase;
import io.reactivex.Maybe;

class FirebaseRepository implements ShoppingCategoryMVPRepository {

    FirebaseDatabase firebaseDatabase;

    FirebaseUser user;
    DatabaseReference databaseReference;

    public FirebaseRepository(FirebaseDatabase firebaseDatabase) {

        this.user = FirebaseAuth.getInstance().getCurrentUser();

        this.firebaseDatabase = firebaseDatabase;

        this.databaseReference  = this.firebaseDatabase.getReference("lists").child(user.getUid()).child("categories");
    }

    @Override
    public Maybe<Category> createCategory(String name) {

        String id = databaseReference.push().getKey();

        Category category = new Category(id,name.toUpperCase());

        databaseReference.child(id).setValue(category);

        return RxFirebaseDatabase.observeSingleValueEvent(databaseReference.child(id), Category.class);

    }

    @Override
    public Maybe<Category> updateCategory(Category category) {

        //Category category = new Category(category.getId(), name.toUpperCase());

        databaseReference.child(category.getId()).setValue(category);

        return RxFirebaseDatabase.observeSingleValueEvent(databaseReference.child(category.getId()), Category.class);
    }

    @Override
    public Maybe<Category> deleteCategory(Category category) {

        databaseReference.child(category.getId()).removeValue();

        return RxFirebaseDatabase.observeSingleValueEvent(databaseReference.child(category.getId()), Category.class);
    }

    @Override
    public Maybe<Category> restoreCategory(Category category) {

        databaseReference.child(category.getId()).setValue(category);

        return RxFirebaseDatabase.observeSingleValueEvent(databaseReference.child(category.getId()), Category.class);
    }

    @Override
    public Maybe<List<Category>> getAllCategories() {

        return RxFirebaseDatabase.observeSingleValueEvent(databaseReference, DataSnapshotMapper.listOf(Category.class));

    }
}
