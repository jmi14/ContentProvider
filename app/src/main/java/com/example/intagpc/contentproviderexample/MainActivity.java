package com.example.intagpc.contentproviderexample;

import android.Manifest;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listViewContacts;
    private ArrayList arrayList = new ArrayList();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initWidgets();
        requirePermission();
    }

    public void initWidgets() {

        listViewContacts = (ListView) findViewById(R.id.litviewContacts);
    }

    public void requirePermission(){
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_CONTACTS},1);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1){

            getContacts();
        }
        else{
            Toast.makeText(MainActivity.this,"permission deny",Toast.LENGTH_SHORT).show();
        }
    }

    public void getContacts(){
        Cursor cursor = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null);
        }
        while (cursor.moveToNext()) {

            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            arrayList.add(name);
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,arrayList);
        listViewContacts.setAdapter(arrayAdapter);

    }
}
