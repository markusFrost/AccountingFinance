package com.example.hello.com.myapplicationest1.Activities;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hello.com.myapplicationest1.Fragments.ListPlannItemsFragment;
import com.example.hello.com.myapplicationest1.Fragments.ProductDetailFragment;
import com.example.hello.com.myapplicationest1.R;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        FragmentManager fragmentManager = getFragmentManager();

        ProductDetailFragment fragment = new ProductDetailFragment();

        Bundle b = getIntent().getExtras();

        fragment.setArguments(b);

        fragmentManager.beginTransaction()
                .replace(R.id.productDetailFragment, fragment ).commit();

    }
}
