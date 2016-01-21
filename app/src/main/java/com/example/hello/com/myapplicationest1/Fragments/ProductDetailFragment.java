package com.example.hello.com.myapplicationest1.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hello.com.myapplicationest1.Adapters.ProductsAdapter;
import com.example.hello.com.myapplicationest1.Models.Product;
import com.example.hello.com.myapplicationest1.Objects.Constants;
import com.example.hello.com.myapplicationest1.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ProductDetailFragment extends ListFragment
{
    EditText editName, editProductCount, editPricePerUnit;
    Spinner spn;
    TextView tvResult;
    Button btnOk, btnClear;

    double totalAmount = 0;

    String[] data = {"one", "two", "three", "four", "five","one", "two", "three", "four", "five","one", "two", "three", "four", "five"};

    ProductsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_plann, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if ( id == R.id.menuAddProductItem)
        {
            createListViewAlertDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        View header = getActivity().getLayoutInflater().inflate(R.layout.product_detail_item, null);

        List<Product> productList = new ArrayList<>();

        adapter = new ProductsAdapter(getActivity(), productList);

        setListAdapter(adapter);
        getListView().addHeaderView(header);

        fillView(header);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position--;

                Product item = adapter.getItem(position);
                if (item.getIsDone() == Constants.DONE) {
                    item.setIsDone(Constants.PLAN);
                } else {
                    item.setIsDone(Constants.DONE);
                }
                adapter.Update(position, item);
                adapter.notifyDataSetChanged();

            }
        });

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                position--;

                double price = adapter.getItem(position).getPriceTotal();
                totalAmount -= price;

                tvResult.setText(getActivity().getResources().getString(R.string.result) + " " +
                        totalAmount + getActivity().getResources().getString(R.string.rub));

                adapter.Remove(position);
                adapter.notifyDataSetChanged();

                return false;
            }
        });

    }


    /* public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.product_detail_item, container, false);

        fillView(v);


        return v;
    }*/

    private void calcProductAmount()
    {
        String strCount = editProductCount.getText().toString();
        String strPricePerUnit = editPricePerUnit.getText().toString();

        try
        {
            double count = Double.parseDouble(strCount);
            double pricePerUnit = Double.parseDouble(strPricePerUnit);

            DecimalFormat df = new DecimalFormat("#.##");
            String resultValue = df.format(count * pricePerUnit) ;



            tvResult.setText( getActivity().getResources().getString(R.string.result) +
                            " " + getActivity().getResources().getString(R.string.current_product) +
                    " " + resultValue + " " +
                    getActivity().getResources().getString(R.string.rub) +  "\n" +
                            getActivity().getResources().getString(R.string.totalPrice)  + " " +
                    ( totalAmount + count * pricePerUnit ) +  getActivity().getResources().getString(R.string.rub)
            );

        }
        catch (Exception e){
            tvResult.setText( getActivity().getResources().getString(R.string.result) + " " +
                             totalAmount  +  getActivity().getResources().getString(R.string.rub)
            );

        }
    }

    private void clearFields()
    {
        editName.setText("");
        editProductCount.setText("");
        editPricePerUnit.setText("");

        spn.setSelection(0);
        setFocus(editName);
    }

    private void setFocus(EditText edit)
    {
        /*InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT);*/
        edit.requestFocus();

        showKeyboard(getActivity());
    }

    public static void showKeyboard(Activity activity) {
        if (activity != null) {
            activity.getWindow()
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null) {
            activity.getWindow()
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    private void fillView(View v)
    {
        editName = (EditText) v.findViewById(R.id.editProductName);
        spn = (Spinner) v.findViewById(R.id.spnProductPersonType);
        editProductCount = (EditText) v.findViewById(R.id.editProductCount);
        editPricePerUnit = (EditText) v.findViewById(R.id.editPricePerUnit);
        tvResult = (TextView) v.findViewById(R.id.tvProductResult);
        btnOk = (Button) v.findViewById(R.id.btnProductOk);
        btnClear = (Button) v.findViewById(R.id.btnProductClear);

        setFocus(editName);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String strCount = editProductCount.getText().toString();
                String strPricePerUnit = editPricePerUnit.getText().toString();

                try
                {
                    double count = Double.parseDouble(strCount);
                    double pricePerUnit = Double.parseDouble(strPricePerUnit);
                    totalAmount += count * pricePerUnit;
                    createProduct(editName.getText().toString(), spn.getSelectedItemPosition(), pricePerUnit, count);
                    clearFields();
                }
                catch (Exception e){}
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });

        editProductCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                calcProductAmount();
            }
        });

        editPricePerUnit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                calcProductAmount();
            }
        });
    }

    private void createProduct(String name, int productPersonType, double pricePerUnit, double count)
    {
        Product item = new Product();
        item.setName(name);
        item.setPersonType(productPersonType);
        item.setPricePerUnit(pricePerUnit);
        item.setPriceTotal(count * pricePerUnit);
        item.setProductCount(count);
        item.setIsDone(Constants.PLAN);

        adapter.Add(item);
        adapter.notifyDataSetChanged();
    }
     CharSequence[] listPlannItems = null;
    private void createListViewAlertDialog()
    {
         listPlannItems = formCharSequenceFromProducts();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Make your selection");
        builder.setItems(listPlannItems, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int index) {
                //Toast.makeText(getActivity(), item + "", Toast.LENGTH_SHORT).show();
                //TODO: При клике на запланированную покупку элемент должен удаляться
                editName.setText(getProductName(listPlannItems[index]) + "");
                editProductCount.setText(getProductCountFromString(listPlannItems[index]) + "");
                editPricePerUnit.requestFocus();
                showKeyboard(getActivity());

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private String getProductName(CharSequence text)
    {
        int skob_index = String.valueOf(text).indexOf("(");

        return String.valueOf(text).substring(0, skob_index).replace(" ", "");
    }

    private double getProductCountFromString(CharSequence text)
    {
        int skob_index = String.valueOf(text).indexOf("(");

        String count = String.valueOf(text).substring(skob_index).replace(" ","").replace("(", "").replace(")", "");

        try
        {
            return Double.parseDouble(count);
        }catch (Exception e)
        {}
        return  0;
    }

    private CharSequence[] formCharSequenceFromProducts()
    {
        List<Product> listProducts = new ArrayList<>();
        Product item;

        for ( int i = 0; i <= 10; i++)
        {
            item = new Product();
            item.setName("Name " + i);
            item.setProductCount(i + 1);

            listProducts.add(item);
        }

        List<String> listNames = new ArrayList<>();
        for ( Product p : listProducts)
        {
            listNames.add( p.getName().substring(0,1).toUpperCase() +
                    p.getName().substring(1).toLowerCase()
                    + " ( " + p.getProductCount() + " )" );
        }

        return listNames.toArray(new CharSequence[listNames.size()]);
    }
}
