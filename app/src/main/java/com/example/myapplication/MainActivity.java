package com.example.myapplication;

import static com.example.myapplication.Common.*;
import static com.example.myapplication.Contact.ContactActivity.CONTACT_JSON_FILE_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getContacts();
    }

    private void getContacts() {
        String contact_JSON_str = getJsonString(this, CONTACT_JSON_FILE_NAME);
        mContactList = parseContact(contact_JSON_str);
    }
}