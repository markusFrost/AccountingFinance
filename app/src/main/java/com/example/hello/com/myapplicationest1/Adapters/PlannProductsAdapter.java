package com.example.hello.com.myapplicationest1.Adapters;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hello.com.myapplicationest1.Models.PurchareItem;
import com.example.hello.com.myapplicationest1.Objects.HelpUtils;
import com.example.hello.com.myapplicationest1.R;

import java.text.DecimalFormat;
import java.util.List;

public class PlannProductsAdapter extends BaseAdapter
{
    Activity context;
    List<PurchareItem> list;

    public PlannProductsAdapter(Activity context, List<PurchareItem> list)
    {
        this.context = context;
        this.list = list;
    }

    public void Add(PurchareItem item)
    {
        list.add(0, item);
    }

    public void Update ( int position, PurchareItem item)
    {
        list.set(position, item);
    }


    public void Remove(int position)
    {
        list.remove(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public PurchareItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.product_item,
                    parent, false);

            ViewHolder viewHolder = new ViewHolder();

            viewHolder.tvName = (TextView) convertView
                    .findViewById(R.id.productName);

            viewHolder.tvTotalPrice = (TextView) convertView
                    .findViewById(R.id.productTotalPrice);

            viewHolder.tvProductCount = (TextView) convertView
                    .findViewById(R.id.productCount);

            viewHolder.ll = (LinearLayout) convertView
                    .findViewById(R.id.productLL);

            convertView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        PurchareItem item =  getItem(position);

        DecimalFormat df = new DecimalFormat("#.##");

        holder.tvName.setText(HelpUtils.formatName(item.Product.Name));
        holder.tvProductCount.setText(df.format(item.Count));
        holder.tvTotalPrice.setText(df.format(item.TotalPrice) + " " + context.getResources().getString(R.string.rub));

        if ( item.Count == 0)
        {
            holder.tvProductCount.setVisibility(View.GONE);
        }
        else
        {
            holder.tvProductCount.setVisibility(View.VISIBLE);
        }

        if ( item.TotalPrice == 0)
        {
            holder.tvTotalPrice.setVisibility(View.GONE);
        }
        else
        {
            holder.tvTotalPrice.setVisibility(View.VISIBLE);
        }

      /*  if (item.getIsDone() == Constants.DONE)
        {
            holder.ll.setBackgroundColor(Color.BLUE);
            holder.tvName.setTextColor(Color.WHITE);
            holder.tvTotalPrice.setTextColor(Color.WHITE);
            holder.tvProductCount.setTextColor(Color.WHITE);
        }
        else
        {
            holder.ll.setBackgroundColor(Color.WHITE);
            holder.tvName.setTextColor(context.getResources().getColor(android.R.color.holo_purple));
            holder.tvTotalPrice.setTextColor(context.getResources().getColor(android.R.color.holo_blue_dark));
            holder.tvProductCount.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        }*/

        return convertView;
    }

    static class ViewHolder
    {
        TextView tvName;
        TextView tvTotalPrice;
        TextView tvProductCount;
        LinearLayout ll;
    }
}
