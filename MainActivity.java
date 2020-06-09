package com.example.gdrivebackup;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    ArrayList<String> custIdList , custNameList;
    ListView listView;

    DemoSQLite demoSQLite;

    EditText searchText;

    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        demoSQLite = new DemoSQLite(this);

        custIdList = new ArrayList<String>();
        custNameList = new ArrayList<String>();

        listView = findViewById(R.id.listView);
        listView.setTextFilterEnabled(true);

        searchText = findViewById(R.id.searchText);

        getDetails();

        showList();

        search();

        itemInteraction();
    }


    public void AddWindow(View view)
    {
        Intent intent  = new Intent(MainActivity.this, AddActivity.class);
        startActivity(intent);
    }

    public void getDetails()
    {
        Cursor result = demoSQLite.getData();

        if(result.getCount() == 0)
        {
            Toast.makeText(this, "No data Found", Toast.LENGTH_SHORT).show();
        }

        while(result.moveToNext())
        {
            custIdList.add(result.getString(0));
            custNameList.add(result.getString(1));
        }

    }

    public void showList()
    {
        adapter = new CustomAdapter(this, custIdList, custNameList);
        listView.setAdapter(adapter);
    }

    public void search()
    {
        searchText = (EditText) findViewById(R.id.searchText);

        if (custNameList.size() != 0)
        {
            searchText.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    adapter.getFilter().filter(charSequence);
                    listView.invalidateViews();
                }

                @Override
                public void afterTextChanged(Editable editable)
                {

                }
            });
        }
        else
        {
            Toast.makeText(this, "No Entry", Toast.LENGTH_SHORT).show();
        }
    }

    public void itemInteraction()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Toast.makeText(MainActivity.this, custNameList.get(i), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
