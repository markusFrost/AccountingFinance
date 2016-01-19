package com.example.hello.com.myapplicationest1.Fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.hello.com.myapplicationest1.Adapters.PlannItemsAdapter;
import com.example.hello.com.myapplicationest1.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListPlannItemsFragment extends ListFragment
{
    String data[] = new String[] { "one", "two", "three", "four" }; // this is test array

    PlannItemsAdapter adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<String> list = new ArrayList<String>(Arrays.asList(data));

        adapter = new PlannItemsAdapter(getActivity(), list);

        setListAdapter(adapter);

        //createAddItemAlertDialog(getActivity());

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
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

            }
        });
    }


    private void createAddItemAlertDialog(Activity context)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        final EditText edittext = new EditText(context);

        edittext.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        alert.setMessage(R.string.input_product_name);
        alert.setTitle(R.string.add_new_product);

        alert.setView(edittext);

        alert.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                String name = edittext.getText().toString();
                adapter.Add(name);
                adapter.notifyDataSetChanged();
            }
        });

        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {

            }
        });

        alert.show();
    }
}
