package com.example.hello.com.myapplicationest1.Fragments;


import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class ListPlannItemsFragment extends ListFragment
{
    String data[] = new String[] { "one", "two", "three", "four" }; // this is test array

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, data);
        setListAdapter(adapter);
    }
}
