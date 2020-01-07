package com.redpepper.todothings.ui.shopping_Item;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.redpepper.todothings.DataModels.Item;
import com.redpepper.todothings.R;
import com.redpepper.todothings.root.App;
import com.redpepper.todothings.ui.shopping_Item.listAdapter.ItemRecyclerItemTouchHelper;
import com.redpepper.todothings.ui.shopping_Item.listAdapter.ItemRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingItemsFragment extends Fragment implements
        ShoppingItemsMVP.View ,
        ItemRecyclerViewAdapter.ItemRecyclerViewClickListener,
        ItemRecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private String category_id;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.itemList)
    RecyclerView listView;

    @Inject
    ShoppingItemsMVP.Presenter presenter;

    @Inject
    Context context;

    private static final int CREATE_ITEM = 1;

    private static final int EDIT_ITEM = 2;

    ItemRecyclerViewAdapter mAdapter;

    List<Item> itemsListA;

    public ShoppingItemsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            category_id = ShoppingItemsFragmentArgs.fromBundle(getArguments()).getCategoryId();

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_shopping_items_list, container, false);

        ButterKnife.bind(this,root);

        FloatingActionButton fab = root.findViewById(R.id.itemListfab);

        fab.setOnClickListener(view -> showAlertDialog(CREATE_ITEM,null,-1));

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ((App) context.getApplicationContext()).getComponent().inject(this);

//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

        presenter.rxUnsubscribe();
    }

    @Override
    public void fillListView(List<Item> itemsList) {

        itemsListA.clear();
        itemsListA.addAll(itemsList);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void addItemToListView(Item item) {

        itemsListA.add(item);

        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void editItem(Item item, int posiiton) {

        itemsListA.set(posiiton,item);

        mAdapter.notifyDataSetChanged();

    }
//
//    @Override
//    public void editItemChecked(Item item, int posiiton) {
//
//    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if(viewHolder instanceof ItemRecyclerViewAdapter.ViewHolder){

            String description = itemsListA.get(viewHolder.getAdapterPosition()).getDescription();

            final Item deletedItem = itemsListA.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            presenter.deleteItem(category_id,deletedItem);

            mAdapter.deleteItem(viewHolder.getAdapterPosition());

            Snackbar snackbar = Snackbar
                    .make(getActivity().findViewById(android.R.id.content), description + " removed from cart!", Snackbar.LENGTH_LONG);

            snackbar.setAction("UNDO",view -> {

                mAdapter.restoreItem(deletedItem, deletedIndex);

                presenter.restoreItem(category_id, deletedItem);

            });

            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();

        }

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {

    }

    @Override
    public void recyclerViewItemEditClicked(String id, int position) {

        showAlertDialog(EDIT_ITEM,id,position);
    }

    @Override
    public void recyclerViewItemCheckedChange(int position, boolean checked) {

        Item item = itemsListA.get(position);

        item.setChecked(checked);


        presenter.updateItem(category_id,item,position);
    }

    private void showAlertDialog(int type, String id, int position){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View dialogView = inflater.inflate(R.layout.add_item_dialog_layout,null);

        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();

        TextView titleTxt = dialogView.findViewById(R.id.add_item_dialog_title_textview);

        EditText nameEdt = dialogView.findViewById(R.id.add_item_dialog_description_edittext);

        Button cancelBtn = dialogView.findViewById(R.id.add_itemDialog_cancel_button);

        Button submitBtn = dialogView.findViewById(R.id.add_itemDialog_submit_button);

        Button counterMinusBtn = dialogView.findViewById(R.id.add_item_dialog_counter_minus_button);

        Button counterPlusBtn = dialogView.findViewById(R.id.add_item_dialog_counter_plus_button);

        TextView counterAmountEdt = dialogView.findViewById(R.id.add_item_dialog_counter_textview);

        AtomicInteger amount = new AtomicInteger(1);


        if(position >= 0){

            amount.set(itemsListA.get(position).getAmount());

            counterAmountEdt.setText(Integer.toString(amount.get()));

            nameEdt.setText(itemsListA.get(position).getDescription());


        }else{

            counterAmountEdt.setText(Integer.toString(amount.get()));

        }

        if(type == CREATE_ITEM){

            titleTxt.setText("Add New Item");

        }else if(type == EDIT_ITEM){

            titleTxt.setText("Edit An Item");

        }

        counterMinusBtn.setOnClickListener(view->{

            if(amount.get() > 1 ){

                amount.addAndGet(-1);

                counterAmountEdt.setText(Integer.toString(amount.get()));
            }

        });

        counterPlusBtn.setOnClickListener(view->{

            amount.addAndGet(1);

            counterAmountEdt.setText(Integer.toString(amount.get()));
        });

        cancelBtn.setOnClickListener(view -> alertDialog.dismiss());

        submitBtn.setOnClickListener(view -> {

            if(type == CREATE_ITEM){

                Item item = new Item();

                item.setDescription(nameEdt.getText().toString().toUpperCase());

                item.setAmount(Integer.valueOf(counterAmountEdt.getText().toString()));

                item.setChecked(false);

                presenter.createNewItem(category_id,item);

            }else if(type == EDIT_ITEM){

                Item itemToEdit = itemsListA.get(position);

                itemToEdit.setDescription(nameEdt.getText().toString().toUpperCase());

                itemToEdit.setAmount(Integer.valueOf(counterAmountEdt.getText().toString()));

                presenter.updateItem(category_id, itemToEdit, position);

            }

            alertDialog.dismiss();

        });

        alertDialog.setView(dialogView);
        alertDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.setView(this);

        itemsListA = new ArrayList<>();

        mAdapter = new ItemRecyclerViewAdapter(itemsListA,this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        listView.setLayoutManager(layoutManager);

        listView.setItemAnimator(new DefaultItemAnimator());

        listView.addItemDecoration(new DividerItemDecoration(context,LinearLayoutManager.VERTICAL));

        listView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback itemtouchhelpercallback = new ItemRecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);

        new ItemTouchHelper((itemtouchhelpercallback)).attachToRecyclerView(listView);

        presenter.downLoadItems(category_id);
    }
}
