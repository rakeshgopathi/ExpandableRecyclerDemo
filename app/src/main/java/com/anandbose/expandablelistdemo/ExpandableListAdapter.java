package com.anandbose.expandablelistdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.List;

public class ExpandableListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int HEADER = 0;
    public static final int CHILD = 1;

    private List<Item> mItemData;

    public ExpandableListAdapter(List<Item> Itemdata) {
        this.mItemData = Itemdata;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = null;
        Context context = parent.getContext();
        float dp = context.getResources().getDisplayMetrics().density;
        int subItemPaddingLeft = (int) (18 * dp);
        int subItemPaddingTopAndBottom = (int) (5 * dp);
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (type) {
            case HEADER:
                view = inflater.inflate(R.layout.list_header, parent, false);
                ListHeaderViewHolder header = new ListHeaderViewHolder(view);
                return header;
            case CHILD:
                view = inflater.inflate(R.layout.child_list_header, parent, false);
                ListChildViewHolder child_header = new ListChildViewHolder(view);
                return child_header;
        }
        return null;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Item item = mItemData.get(position);
        switch (item.type) {
            case HEADER:
                final ListHeaderViewHolder headerViewholder = (ListHeaderViewHolder) holder;
                headerViewholder.refferalItem = item;
                headerViewholder.header_title.setText(item.text);
                if (item.invisibleChildren == null) {
                    headerViewholder.btn_expand_toggle.setImageResource(R.drawable.circle_minus);
                } else {
                    headerViewholder.btn_expand_toggle.setImageResource(R.drawable.circle_plus);
                }
                headerViewholder.btn_expand_toggle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.invisibleChildren == null) {
                            item.invisibleChildren = new ArrayList<Item>();
                            int count = 0;
                            int pos = mItemData.indexOf(headerViewholder.refferalItem);
                            while (mItemData.size() > pos + 1 && mItemData.get(pos + 1).type == CHILD) {
                                item.invisibleChildren.add(mItemData.remove(pos + 1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);
                            headerViewholder.btn_expand_toggle.setImageResource(R.drawable.circle_plus);
                        } else {
                            int pos = mItemData.indexOf(headerViewholder.refferalItem);
                            int index = pos + 1;
                            for (Item i : item.invisibleChildren) {
                                mItemData.add(index, i);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);
                            headerViewholder.btn_expand_toggle.setImageResource(R.drawable.circle_minus);
                            item.invisibleChildren = null;
                        }
                    }
                });
                break;
            case CHILD:
                TextView itemTextView = ((ListChildViewHolder) holder).header_title;
                itemTextView.setText(mItemData.get(position).text);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mItemData.get(position).type();
    }

    @Override
    public int getItemCount() {
        return mItemData.size();
    }

    private static class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView header_title;
        public ImageView btn_expand_toggle;
        public Item refferalItem;

        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            header_title = (TextView) itemView.findViewById(R.id.header_title);
            btn_expand_toggle = (ImageView) itemView.findViewById(R.id.btn_expand_toggle);
        }
    }

    private static class ListChildViewHolder extends RecyclerView.ViewHolder {
        public TextView header_title;
        public ImageView btn_expand_toggle;
        public Item refferalItem;

        public ListChildViewHolder(View itemView) {
            super(itemView);
            header_title = (TextView) itemView.findViewById(R.id.header_title);
            btn_expand_toggle = (ImageView) itemView.findViewById(R.id.btn_expand_toggle);
        }
    }

    static abstract class ViewModel {
        abstract int type();
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
    }
}