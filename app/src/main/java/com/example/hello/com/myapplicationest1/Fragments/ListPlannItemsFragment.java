package com.example.hello.com.myapplicationest1.Fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.hello.com.myapplicationest1.Adapters.PlannProductsAdapter;
import com.example.hello.com.myapplicationest1.Databases.PlannPurchareDb;
import com.example.hello.com.myapplicationest1.Databases.ProductDb;
import com.example.hello.com.myapplicationest1.MainActivity;
import com.example.hello.com.myapplicationest1.Models.PurchareItem;
import com.example.hello.com.myapplicationest1.Models.Product;
import com.example.hello.com.myapplicationest1.Objects.Constants;
import com.example.hello.com.myapplicationest1.Objects.Extras;
import com.example.hello.com.myapplicationest1.Objects.HelpUtils;
import com.example.hello.com.myapplicationest1.R;

import java.text.DecimalFormat;
import java.util.List;

public class ListPlannItemsFragment extends Fragment
{

    PlannProductsAdapter adapter;

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

        List<PurchareItem> list = PlannPurchareDb.getPlannPurchares(getActivity());

        calcTotalAmount(list);

        adapter = new PlannProductsAdapter(getActivity(), list);

        lv.setAdapter(adapter);


        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3)
            {
                PurchareItem productItem = adapter.getItem(position);
                totalPrice -= productItem.TotalPrice;
                adapter.Remove(position);
                adapter.notifyDataSetChanged();

                PlannPurchareDb.deletePlannPurchare(productItem, getActivity());

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
            public void onClick(View view) {

                createAddItemAlertDialog(getActivity());
            }
        });

        return v;
    }

    private void calcTotalAmount(List<PurchareItem> list)
    {
        for ( PurchareItem item : list)
        {
            totalPrice += item.TotalPrice;
        }
        setSupportActionBarTitle(totalPrice);
    }


    private void createAddItemAlertDialog(Activity context)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setMessage(R.string.input_product_name);
        alert.setTitle(R.string.add_new_product);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        //http://developer.android.com/intl/ru/guide/topics/ui/controls/text.html

        final AutoCompleteTextView editName = new AutoCompleteTextView(context);
        //editName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        editName.setHint(R.string.name);
        //-----------------------------------------------
        List<String> products_array = ProductDb.getProductsNames(getActivity());
        ArrayAdapter<String> product_adapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, products_array);
        editName.setAdapter(product_adapter);


        //-----------------------------------------------
        layout.addView(editName);

        final EditText editCount = new EditText(context);
        editCount.setInputType(InputType.TYPE_CLASS_NUMBER
                | InputType.TYPE_NUMBER_FLAG_DECIMAL |
                InputType.TYPE_NUMBER_FLAG_SIGNED);
        editCount.setHint(R.string.count);
        layout.addView(editCount);

        editName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editCount.requestFocus();
            }
        });

        alert.setView(layout);


        alert.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                saveToDb(editName.getText().toString(), Constants.ALL, editCount.getText().toString());
            }
        });

        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.show();
    }

    private Product createProduct(String name)
    {
        Product item = null;

        item = ProductDb.isProductExists(name, getActivity());

        if ( item == null )
        {
            item = new Product();
            item.Name = HelpUtils.formatName(name);
            item.PricePerUnit = 0;

            item.Id = ProductDb.createProduct(item, getActivity());
        }

        return item;
    }

    private PurchareItem createPlannPurchare(String strCount, Product product, int type)
    {
        try
        {
            double count = Double.parseDouble(strCount);
            PurchareItem item = new PurchareItem();

            item.Count = count;
            item.DatePurchare = HelpUtils.getTimeMillsDay(System.currentTimeMillis());
            item.Product = product;
            item.TotalPrice = item.Product.PricePerUnit * item.Count;
            item.Type = type;
            item.Id = PlannPurchareDb.createPlannPurchare(item, getActivity());

            return item;

        }catch (Exception e){}

        return null;
        }

    private void saveToDb(String name, int type, String strCount)
    {
        Product product = createProduct(name);

        PurchareItem plannPurchare = createPlannPurchare(strCount, product, type);

        totalPrice += plannPurchare.TotalPrice;

        adapter.Add(plannPurchare);
        adapter.notifyDataSetChanged();
        setSupportActionBarTitle(totalPrice);


    }


    private void setSupportActionBarTitle(double price)
    {
        DecimalFormat df = new DecimalFormat("#.##");
        MainActivity activity = (MainActivity) getActivity();
        activity.getSupportActionBar().setTitle(activity.getResources().getString(R.string.totalPrice) + " " +
                df.format(price) + " " + activity.getResources().getString(R.string.rub));
    }
}
