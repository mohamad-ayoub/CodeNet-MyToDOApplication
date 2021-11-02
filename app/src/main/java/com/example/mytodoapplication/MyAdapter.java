package com.example.mytodoapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private ArrayList<String> data = new ArrayList<String>();
    private Context context;

    public MyAdapter(Context context) {
        this.context=context;
        //data.add("first");
        //add("second");
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.item_layout, parent, false);
        }

        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        tvTitle.setText(getItem(position).toString());
        return convertView;
    }

    public void add(String newItemTitle) {
        data.add(newItemTitle);
        notifyDataSetChanged();

    }
}
