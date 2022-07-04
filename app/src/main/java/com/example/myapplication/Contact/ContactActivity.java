package com.example.myapplication.Contact;

import static com.example.myapplication.Common.*;

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
import android.widget.AdapterView;
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
    private String searchString = null;
    private int tagPosition = -1;
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

        contactCopy(allContacts, mContactList);
        searchET = findViewById(R.id.searchEditText);
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchString = s.toString();
                ArrayList<Contact> found = getFoundContacts();
                contactCopy(found, mContactList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tagSpinner = findViewById(R.id.spinnerTag);

        tagSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tagPosition = position;
                ArrayList<Contact> found = getFoundContacts();
                contactCopy(found, mContactList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mRecyclerView = findViewById(R.id.contact_rv);
        setOnClickListener();

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new ContactAdapter(mContactList, listener);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    private ArrayList<Contact> getFoundContacts() {
        ArrayList<Contact> newContact = new ArrayList<>();

        if(tagPosition == -1) {
            return allContacts;
        }
        else {
            for(Contact contact: allContacts) {
                String parsedPhoneNumber = parsePhoneNumber(contact.getPhone());
                if(contact.getTags()[tagPosition]) {
                    if(searchString == null || ((contact.getName().contains(searchString) || parsedPhoneNumber.contains(searchString)))){
                        newContact.add(contact);
                    }
                }
            }
            return newContact;
        }
    }

    private String parsePhoneNumber(String phoneNumber) {
        return phoneNumber.replaceAll("^([0-9]{3})-([0-9]{4})-([0-9]{4})$", "$1$2$3");
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
                tagPosition = 0;
                searchVisible = true;
                searchLinearLayout.setVisibility(View.VISIBLE);
            }
            else {
                Log.d("1231231231", allContacts.toString());
                contactCopy(allContacts, mContactList);
                searchString=null;
                tagPosition = -1;
                searchET.setText("");
                tagSpinner.setSelection(0);
                searchVisible = false;
                searchLinearLayout.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Common.toPrev(this);
    }

}