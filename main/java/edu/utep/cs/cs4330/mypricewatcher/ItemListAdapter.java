package edu.utep.cs.cs4330.mypricewatcher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemListAdapter extends ArrayAdapter<Item> {

    private final ArrayList<Item> itemList;

    public ItemListAdapter(Context context, ArrayList<Item> item) {
        super(context, -1, item);
        this.itemList = item;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView != null ? convertView : LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_view_layout, parent, false);
        Item item = itemList.get(position);
        TextView name = row.findViewById(R.id.itemNameString);
        TextView initial = row.findViewById(R.id.initialString);
        TextView current = row.findViewById(R.id.currentString);
        TextView change = row.findViewById(R.id.changeString);
        initial.setText(item.getInitialPrice());
        current.setText(item.getCurrentPrice());
        change.setText(item.getChangePrice());
        name.setText(item.getName());
        return row;
    }



}
