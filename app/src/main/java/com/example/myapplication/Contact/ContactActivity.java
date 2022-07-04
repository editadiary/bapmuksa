package com.example.myapplication.Contact;

import static com.example.myapplication.Common.*;

import android.Manifest;
import android.annotation.SuppressLint;
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
import com.example.myapplication.Recommend.FoodSpinnerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ContactActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<String> foodTags;
    private Spinner foodSpinner;
    private SearchFoodSpinnerAdapter foodAdapter;
    private ImageView addContactIcon, searchIcon;
    private LinearLayoutManager mLinearLayoutManager;
    private LinearLayout searchLinearLayout;
    private EditText searchET;
    private boolean searchVisible = false;
    private static RecyclerView mRecyclerView;
    private static ContactAdapter.RecyclerViewClickListener listener;
    private int tagPosition;
    private String searchString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        initVariables();
        initTags();
        initViews();
        initSetting();
        initListeners();
        initDecoration();
    }

    private void initTags() {
        final String[] foods = {"전체", "한식", "중식", "일식", "양식", "후식", "기타"};
        int sz = foods.length;

        for(int i = 0; i < sz; ++i) {
            foodTags.add(foods[i]);
        }
    }

    private void initVariables() {
        foodTags = new ArrayList<>();
        foodAdapter = new SearchFoodSpinnerAdapter(this, foodTags);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        initRecyclerViewListener();
        contactCopy(allContacts, mContactList);

        mAdapter = new ContactAdapter(mContactList, listener);
    }

    private void initRecyclerViewListener() {
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

    private void initViews() {
        mRecyclerView = findViewById(R.id.contact_rv);

        foodSpinner = findViewById(R.id.searchTag);
        foodSpinner.setAdapter(foodAdapter);

        searchLinearLayout = findViewById(R.id.searchBar);
        searchIcon = findViewById(R.id.searchBtn);
        searchET = findViewById(R.id.searchEditText);

        addContactIcon = findViewById(R.id.ic_plus);
    }

    private void initSetting() {
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListeners() {
        addContactIcon.setOnClickListener(this);
        searchIcon.setOnClickListener(this);

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

        foodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    }

    private void initDecoration(){
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }


    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    private ArrayList<Contact> getFoundContacts() {
        ArrayList<Contact> newContacts = new ArrayList<>();
        contactCopy(allContacts, newContacts);

        ArrayList<Contact> filteredByName = filterByName(newContacts);
        ArrayList<Contact> resContacts = filterByTag(filteredByName);

        return resContacts;
    }

    @SuppressLint("NewApi")
    private ArrayList<Contact> filterByName(ArrayList<Contact> contacts) {
        if(searchString == null || searchString == "") {
            return contacts;
        }

        return (ArrayList<Contact>) contacts.stream().filter(contact -> {
            String name = contact.getName();
            String phone = parsePhoneNumber(contact.getPhone());

            return name.contains(searchString) || phone.contains(searchString);
        }).collect(Collectors.toList());
    }

    @SuppressLint("NewApi")
    private ArrayList<Contact> filterByTag(ArrayList<Contact> contacts) {
        if(tagPosition == 0) {
            return contacts;
        }

        return (ArrayList<Contact>) contacts.stream().filter(contact -> {
            String[] tagString = contact.getTags().replaceAll("[\\[\\]]", "").split(", ");

            return Boolean.parseBoolean(tagString[tagPosition-1]);
        }).collect(Collectors.toList());
    }

    private String parsePhoneNumber(String phoneNumber) {
        return phoneNumber.replaceAll("^([0-9]{3})-([0-9]{4})-([0-9]{4})$", "$1$2$3");
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
                foodSpinner.setSelection(0);
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