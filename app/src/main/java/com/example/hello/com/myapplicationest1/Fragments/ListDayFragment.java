package com.example.hello.com.myapplicationest1.Fragments;


import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.hello.com.myapplicationest1.Activities.DoneItemsActivity;
import com.example.hello.com.myapplicationest1.Adapters.DayAdapter;
import com.example.hello.com.myapplicationest1.Databases.DonePurcharesDb;
import com.example.hello.com.myapplicationest1.Models.DayDto;
import com.example.hello.com.myapplicationest1.Objects.Extras;

public class ListDayFragment extends ListFragment
{

    DayAdapter adapter;
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        adapter = new DayAdapter(getActivity(), DonePurcharesDb.getDaysDayDto(getActivity()));

        setListAdapter(adapter);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DayDto item = adapter.getItem(position);

                Intent intent = new Intent(getActivity(), DoneItemsActivity.class);
                intent.putExtra(Extras.PURCHARE_DATE, item.Time);
                startActivity(intent);

            }
        });

    }
}
