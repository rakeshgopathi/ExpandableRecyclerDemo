package com.anandbose.expandablelistdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Rakesh
 * on 24-10-2016.
 */

public abstract class BaseRecyclerAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public SparseArray<? extends ViewModel> mItemData;

    public BaseRecyclerAdapter(SparseArray<? extends ViewModel> Itemdata) {
        this.mItemData = Itemdata;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {

        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(type, parent, false);
        return mItemData.get(0).getViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof BaseViewHolder){
            ((BaseViewHolder) holder).bindView(getItemViewType(position), mItemData, position);
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

    public abstract static class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(int type, View itemView) {
            super(itemView);
        }

        abstract void bindView(int type, SparseArray<? extends ViewModel> item, int position);
    }
}
