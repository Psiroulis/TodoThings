package com.redpepper.todothings.ui.shopping_Item.listAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.redpepper.todothings.DataModels.Item;
import com.redpepper.todothings.R;

import java.util.List;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder> {

    private final List<Item> itemList;

    private static ItemRecyclerViewClickListener itemListener;

    private Context context;

    public ItemRecyclerViewAdapter(List<Item> itemList, ItemRecyclerViewClickListener listener) {

        this.itemList = itemList;
        this.itemListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_shopping_item, parent, false);

        this.context = parent.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Item item = itemList.get(position);

        holder.itemId.setText(item.getId());
        holder.itemDescription.setText(item.getDescription());
        holder.itemAmount.setText(Integer.toString(item.getAmount()));
        holder.editBtn.setOnClickListener(view-> itemListener.recyclerViewItemEditClicked(item.getId(), position));
        holder.doneBox.setChecked(item.isChecked());
        holder.doneBox.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if(isChecked){

                itemListener.recyclerViewItemCheckedChange(position,true);

            }else{

                itemListener.recyclerViewItemCheckedChange(position,false);

            }

        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void deleteItem(int posotion){
        itemList.remove(posotion);
        notifyItemRemoved(posotion);
    }

    public void restoreItem(Item deletedItem, int deletedIndex){
        itemList.add(deletedIndex,deletedItem);
        notifyItemInserted(deletedIndex);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView itemId;
        public TextView itemDescription;
        public TextView itemAmount;
        public RelativeLayout viewBackground, viewForeground;
        public ImageButton editBtn;
        public CheckBox doneBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemId = itemView.findViewById(R.id.shoppingItemId);
            itemDescription = itemView.findViewById(R.id.shoppingItemDescription);
            itemAmount = itemView.findViewById(R.id.shoppingItemAmount);
            viewBackground = itemView.findViewById(R.id.view_background_shit);
            viewForeground = itemView.findViewById(R.id.view_foreground_shit);
            editBtn = itemView.findViewById(R.id.shoppingItemEditButton);
            doneBox = itemView.findViewById(R.id.doneBox);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v,this.getLayoutPosition());
        }
    }

    public interface ItemRecyclerViewClickListener{
        void recyclerViewListClicked(View v, int position);
        void recyclerViewItemEditClicked(String id, int position);
        void recyclerViewItemCheckedChange(int position, boolean checked);
    }

    public Context getContext(){
        return this.context;
    }

}
