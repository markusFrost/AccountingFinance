package com.example.hello.com.myapplicationest1.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hello.com.myapplicationest1.R;

import java.text.DecimalFormat;


public class ProductDetailFragment extends Fragment
{
    EditText editName, editProductCount, editPricePerUnit;
    Spinner spn;
    TextView tvResult;
    Button btnOk, btnClear;

    double totalAmount = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.product_detail_item, container, false);

        fillView(v);


        return v;
    }

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

        showKeyboard(getActivity() );
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
}
