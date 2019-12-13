package com.gruppo3.eissmsproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.gruppo3.smsconnection.replicatednet.manager.ReplicatedNetManager;

import java.util.Iterator;
import java.util.Map;

public class NetActivity extends AppCompatActivity {

    ReplicatedNetManager<String, String> netManager;
    ListView listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);

        listData = findViewById(R.id.listViewData);
        netManager = ReplicatedNetManager.getDefault();

        updateDataListView();

    }


    private void updateDataListView() {
        Iterator<Map.Entry<String, String>> iterator = netManager.getDictionary().getResourcesIterator();
        if (iterator.hasNext()) {
            Map.Entry<String, String>[] arrayData = new Map.Entry[netManager.getDictionary().numberOfResources()];


            int i = 0;
            while (iterator.hasNext()) {
                Map.Entry<String, String> a = iterator.next();
                arrayData[i++] = a;
            }

            ArrayAdapter<Map.Entry<String, String>> arrayAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    arrayData);

            listData.setAdapter(arrayAdapter);
        }
    }

    public void onAddResourceButtonClick(View view) {

        Intent callAddResourceActivityIntent = new Intent(this, AddResourceActivity.class);

        startActivity(callAddResourceActivityIntent);
    }

    public void onAddPeerButtonClick(View view) {

        Intent callAddResourceActivityIntent = new Intent(this, AddPeerActivity.class);

        startActivity(callAddResourceActivityIntent);
    }

    public void onRefreshButtonClick(View view) {

       updateDataListView();
    }
}
