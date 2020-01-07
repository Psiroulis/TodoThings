package com.redpepper.todothings.ui.shopping;

import android.app.AlertDialog;
import android.content.Context;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.material.snackbar.Snackbar;
import com.redpepper.todothings.DataModels.Category;
import com.redpepper.todothings.R;

import com.redpepper.todothings.root.App;
import com.redpepper.todothings.ui.shopping.listAdapter.CategoryRecyclerViewAdapter;
import com.redpepper.todothings.ui.shopping.listAdapter.RecyclerItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryFragment extends Fragment implements ShoppingCategoryMVP.View, CategoryRecyclerViewAdapter.RecyclerViewClickListener,RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{

    @BindView(R.id.categoryList)
    RecyclerView listView;

    @Inject
    ShoppingCategoryMVP.Presenter presenter;

    @Inject
    Context context;

    CategoryRecyclerViewAdapter mAdapter;

    List<Category> categoryList;

    private static final int CREATE_CATEGORY = 1;

    private static final int EDIT_CATEGORY = 2;


    public CategoryFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category_list, container, false);

        ButterKnife.bind(this,root);

        FloatingActionButton fab = root.findViewById(R.id.categoryListfab);

        fab.setOnClickListener(view -> showAlertDialog(CREATE_CATEGORY,-1));

        return root;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ((App) context.getApplicationContext()).getComponent().inject(this);

//        presenter.downLoadCategories();

//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        presenter.rxUnsubscribe();

    }

    @Override
    public void fillListView(List<Category> itemList) {

       categoryList.clear();
       categoryList.addAll(itemList);
       this.mAdapter.notifyDataSetChanged();
    }

    @Override
    public void addItemToListView(Category category) {
        categoryList.add(category);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void editItem(Category category, int position) {

        categoryList.set(position,category);

        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if(viewHolder instanceof CategoryRecyclerViewAdapter.ViewHolder){

            String name = categoryList.get(viewHolder.getAdapterPosition()).getName();

            final Category deletedItem = categoryList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            presenter.deleteCategory(categoryList.get(position));

            mAdapter.deleteItem(viewHolder.getAdapterPosition());

            Snackbar snackbar = Snackbar
                    .make(getActivity().findViewById(android.R.id.content), name + " removed from cart!", Snackbar.LENGTH_LONG);

            snackbar.setAction("UNDO",view -> {

                mAdapter.restoreItem(deletedItem, deletedIndex);

                presenter.restoreCategory(deletedItem);

            });

            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();

        }

    }


    private void showAlertDialog(int type, int position){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View dialogView = inflater.inflate(R.layout.add_category_dialog_layout,null);

        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();

        TextView titleTxt = dialogView.findViewById(R.id.addCategoryDialogTitleTxt);

        EditText nameEdt = dialogView.findViewById(R.id.addCategoryDialogNameEdt);

        Button cancelBtn = dialogView.findViewById(R.id.addcategorycancelbtn);

        Button submitBtn = dialogView.findViewById(R.id.addCategorySubmitBtn);


        if(type == CREATE_CATEGORY){

            titleTxt.setText("Add New Category");

        }else if(type == EDIT_CATEGORY){

            titleTxt.setText("Edit ACategory");

        }

        cancelBtn.setOnClickListener(view -> alertDialog.dismiss());

        submitBtn.setOnClickListener(view -> {

           if(type == CREATE_CATEGORY){

               presenter.createNewCategory(nameEdt.getText().toString());

           }else if(type == EDIT_CATEGORY){

               Category category = categoryList.get(position);

               category.setName(nameEdt.getText().toString());

               presenter.updateCategory(category,position);

           }

           alertDialog.dismiss();

       });

       alertDialog.setView(dialogView);
       alertDialog.show();
    }


    //Adapter Interface Methods
    @Override
    public void recyclerViewListClicked(View v, int position) {

        Category category = categoryList.get(position);

//        NavDirections action = HomeFragmentDirections.goToShoppingCategory();
//        Navigation.findNavController(view).navigate(action);

       NavDirections action = CategoryFragmentDirections.goToCategoryItems(category.getId());
        Navigation.findNavController(v).navigate(action);

    }

    @Override
    public void recyclerViewItemEditClicked(String id, int position) {

        showAlertDialog(EDIT_CATEGORY,position);
    }

    //    public interface OnListFragmentInteractionListener {
//    }


    @Override
    public void onResume() {
        super.onResume();

        presenter.setView(this);

        categoryList = new ArrayList<>();

        mAdapter = new CategoryRecyclerViewAdapter(categoryList,this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        listView.setLayoutManager(layoutManager);

        listView.setItemAnimator(new DefaultItemAnimator());

        listView.addItemDecoration(new DividerItemDecoration(context,LinearLayoutManager.VERTICAL));

        listView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback itemtouchhelpercallback = new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);

        new ItemTouchHelper((itemtouchhelpercallback)).attachToRecyclerView(listView);

        presenter.downLoadCategories();
    }
}
