package com.example.hello.com.myapplicationest1.Fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hello.com.myapplicationest1.Adapters.PlannItemsAdapter;
import com.example.hello.com.myapplicationest1.Objects.Constants;
import com.example.hello.com.myapplicationest1.Objects.Extras;
import com.example.hello.com.myapplicationest1.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListPlannItemsFragment extends Fragment
{
    String data[] = new String[] { "one", "two", "three", "four", "two", "three", "four", "two", "three", "four", "two", "three", "four", "two", "three", "four" };

    PlannItemsAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.plann_items_list, container, false);

        ListView lv = (ListView) v.findViewById(R.id.lvMain);

         int position = -1;

        if ( getArguments()!= null )
        {
            position = getArguments().getInt(Extras.PURCHARE_ID);
        }



        ArrayList<String> list = new ArrayList<String>(Arrays.asList(data));

        adapter = new PlannItemsAdapter(getActivity(), list);

        lv.setAdapter(adapter);

        //createAddItemAlertDialog(getActivity());

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                adapter.Remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               // Toast.makeText(getActivity(),"12233" + "", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
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
