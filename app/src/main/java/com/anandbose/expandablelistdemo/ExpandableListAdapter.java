package com.anandbose.expandablelistdemo;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListAdapter extends BaseRecyclerAdapter {

    public static final int CHILD = 2;
    public static final int HEADER = 1;

    public ExpandableListAdapter(SparseArray<Item> itemData) {
        super(itemData);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        return super.onCreateViewHolder(parent, type);
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    private static class ListHeaderViewHolder extends BaseViewHolder {
        public TextView header_title;
        public ImageView btn_expand_toggle;
        public Item refferalItem;

        public ListHeaderViewHolder(int type, View itemView) {
            super(type, itemView);
            header_title = (TextView) itemView.findViewById(R.id.header_title);
            btn_expand_toggle = (ImageView) itemView.findViewById(R.id.btn_expand_toggle);
        }

        @Override
        void bindView(int type, final SparseArray<? extends ViewModel> items, int position) {

            final Item item = (Item) items.get(position);
            header_title.setText(item.text);
            if (item.invisibleChildren == null) {
                btn_expand_toggle.setImageResource(R.drawable.circle_minus);
            } else {
               btn_expand_toggle.setImageResource(R.drawable.circle_plus);
            }
            btn_expand_toggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.invisibleChildren == null) {
                        item.invisibleChildren = new ArrayList<Item>();
                        int count = 0;
                        int pos = items.indexOfValue(refferalItem);
                        while (items.size() > pos + 1 && ((Item)items.get(pos + 1)).type == CHILD) {
                            item.invisibleChildren.add(((Item)items.get(pos + 1)));
                            items.remove(pos + 1);
                            count++;
                        }
                        notifyItemRangeRemoved(pos + 1, count);
                        btn_expand_toggle.setImageResource(R.drawable.circle_plus);
                    } else {
                        int pos = items.indexOfValue(refferalItem);
                        int index = pos + 1;
                        for (Item i : item.invisibleChildren) {
                            items.setValueAt(index, i);
                            index++;
                        }
                        notifyItemRangeInserted(pos + 1, index - pos - 1);
                        btn_expand_toggle.setImageResource(R.drawable.circle_minus);
                        item.invisibleChildren = null;
                    }
                }
            });
        }
    }

    private static class ListChildViewHolder extends BaseViewHolder {
        public TextView header_title;
        public ImageView btn_expand_toggle;

        public ListChildViewHolder(int type, View itemView) {
            super(type, itemView);
            header_title = (TextView) itemView.findViewById(R.id.header_title);
            btn_expand_toggle = (ImageView) itemView.findViewById(R.id.btn_expand_toggle);
        }

        @Override
        void bindView(int type, SparseArray<? extends ViewModel> item, int position) {
                header_title.setText(((Item)(item.get(position))).text);
        }
    }

    //This solution is for Items with different object models.
    public static class Item extends ViewModel {
        public int type;
        public String text;
        public List<Item> invisibleChildren;

        public Item() {
        }

        public Item(int type, String text) {
            this.type = type;
            this.text = text;
        }

        @Override
        int type() {
            return type;
        }

        @Override
        RecyclerView.ViewHolder getViewHolder(View view) {
            switch (type()){
                default: return new ListHeaderViewHolder(type(), view);
            }
        }
    }
}