package com.example.hello.com.myapplicationest1.activities;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hello.com.myapplicationest1.Fragments.ListPlannItemsFragment;
import com.example.hello.com.myapplicationest1.R;

public class HeaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);

        FragmentManager fragmentManager = getFragmentManager();

        ListPlannItemsFragment fragment = new ListPlannItemsFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.listPlanFragment, fragment ).commit();
    }
}
