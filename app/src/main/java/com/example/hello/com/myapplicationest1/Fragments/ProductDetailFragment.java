package com.example.hello.com.myapplicationest1.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hello.com.myapplicationest1.Adapters.PlannProductsAdapter;
import com.example.hello.com.myapplicationest1.Adapters.PurchasesItemsAdapter;
import com.example.hello.com.myapplicationest1.Databases.DonePurcharesDb;
import com.example.hello.com.myapplicationest1.Databases.PlannPurchareDb;
import com.example.hello.com.myapplicationest1.Databases.ProductDb;
import com.example.hello.com.myapplicationest1.MainActivity;
import com.example.hello.com.myapplicationest1.Models.PurchareItem;
import com.example.hello.com.myapplicationest1.Models.Product;
import com.example.hello.com.myapplicationest1.Objects.HelpUtils;
import com.example.hello.com.myapplicationest1.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class ProductDetailFragment extends ListFragment
{
    EditText  editProductCount, editPricePerUnit;
    AutoCompleteTextView editName;
    Spinner spn;
    TextView tvResult;
    Button btnOk, btnClear;

    double totalAmount = 0;

    List<PurchareItem> listPlannItems = null;


    PlannProductsAdapter adapter;

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

        List<PurchareItem> productItemList = new ArrayList<>();

        adapter = new PlannProductsAdapter(getActivity(), productItemList);

        setListAdapter(adapter);
        getListView().addHeaderView(header);

        fillView(header);

        listPlannItems =  PlannPurchareDb.getPlannPurchares(getActivity());

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position--;

               /* ProductItem item = adapter.getItem(position);
                if (item.getIsDone() == Constants.DONE) {
                    item.setIsDone(Constants.PLAN);
                } else {
                    item.setIsDone(Constants.DONE);
                }
                adapter.Update(position, item);
                adapter.notifyDataSetChanged();*/

            }
        });

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                position--;

                double price = adapter.getItem(position).TotalPrice;
                totalAmount -= price;

                tvResult.setText(getActivity().getResources().getString(R.string.result) + " " +
                        totalAmount + getActivity().getResources().getString(R.string.rub));

                adapter.Remove(position);
                adapter.notifyDataSetChanged();

                DonePurcharesDb.deletePurchareItem(adapter.getItem(position), getActivity());

                return false;
            }
        });

    }


    private void setSupportActionBarTitle(double price)
    {
        DecimalFormat df = new DecimalFormat("#.##");
        MainActivity activity = (MainActivity) getActivity();
        activity.getSupportActionBar().setTitle(activity.getResources().getString(R.string.totalPrice) + " " +
                df.format(price) + " " + activity.getResources().getString(R.string.rub));
    }

    private void calcProductAmount()
    {
        DecimalFormat df = new DecimalFormat("#.##");

        String strCount = editProductCount.getText().toString();
        String strPricePerUnit = editPricePerUnit.getText().toString();

        try
        {
            double count = Double.parseDouble(strCount);
            double pricePerUnit = Double.parseDouble(strPricePerUnit);


            String resultValue = df.format(count * pricePerUnit) ;

            setSupportActionBarTitle(totalAmount + count * pricePerUnit);

            tvResult.setText( getActivity().getResources().getString(R.string.result) +
                            " " + getActivity().getResources().getString(R.string.current_product) +
                    " " + resultValue + " " +
                    getActivity().getResources().getString(R.string.rub) +  "\n" +
                            getActivity().getResources().getString(R.string.totalPrice)  + " " +
                    df.format( totalAmount + count * pricePerUnit ) +  getActivity().getResources().getString(R.string.rub)
            );

        }
        catch (Exception e){
            setSupportActionBarTitle(totalAmount);

            tvResult.setText(getActivity().getResources().getString(R.string.result) + " " +
                            df.format(totalAmount) + getActivity().getResources().getString(R.string.rub)
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
        editName = (AutoCompleteTextView) v.findViewById(R.id.editProductName);
        spn = (Spinner) v.findViewById(R.id.spnProductPersonType);
        editProductCount = (EditText) v.findViewById(R.id.editProductCount);
        editPricePerUnit = (EditText) v.findViewById(R.id.editPricePerUnit);
        tvResult = (TextView) v.findViewById(R.id.tvProductResult);
        btnOk = (Button) v.findViewById(R.id.btnProductOk);
        btnClear = (Button) v.findViewById(R.id.btnProductClear);

        setFocus(editName);

       List<String> products_array = ProductDb.getProductsNames(getActivity());
        ArrayAdapter<String> product_adapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, products_array);
        editName.setAdapter(product_adapter);

        editName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editProductCount.requestFocus();
            }
        });

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
            public void afterTextChanged(Editable s) {
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
            public void afterTextChanged(Editable s) {
                calcProductAmount();
            }
        });
    }

    private long currentId = -1;

    private void createProduct(String name, int productPersonType, double pricePerUnit, double count)
    {
        Activity context = getActivity();

        Product product = ProductDb.isProductExists(name, context);

        if ( product == null)
        {
            product = new Product();
            product.PricePerUnit = pricePerUnit;

            product.Name = name;

            product.Id = ProductDb.createProduct(product, context);
        }
        else
        {

            product.PricePerUnit = pricePerUnit;

            ProductDb.updateProduct(product, context);
        }


        PurchareItem item = new PurchareItem();
        item.DatePurchare = HelpUtils.getTimeMillsDay(System.currentTimeMillis());
        item.Product = product;
        item.Count = count;
        item.Type = productPersonType;
        item.TotalPrice = product.PricePerUnit * count;

        item.Id = DonePurcharesDb.createPurchareItem(item,context);
        adapter.Add(item);
        adapter.notifyDataSetChanged();

       /* PurchareItem item = new PurchareItem();
        item.setName(name);
        item.setPersonType(productPersonType);
        item.setPricePerUnit(pricePerUnit);
        item.setPriceTotal(count * pricePerUnit);
        item.setProductCount(count);
        item.setIsDone(Constants.PLAN);

        //adapter.Add(item);
        adapter.notifyDataSetChanged();*/
    }

    private void createListViewAlertDialog()
    {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());

        adb.setTitle(R.string.plann_purchare);
        /*final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.select_dialog_singlechoice, listPlannItems);*/
        final PlannProductsAdapter adapter = new PlannProductsAdapter(getActivity(), listPlannItems);

        adb.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int index)
            {
                dialog.dismiss();

                PurchareItem item = adapter.getItem(index);

                editName.setText(item.Product.Name);
                editProductCount.setText(item.Count +  "");

                listPlannItems.remove(index);
                adapter.notifyDataSetChanged();

                PlannPurchareDb.deletePlannPurchare(item, getActivity());

                editPricePerUnit.requestFocus();
                showKeyboard(getActivity());
            }
        });

        adb.create().show();
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


}
