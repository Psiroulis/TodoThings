package com.redpepper.todothings.ui.shopping_Item;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.redpepper.todothings.DataModels.Item;

import java.util.List;

import durdinapps.rxfirebase2.DataSnapshotMapper;
import durdinapps.rxfirebase2.RxFirebaseDatabase;
import io.reactivex.Maybe;

class FirebaseRepository implements ShoppingItemsMVPRepository {

    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    DatabaseReference databaseReference;

    public FirebaseRepository(FirebaseDatabase firebaseDatabase) {

        this.user = FirebaseAuth.getInstance().getCurrentUser();

        this.firebaseDatabase = firebaseDatabase;

        this.databaseReference  = this.firebaseDatabase.getReference("categories").child("shopping").child(user.getUid());

    }


    @Override
    public Maybe<Item> createItem(String categoryId, String description, int amount) {

        String id = databaseReference.child(categoryId).push().getKey();

        Item item = new Item(id, description,amount);

        databaseReference.child(categoryId).child(id).setValue(item);

        return RxFirebaseDatabase.observeSingleValueEvent(databaseReference.child(categoryId).child(id), Item.class);

    }

    @Override
    public Maybe<Item> updateItem(String categoryId, String id, String description, int amount) {

        Item item = new Item(id,description,amount);

        databaseReference.child(categoryId).child(id).setValue(item);

        return RxFirebaseDatabase.observeSingleValueEvent(databaseReference.child(id), Item.class);
    }

    @Override
    public Maybe<Item> deleteCategory(String categoryId, String itemId) {

        databaseReference.child(categoryId).child(itemId).removeValue();

        return RxFirebaseDatabase.observeSingleValueEvent(databaseReference.child(categoryId).child(itemId), Item.class);
    }

    @Override
    public Maybe<Item> restoreCategory(String categoryId, Item item) {

        databaseReference.child(categoryId).child(item.getId()).setValue(item);

        return RxFirebaseDatabase.observeSingleValueEvent(databaseReference.child(categoryId).child(item.getId()), Item.class);
    }

    @Override
    public Maybe<List<Item>> getAllCategoryItems(String categoryid) {
        return RxFirebaseDatabase.observeSingleValueEvent(databaseReference.child(categoryid), DataSnapshotMapper.listOf(Item.class));
    }


}
