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
    private static RecyclerView mRecyclerView;
    private static ContactAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        setOnClickListener();
        mRecyclerView = findViewById(R.id.contact_rv);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        RVSetList(mContactList);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    public static void RVSetList(ArrayList<Contact> list) {
        ContactAdapter mAdapter = new ContactAdapter(list, listener);
        if(mRecyclerView.getAdapter() == null) {
            mRecyclerView.setAdapter(mAdapter);
        } else{
            mRecyclerView.swapAdapter(mAdapter, false);
        }
    }

    private void setOnClickListener() {
        listener = (v, position) -> {
            Common.btn = 5;
            Intent intent = new Intent(getApplicationContext(), ContactDetailActivity.class);
            intent.putExtra("name", mContactList.get(position).getName())
                    .putExtra("phone", mContactList.get(position).getPhone())
                            .putExtra("pos", Integer.toString(position));

            startActivity(intent);
        };
    }

    public void addClick(View view) {
        Common.btn = 4;
        Intent intent = new Intent(getApplicationContext(), ContactCreateActivity.class);
        startActivity(intent);
    }


}