package com.example.hello.com.myapplicationest1.Fragments;


import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.hello.com.myapplicationest1.Activities.PlannItemsActivity;
import com.example.hello.com.myapplicationest1.Adapters.PurchasesItemsAdapter;
import com.example.hello.com.myapplicationest1.Objects.Constants;
import com.example.hello.com.myapplicationest1.Objects.Extras;
import com.example.hello.com.myapplicationest1.R;

import java.util.ArrayList;
import java.util.Arrays;

public class ListPurchasesFragment extends ListFragment
{
    PurchasesItemsAdapter adapter;

    String data[] = new String[] { "one", "two", "three", "four", "two", "three", "four", "two", "three", "four", "two", "three", "four", "two", "three", "four" };


    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        ArrayList<String> list = new ArrayList<String>(Arrays.asList(data));

        adapter = new PurchasesItemsAdapter(getActivity(), list);
        setListAdapter(adapter);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                adapter.Remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // на будущее тут будет ид
                Intent intent = new Intent(getActivity(), PlannItemsActivity.class);
                intent.putExtra(Extras.PURCHARE_ID, position);
                startActivityForResult(intent, Constants.CODE_PURCHARE_ACTIVITY);
            }
        });
    }




}
