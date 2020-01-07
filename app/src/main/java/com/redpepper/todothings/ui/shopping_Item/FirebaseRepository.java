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

        this.databaseReference  = this.firebaseDatabase.getReference("lists").child(user.getUid()).child("items");

    }

    @Override
    public Maybe<Item> createItem(String categoryId, Item item) {

        String id = databaseReference.child(categoryId).push().getKey();

        item.setId(id);

        databaseReference.child(categoryId).child(id).setValue(item);

        return RxFirebaseDatabase.observeSingleValueEvent(databaseReference.child(categoryId).child(id), Item.class);

    }

    @Override
    public Maybe<Item> updateItem(String categoryId, Item item) {

        databaseReference.child(categoryId).child(item.getId()).setValue(item);

        return RxFirebaseDatabase.observeSingleValueEvent(databaseReference.child(categoryId).child(item.getId()), Item.class);
    }

    @Override
    public Maybe<Item> deleteItem(String categoryId, Item item) {

        databaseReference.child(categoryId).child(item.getId()).removeValue();


        return RxFirebaseDatabase.observeSingleValueEvent(databaseReference.child(categoryId).child(item.getId()), Item.class);
    }

    @Override
    public Maybe<Item> restoreItem(String categoryId, Item item) {

        databaseReference.child(categoryId).child(item.getId()).setValue(item);

        return RxFirebaseDatabase.observeSingleValueEvent(databaseReference.child(categoryId).child(item.getId()), Item.class);
    }

    @Override
    public Maybe<List<Item>> getAllCategoryItems(String categoryid) {
        return RxFirebaseDatabase.observeSingleValueEvent(databaseReference.child(categoryid), DataSnapshotMapper.listOf(Item.class));


    }

}
