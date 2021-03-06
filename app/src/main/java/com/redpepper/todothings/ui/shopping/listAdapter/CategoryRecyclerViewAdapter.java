package com.redpepper.todothings.ui.shopping.listAdapter;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.redpepper.todothings.DataModels.Category;
import com.redpepper.todothings.R;


import java.util.List;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {

    private final List<Category> categoryList;
    private static RecyclerViewClickListener itemListener;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView categoryId;
        public TextView categoryName;
        public RelativeLayout viewBackground, viewForeground;
        public ImageButton editBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            categoryId = itemView.findViewById(R.id.categoryItemId);
            categoryName = itemView.findViewById(R.id.categoryItemName);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
            editBtn = itemView.findViewById(R.id.categoryItemEditButton);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            itemListener.recyclerViewListClicked(itemView,this.getLayoutPosition());

        }
    }

    public CategoryRecyclerViewAdapter(List<Category> items, RecyclerViewClickListener listener) {
        this.categoryList = items;
        this.itemListener = listener;
    }

    @Override
    public CategoryRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_category, parent, false);

        this.context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CategoryRecyclerViewAdapter.ViewHolder holder, int position) {

        Category category = categoryList.get(position);

        holder.categoryId.setText(category.getId());
        holder.categoryName.setText(category.getName());
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.recyclerViewItemEditClicked(category.getId(), position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public interface RecyclerViewClickListener{
        void recyclerViewListClicked(View v, int position);
        void recyclerViewItemEditClicked(String id, int position);
    }

    public void deleteItem(int posotion){
        categoryList.remove(posotion);
        notifyItemRemoved(posotion);
    }

    public void restoreItem(Category deletedCategory, int deletedIndex){
        categoryList.add(deletedIndex,deletedCategory);
        notifyItemInserted(deletedIndex);
    }

    public Context getContext(){
        return this.context;
    }
}
