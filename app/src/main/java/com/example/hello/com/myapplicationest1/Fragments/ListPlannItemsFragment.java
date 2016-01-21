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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hello.com.myapplicationest1.Activities.ProductDetailActivity;
import com.example.hello.com.myapplicationest1.Adapters.PlannItemsAdapter;
import com.example.hello.com.myapplicationest1.Adapters.ProductsAdapter;
import com.example.hello.com.myapplicationest1.MainActivity;
import com.example.hello.com.myapplicationest1.Models.Product;
import com.example.hello.com.myapplicationest1.Objects.Constants;
import com.example.hello.com.myapplicationest1.Objects.Extras;
import com.example.hello.com.myapplicationest1.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ListPlannItemsFragment extends Fragment
{

    ProductsAdapter adapter;

    double totalPrice = 0;
    private double supportActionBarTitle;

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

        setSupportActionBarTitle(totalPrice);



        List<Product> list = new ArrayList<Product>();

        adapter = new ProductsAdapter(getActivity(), list);

        lv.setAdapter(adapter);

        //createAddItemAlertDialog(getActivity());

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                Product product = adapter.getItem(position);
                totalPrice -= product.getPriceTotal();
                adapter.Remove(position);
                adapter.notifyDataSetChanged();
                setSupportActionBarTitle(totalPrice);
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

                createAddItemAlertDialog(getActivity());
            }
        });

        return v;
    }




    private void createAddItemAlertDialog(Activity context)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setMessage(R.string.input_product_name);
        alert.setTitle(R.string.add_new_product);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText editName = new EditText(context);
        //editName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        editName.setHint(R.string.name);
        layout.addView(editName);

        final EditText editCount = new EditText(context);
        editCount.setInputType(InputType.TYPE_CLASS_NUMBER
                | InputType.TYPE_NUMBER_FLAG_DECIMAL |
                InputType.TYPE_NUMBER_FLAG_SIGNED);
        editCount.setHint(R.string.count);
        layout.addView(editCount);

        alert.setView(layout);


        alert.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                createProduct(editName.getText().toString(), editCount.getText().toString());
            }
        });

        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.show();
    }

    private void createProduct(String name, String strCount)
    {
        try
        {
            double count = Double.parseDouble(strCount);
            Product item = new Product();
            item.setName(name);
            item.setProductCount(count);
            double productAmount = getPriceTotal(count);
            item.setPriceTotal(productAmount);

            totalPrice += productAmount;

            adapter.Add(item);
            adapter.notifyDataSetChanged();
            setSupportActionBarTitle(totalPrice);
        }catch (Exception e){}
    }

    private double getPriceTotal(double count)
    {
        Random random = new Random();
        double pricePerUnit =  random.nextDouble() * 100;
        return count * pricePerUnit;

    }

    private void setSupportActionBarTitle(double price)
    {
        DecimalFormat df = new DecimalFormat("#.##");
        MainActivity activity = (MainActivity) getActivity();
        activity.getSupportActionBar().setTitle(activity.getResources().getString(R.string.totalPrice) + " " +
                df.format(price) + " " + activity.getResources().getString(R.string.rub));
    }
}
