package com.anandbose.expandablelistdemo;

import android.support.v7.widget.RecyclerView;
import android.view.View;

abstract class ViewModel {
    abstract int type();
    abstract RecyclerView.ViewHolder getViewHolder(View view);
}