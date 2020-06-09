package com.example.gdrivebackup;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity
{
    ArrayList<String> custIdList , custNameList;
    ListView listView;

    DemoSQLite demoSQLite;

    EditText searchText;

    SimpleAdapter adapter;

    LinkedHashMap<String, String> nameContact = new LinkedHashMap<>();

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
            String custId = result.getString(0);
            custIdList.add(custId);

            String custName = result.getString(1);
            custNameList.add(custName);

            nameContact.put(custId, custName);
        }

    }

    public void showList()
    {
        //adapter = new CustomAdapter(this, custIdList, custNameList);
        List<LinkedHashMap<String, String>> listItems = new ArrayList<>();
        adapter = new SimpleAdapter(this, listItems, R.layout.item_subitem, new String[]{"ID", "Name"}, new int[]{R.id.sampleCustId, R.id.sampleCustName});

        Iterator it = nameContact.entrySet().iterator();

        while (it.hasNext())
        {
            LinkedHashMap<String, String> nameContactMap = new LinkedHashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            nameContactMap.put("ID", pair.getKey().toString());
            nameContactMap.put("Name", pair.getValue().toString());
            listItems.add(nameContactMap);
        }

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
                    //listView.invalidateViews();
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
                String requiredId = null;

                Pattern p = Pattern.compile("ID=(.*?),");
                Matcher m = p.matcher(adapter.getItem(i).toString());

                while(m.find())
                {
                    requiredId = m.group(1);
                }
                Log.i("Item", requiredId);
            }
        });
    }
}
