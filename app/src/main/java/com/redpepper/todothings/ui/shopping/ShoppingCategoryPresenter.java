package com.redpepper.todothings.ui.shopping;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.redpepper.todothings.DataModels.Category;

import java.util.ArrayList;
import java.util.List;

class ShoppingCategoryPresenter implements ShoppingCategoryMVP.Presenter {

    ShoppingCategoryMVP.Model model;
    ShoppingCategoryMVP.View view;
    FirebaseDatabase firebaseDatabase;

    FirebaseUser user;
    DatabaseReference databaseReference;

    public ShoppingCategoryPresenter(FirebaseDatabase firebaseDatabase,ShoppingCategoryMVP.Model model) {
        this.firebaseDatabase = firebaseDatabase;
        this.model = model;

        this.user = FirebaseAuth.getInstance().getCurrentUser();
        this.databaseReference  = this.firebaseDatabase.getReference("categories").child("shopping").child(user.getUid());
    }

    @Override
    public void setView(ShoppingCategoryMVP.View view) {
        this.view = view;
    }

    @Override
    public void createNewCategory(String name) {

        String id = databaseReference.push().getKey();

        Category category = new Category(id,name.toUpperCase());

        databaseReference.child(id).setValue(category);

        view.addItemToListView(category);
    }

    @Override
    public void downLoadCategories() {

        List<Category> categoryList = new ArrayList<>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()){

                    Category category = categorySnapshot.getValue(Category.class);

                    categoryList.add(category);
                }

                view.fillListView(categoryList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void updateCategory(String id, int position, String name) {

        Category category = new Category(id,name.toUpperCase());

        databaseReference.child(id).setValue(category);

        view.editItem(category, position);
    }

    @Override
    public void deleteCategory(String id) {
        databaseReference.child(id).removeValue();
    }

    @Override
    public void restoreCategory(Category category) {
        databaseReference.child(category.getId()).setValue(category);
    }
}


