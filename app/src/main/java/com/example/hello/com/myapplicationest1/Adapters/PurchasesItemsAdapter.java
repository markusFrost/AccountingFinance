package com.example.hello.com.myapplicationest1.Adapters;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.hello.com.myapplicationest1.R;

import java.util.ArrayList;

public class PurchasesItemsAdapter extends BaseAdapter
{
    Activity context;
    ArrayList<String> list;

    public PurchasesItemsAdapter(Activity context, ArrayList<String> list)
    {
        this.context = context;
        this.list = list;
    }

    public void Add(String name)
    {
        list.add(0,name);
    }

    public void Remove(int position)
    {
        list.remove(position);
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_purchare_item,
                    parent, false);

            ViewHolder viewHolder = new ViewHolder();

            viewHolder.tvName = (TextView) convertView
                    .findViewById(R.id.purchareTvName);

            convertView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        String name = (String) getItem(position);

        holder.tvName.setText(name);

        if ( position == 0)
        {
            holder.tvName.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
        }

        return convertView;
    }

    static class ViewHolder
    {
        TextView tvName;
    }
}
