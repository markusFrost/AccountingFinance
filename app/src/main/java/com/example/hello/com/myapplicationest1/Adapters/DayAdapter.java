package com.example.hello.com.myapplicationest1.Adapters;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.hello.com.myapplicationest1.Models.DayDto;
import com.example.hello.com.myapplicationest1.R;

import java.util.List;

public class DayAdapter extends BaseAdapter
{
    Activity context;
    List<DayDto> list;

    public DayAdapter(Activity context, List<DayDto> list )
    {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public DayDto getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).Id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.day_layout,
                    parent, false);

            ViewHolder viewHolder = new ViewHolder();

            viewHolder.tvName = (TextView) convertView
                    .findViewById(R.id.tvDayName);
            convertView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        DayDto item = getItem(position);

        holder.tvName.setText(item.Name);

        return convertView;
    }

    static class ViewHolder
    {
        TextView tvName;
    }
}
