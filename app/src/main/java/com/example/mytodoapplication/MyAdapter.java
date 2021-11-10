package com.example.mytodoapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private ArrayList<Item> data;
    private Context context;

    public MyAdapter(Context context) {
        this.context = context;
        data = new ArrayList<Item>();
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
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.item_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Item currentItem = (Item) getItem(position);

        holder.tvTitle.setText(currentItem.getTitle());
        holder.chkDone.setChecked(currentItem.isDone());
        holder.tvDetails.setText(currentItem.getDetails());

        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.tvDetails.getVisibility()==View.VISIBLE) {
                    holder.tvDetails.setVisibility(View.GONE);
                } else {
                    holder.tvDetails.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.chkDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox chkDone = (CheckBox) v;
                currentItem.setDone(chkDone.isChecked());
                //Toast.makeText(context, currentItem.isDone()+"", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                // builder.setView();
                builder.setCancelable(true);
                builder.setTitle("Delete item");
                builder.setMessage("Are you sure you want to delete this item ?");
                //builder.setIcon();
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        data.remove(position);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("cancel",null);
                //builder.setOnCancelListener(null);
                //builder.setOnDismissListener(null);
                builder.show();
            }
        });

        return convertView;
    }

    public void add(Item newItem) {
        data.add(newItem);
        notifyDataSetChanged();

    }

    private class ViewHolder {
        TextView tvTitle;
        CheckBox chkDone;
        TextView tvDetails;
        Button btnDelete;

        public ViewHolder(View view) {
             tvTitle = view.findViewById(R.id.tvTitle);
             chkDone = view.findViewById(R.id.chkDone);
             tvDetails = view.findViewById(R.id.tvDetails);
             btnDelete = view.findViewById(R.id.btnDelete);
        }
    }
}
