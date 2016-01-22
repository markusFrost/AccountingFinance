package com.example.hello.com.myapplicationest1.Fragments;


import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.hello.com.myapplicationest1.Activities.DoneItemsActivity;
import com.example.hello.com.myapplicationest1.Adapters.PlannProductsAdapter;
import com.example.hello.com.myapplicationest1.Databases.DonePurcharesDb;
import com.example.hello.com.myapplicationest1.MainActivity;
import com.example.hello.com.myapplicationest1.Models.PurchareItem;
import com.example.hello.com.myapplicationest1.Objects.Extras;
import com.example.hello.com.myapplicationest1.R;

import java.text.DecimalFormat;
import java.util.List;

public class ListPurchasesFragment extends ListFragment
{
    PlannProductsAdapter adapter;
    double totalPrice = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        long time = getArguments().getLong(Extras.PURCHARE_DATE);

        List<PurchareItem> list = DonePurcharesDb.getDonePurchareItems(getActivity());

        calcTotalAmount(list);

        adapter = new PlannProductsAdapter(getActivity(), list);
        setListAdapter(adapter);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {

                DonePurcharesDb.deletePurchareItem(adapter.getItem(position), getActivity());
                adapter.Remove(position);
                adapter.notifyDataSetChanged();

                return true;
            }
        });

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // на будущее тут будет ид
                /*Intent intent = new Intent(getActivity(), DoneItemsActivity.class);
                intent.putExtra(Extras.PURCHARE_ID, position);
                startActivityForResult(intent, Constants.CODE_PURCHARE_ACTIVITY);*/
            }
        });
    }

    private void calcTotalAmount(List<PurchareItem> list)
    {
        for ( PurchareItem item : list)
        {
            totalPrice += item.TotalPrice;
        }
        setSupportActionBarTitle(totalPrice);
    }

    private void setSupportActionBarTitle(double price)
    {
        DecimalFormat df = new DecimalFormat("#.##");
        DoneItemsActivity activity = (DoneItemsActivity) getActivity();
        activity.getSupportActionBar().setTitle(activity.getResources().getString(R.string.totalPrice) + " " +
                df.format(price) + " " + activity.getResources().getString(R.string.rub));
    }




}
