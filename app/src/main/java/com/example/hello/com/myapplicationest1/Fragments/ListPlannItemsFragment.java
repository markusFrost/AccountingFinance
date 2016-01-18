package com.example.hello.com.myapplicationest1.Fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.hello.com.myapplicationest1.Adapters.PlannItemsAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListPlannItemsFragment extends ListFragment
{
    String data[] = new String[] { "one", "two", "three", "four" }; // this is test array

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<String> list = new ArrayList<String>(Arrays.asList(data));

        PlannItemsAdapter adapter = new PlannItemsAdapter(getActivity(), list);

        setListAdapter(adapter);
    }


    private void createAddItemAlertDialog(Activity context)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        final EditText edittext = new EditText(context);
        alert.setMessage("Enter Your Message");
        alert.setTitle("Enter Your Title");

        alert.setView(edittext);

        alert.setPositiveButton("Yes Option", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                String name = edittext.getText().toString();
            }
        });

        alert.setNegativeButton("No Option", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {

            }
        });

        alert.show();
    }
}
