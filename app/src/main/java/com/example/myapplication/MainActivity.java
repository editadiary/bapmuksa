package com.example.myapplication;

import static com.example.myapplication.Common.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayDeque;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        stack_page = new Stack<Integer>();
        stack_page.push(0);
        getContacts();
    }

    private void getContacts() {
        String contact_JSON_str = getJsonString(this, CONTACT_JSON_FILE_NAME);
        mContactList = parseContact(contact_JSON_str);
    }
}