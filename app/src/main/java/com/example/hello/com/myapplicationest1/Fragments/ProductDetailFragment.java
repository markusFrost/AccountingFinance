package com.example.hello.com.myapplicationest1.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hello.com.myapplicationest1.R;


public class ProductDetailFragment extends Fragment
{
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.product_detail_item, container, false);

//макте готов. Осталось его заполнить, проинициализировать, сделать проверку на валидацию а затем отправить в одительскую

        return v;
    }
}
