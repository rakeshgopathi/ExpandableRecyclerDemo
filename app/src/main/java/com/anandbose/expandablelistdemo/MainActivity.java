package com.anandbose.expandablelistdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;

import com.anandbose.expandablelistdemo.ExpandableListAdapter.Item;

import java.util.ArrayList;

import static com.anandbose.expandablelistdemo.ExpandableListAdapter.CHILD;
import static com.anandbose.expandablelistdemo.ExpandableListAdapter.HEADER;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        SparseArray<ExpandableListAdapter.Item> data = new SparseArray<>();
        int i=0;
        data.setValueAt(i++, new ExpandableListAdapter.Item(HEADER, "Fruits"));
        data.setValueAt(i++, new ExpandableListAdapter.Item(CHILD, "Apple"));
        data.setValueAt(i++, new ExpandableListAdapter.Item(CHILD, "Orange"));
        data.setValueAt(i++, new ExpandableListAdapter.Item(CHILD, "Banana"));
        data.setValueAt(i++, new ExpandableListAdapter.Item(HEADER, "Cars"));
        data.setValueAt(i++, new ExpandableListAdapter.Item(CHILD, "Audi"));
        data.setValueAt(i++, new ExpandableListAdapter.Item(CHILD, "Aston Martin"));
        data.setValueAt(i++, new ExpandableListAdapter.Item(CHILD, "BMW"));
        data.setValueAt(i++ ,new ExpandableListAdapter.Item(CHILD, "Cadillac"));

        ExpandableListAdapter.Item places = new ExpandableListAdapter.Item(HEADER, "Places");
        places.invisibleChildren = new ArrayList<>();
        places.invisibleChildren.add(new Item(CHILD, "Kerala"));
        places.invisibleChildren.add(new Item(CHILD, "Tamil Nadu"));
        places.invisibleChildren.add(new Item(CHILD, "Karnataka"));
        places.invisibleChildren.add(new Item(CHILD, "Maharashtra"));

        data.setValueAt(i, places);


        recyclerview.setAdapter(new ExpandableListAdapter(data));
    }
}
