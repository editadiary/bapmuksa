package com.example.myapplication.Contact;

import static com.example.myapplication.Common.mContactList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Common;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {
    public static final String CONTACT_JSON_FILE_NAME = "contact.json";
    private static ContactAdapter mAdapter;
    private static RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        mRecyclerView = findViewById(R.id.contact_rv);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        RVSetList(mContactList);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    public static void RVSetList(ArrayList<Contact> list) {
        mAdapter = new ContactAdapter(list);
        if(mRecyclerView.getAdapter() == null) {
            mRecyclerView.setAdapter(mAdapter);
        } else{
            mRecyclerView.swapAdapter(mAdapter, false);
        }
    }

    public void addClick(View view) {
        Common.btn = 4;
        Intent intent = new Intent(getApplicationContext(), ContactCreateActivity.class);
        startActivity(intent);
    }


}