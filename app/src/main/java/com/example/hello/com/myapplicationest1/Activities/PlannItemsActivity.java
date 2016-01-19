package com.example.hello.com.myapplicationest1.Activities;

import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.hello.com.myapplicationest1.Fragments.ListPlannItemsFragment;
import com.example.hello.com.myapplicationest1.R;

public class PlannItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);

        FragmentManager fragmentManager = getFragmentManager();

        ListPlannItemsFragment fragment = new ListPlannItemsFragment();

        Bundle b = getIntent().getExtras();

        fragment.setArguments(b);

        fragmentManager.beginTransaction()
                .replace(R.id.listPlanFragment, fragment ).commit();


    }
}
