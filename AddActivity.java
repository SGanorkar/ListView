package com.example.gdrivebackup;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity
{
    DemoSQLite demoSQLite;

    EditText custId, custName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        demoSQLite =new DemoSQLite(this);

        custId = findViewById(R.id.custId);
        custName = findViewById(R.id.custName);
    }

    public void AddData(View view)
    {
        demoSQLite.insertData(custId.getText().toString(), custName.getText().toString());

        Toast.makeText(this, "Entry Added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent  = new Intent(AddActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
