package com.example.hello.com.myapplicationest1.Activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.hello.com.myapplicationest1.Fragments.ListPlannItemsFragment;
import com.example.hello.com.myapplicationest1.Fragments.ListPurchasesFragment;
import com.example.hello.com.myapplicationest1.Objects.Extras;
import com.example.hello.com.myapplicationest1.R;

public class DoneItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);

       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        //setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");

        Intent intent = getIntent();

        if ( intent != null)
        {
            //long time = intent.getLongExtra(Extras.PURCHARE_DATE, 0);

            FragmentManager fragmentManager = getFragmentManager();

            ListPurchasesFragment fragment = new ListPurchasesFragment();

            Bundle b = getIntent().getExtras();

            fragment.setArguments(b);

            fragmentManager.beginTransaction()
                    .replace(R.id.listPlanFragment, fragment).commit();
        }


    }
}
