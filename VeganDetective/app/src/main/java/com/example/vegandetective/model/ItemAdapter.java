package com.example.vegandetective.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.vegandetective.R;

import java.util.ArrayList;


public class ItemAdapter extends ArrayAdapter<Item> {

    private ArrayList<Item> dataSetItem;
    private Context myContext;
    private int rowLayout;
    private ImageView imageView;

    public ItemAdapter(Context context, int rowLayout, ArrayList<Item> dataSetItem) {
        super(context, rowLayout, dataSetItem);
        this.dataSetItem = dataSetItem;
        this.myContext = context;
        this.rowLayout = rowLayout;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(myContext).inflate(rowLayout, parent, false);
        }
        Item currentItem = this.dataSetItem.get(position);
        TextView itemName = listItem.findViewById(R.id.item_name);
        imageView = listItem.findViewById(R.id.list_picture);
        TextView itemType = listItem.findViewById(R.id.item_type);
        itemName.setText(currentItem.getItemName());
        itemType.setText(currentItem.getDescription());


        if (currentItem.isSelected()) {
            imageView.setImageResource(R.drawable.green_selected);
        } else {
            imageView.setImageResource(R.drawable.red_selected);
        }


        return listItem;
    }


}
