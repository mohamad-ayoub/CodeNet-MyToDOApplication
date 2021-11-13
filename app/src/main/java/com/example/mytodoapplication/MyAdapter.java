package com.example.mytodoapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;

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
                if (holder.tvDetails.getVisibility() == View.VISIBLE) {
                    holder.tvDetails.setVisibility(View.GONE);
                } else {
                    holder.tvDetails.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.tvTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final EditText newTitle = new EditText(context);
                newTitle.setText(data.get(position).getTitle());
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Edit Title");
                builder.setMessage("Edit todo title");
                builder.setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String val = newTitle.getText().toString();
                        if (val.length() > 0) {
                            data.get(position).setTitle(val);
                            notifyDataSetChanged();
                        }
                    }
                });
                builder.setNegativeButton("cancel", null);
                builder.setCancelable(true);


                builder.setView(newTitle);
                builder.show();
                return true;
            }
        });

        holder.tvDetails.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final EditText newDetails = new EditText(context);
                newDetails.setText(data.get(position).getDetails());
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Edit Detail");
                builder.setMessage("Edit todo details");
                builder.setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String val = newDetails.getText().toString();
                        data.get(position).setDetails(val);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("cancel", null);
                builder.setCancelable(true);

                builder.setView(newDetails);
                builder.show();
                return true;
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
                MySoundPool.playDeleteSound(context);
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
                builder.setNegativeButton("cancel", null);
                //builder.setOnCancelListener(null);
                //builder.setOnDismissListener(null);
                builder.show();

            }
        });

        return convertView;
    }

    public void add(Item newItem) {
        for (Item item : data) {
            if (item.getTitle().equals(newItem.getTitle())) {

                Toast.makeText(context, newItem.getTitle() + " Alredy exist!", Toast.LENGTH_SHORT).show();return;
            }
        }
        data.add(newItem);
        notifyDataSetChanged();
    }

    @SuppressLint("NewApi")
    public void load() {
        // load data from shared preferences file
        SharedPreferences sp = context.getSharedPreferences("MyData_TODO_APPLICATION", Context.MODE_PRIVATE);
        Set<String> keys = sp.getAll().keySet();

        for (String key : keys) {
            String value = sp.getString(key, "");
            boolean isDone = value.charAt(0) == '1' ? true : false;
            String details = value.substring(1);

            Item item = new Item(key, details, isDone);
            data.add(item);
        }

        data.sort(new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        });
        notifyDataSetChanged();
    }

    public void save() {
        // save data
        SharedPreferences sp = context.getSharedPreferences("MyData_TODO_APPLICATION", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        for (int i = 0; i < data.size(); i++) {
            String key = data.get(i).getTitle();
            //String value=(data.get(i).isDone() ? 1: 0)+ data.get(i).getDetails();
            String value;
            if (data.get(i).isDone()) {
                value = "1" + data.get(i).getDetails();
            } else {
                value = "0" + data.get(i).getDetails();
            }
            editor.putString(key, value);
        }
        editor.commit();
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
