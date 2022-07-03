package com.example.myapplication.Contact;

import static com.example.myapplication.Common.mContactList;
import static com.example.myapplication.Common.mAdapter;
import static com.example.myapplication.Common.stack_page;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Common;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView addContactIcon;
    private static RecyclerView mRecyclerView;
    private static ContactAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        addContactIcon = findViewById(R.id.ic_plus);
        addContactIcon.setOnClickListener(this);

        mRecyclerView = findViewById(R.id.contact_rv);
        setOnClickListener();

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new ContactAdapter(mContactList, listener);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void setOnClickListener() {
        listener = (v, position) -> {
            if(stack_page.peek() == 5) return;

            stack_page.push(5);
            Intent intent = new Intent(this, ContactDetailActivity.class);
            Log.d("tags____", mContactList.get(position).getTags().toString());
            intent.putExtra("name", mContactList.get(position).getName())
                    .putExtra("phone", mContactList.get(position).getPhone())
                    .putExtra("tags", mContactList.get(position).getTags())
                    .putExtra("pos", Integer.toString(position));

            startActivity(intent);
        };
    }

    @Override
    public void onClick(View v) {
        if(stack_page.peek() == 4) return;

        stack_page.push(4);
        Intent intent = new Intent(this, ContactCreateActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Common.toPrev(this);
    }

}