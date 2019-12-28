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
    }

    @Override
    public void downLoadCategories() {

        List<Category> categoryList = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                categoryList.clear();

                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()){

                    Category category = categorySnapshot.getValue(Category.class);

                    categoryList.add(category);
                }

                view.updateListView(categoryList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void deleteCategory(String id) {
        databaseReference.child(id).removeValue();
    }
}


