package com.redpepper.todothings.ui.shopping;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
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
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.material.snackbar.Snackbar;
import com.redpepper.todothings.DataModels.Category;
import com.redpepper.todothings.R;

import com.redpepper.todothings.root.App;
import com.redpepper.todothings.ui.shopping.listAdapter.CategoryRecyclerViewAdapter;
import com.redpepper.todothings.ui.shopping.listAdapter.RecyclerItemTouchHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryFragment extends Fragment implements ShoppingCategoryMVP.View, CategoryRecyclerViewAdapter.RecyclerViewClickListener,RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{

    @BindView(R.id.dialogLayout)
    ConstraintLayout dialogLayout;

    @BindView(R.id.blackWindow)
    RelativeLayout black;

    @BindView(R.id.addCategoryDialogNameEdt)
    EditText nameDilogEdt;

    @BindView(R.id.addcategorycancelbtn)
    Button cancelDialogBtn;

    @BindView(R.id.addCategorySubmitBtn)
    Button submitDialogBtn;

    @BindView(R.id.categoryList)
    RecyclerView listView;

    @Inject
    ShoppingCategoryMVP.Presenter presenter;

    CategoryRecyclerViewAdapter mAdapter;

    List<Category> categoryList;

    public CategoryFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter.setView(this);

        presenter.downLoadCategories();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category_list, container, false);

        ButterKnife.bind(this,root);

        cancelDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                black.setVisibility(View.GONE);
                dialogLayout.setVisibility(View.GONE);
                nameDilogEdt.setText("");

            }
        });

        submitDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.createNewCategory(nameDilogEdt.getText().toString());

                black.setVisibility(View.GONE);
                dialogLayout.setVisibility(View.GONE);
                nameDilogEdt.setText("");



            }
        });

        FloatingActionButton fab = root.findViewById(R.id.categoryListfab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                black.setVisibility(View.VISIBLE);
                dialogLayout.setVisibility(View.VISIBLE);

            }
        });

        return root;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ((App) context.getApplicationContext()).getComponent().inject(this);


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

    }


    @Override
    public void updateListView(List<Category> itemList) {

        categoryList = itemList;

        mAdapter = new CategoryRecyclerViewAdapter(categoryList,this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        listView.setLayoutManager(layoutManager);

        listView.setItemAnimator(new DefaultItemAnimator());

        listView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));

        listView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback itemtouchhelpercallback = new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);

        new ItemTouchHelper((itemtouchhelpercallback)).attachToRecyclerView(listView);

        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void recyclerViewListClicked(View v, int position) {

        presenter.deleteCategory(categoryList.get(position).getId());
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if(viewHolder instanceof CategoryRecyclerViewAdapter.ViewHolder){

            // get the removed item name to display it in snack bar
            String name = categoryList.get(viewHolder.getAdapterPosition()).getName();

            // backup of removed item for undo purpose
            final Category deletedItem = categoryList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            mAdapter.deleteItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
//            Snackbar snackbar = Snackbar
//                    .make(, name + " removed from cart!", Snackbar.LENGTH_LONG);
//            snackbar.setAction("UNDO", new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    // undo is selected, restore the deleted item
//                    mAdapter.restoreItem(deletedItem, deletedIndex);
//                }
//            });
//            snackbar.setActionTextColor(Color.YELLOW);
//            snackbar.show();


        }
    }

    //    public interface OnListFragmentInteractionListener {
//    }
}
