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

import java.util.List;

public class PlannItemsAdapter extends BaseAdapter
{
    Activity context;
    List<String> list;

    public PlannItemsAdapter(Activity context, List<String> list)
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
            convertView = inflater.inflate(R.layout.list_plann_item,
                    parent, false);

            ViewHolder viewHolder = new ViewHolder();

            viewHolder.tvName = (TextView) convertView
                    .findViewById(R.id.plannTvName);

            viewHolder.cb = (CheckBox) convertView
                    .findViewById(R.id.plannCheckBox);

            convertView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        String name = (String) getItem(position);

        holder.tvName.setText(name);

        return convertView;
    }

    static class ViewHolder
    {
        TextView tvName;
        CheckBox cb;

    }
}
