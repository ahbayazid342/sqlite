package com.example.sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etFirstName;
    private EditText etSureName;

    private TextView tvShow;

    private Button btnSave, btnShow, btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFirstName = findViewById(R.id.etFirstName);
        etSureName = findViewById(R.id.etSureName);
        tvShow = findViewById(R.id.tvShow);

        btnSave = findViewById(R.id.btnSave);
        btnShow = findViewById(R.id.btnShow);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);

        UserDB userDB = new UserDB(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName = etFirstName.getText().toString().trim();
                String sName = etSureName.getText().toString().trim();

                boolean insertCheck = userDB.insertData(fName, sName);

                if (insertCheck){
                    Toast.makeText(MainActivity.this, "insert", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "not insert", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor res = userDB.getData();

                if (res.getCount() == 0){
                    Toast.makeText(MainActivity.this, "no data", Toast.LENGTH_LONG).show();
                    return;
                }

                StringBuffer sf = new StringBuffer();

                while (res.moveToNext()){
                    sf.append("Name " + res.getString(0) + "\n");
                    sf.append("Sure Name " + res.getString(1) + "\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("User Info...");
                builder.setMessage(sf.toString());
                builder.show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etFirstName.getText().toString().trim();
                String sureName = etSureName.getText().toString().trim();

                boolean up = userDB.updateData(name, sureName);
                if (up){
                    Toast.makeText(MainActivity.this, "update", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "not update", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etFirstName.getText().toString().trim();

                boolean del = userDB.deleteData(name);
                if (del){
                    Toast.makeText(MainActivity.this, "deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "not dxeleted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}