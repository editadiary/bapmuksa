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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

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
    private ImageView addContactIcon, searchIcon;
    private LinearLayout searchLinearLayout;
    private EditText searchET;
    private Spinner tagSpinner;
    private boolean searchVisible = false;
    private static RecyclerView mRecyclerView;
    private static ContactAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        addContactIcon = findViewById(R.id.ic_plus);
        addContactIcon.setOnClickListener(this);

        searchIcon = findViewById(R.id.searchBtn);
        searchIcon.setOnClickListener(this);

        searchLinearLayout = findViewById(R.id.searchBar);

        searchET = findViewById(R.id.searchEditText);
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchContact(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tagSpinner = findViewById(R.id.spinnerTag);

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
        if(v.getId() == R.id.ic_plus) {
            if(stack_page.peek() == 4) return;

            stack_page.push(4);
            Intent intent = new Intent(this, ContactCreateActivity.class);
            startActivity(intent);
        }

        if(v.getId() == R.id.searchBtn) {
            if(searchVisible == false) {
                searchLinearLayout.setVisibility(View.VISIBLE);
            }
            else {
                searchLinearLayout.setVisibility(View.GONE);
            }
        }
    }

    private void searchContact(CharSequence s) {
        String searchText = s.toString();

    }

    @Override
    public void onBackPressed() {
        Common.toPrev(this);
    }

}