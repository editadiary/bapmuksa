package com.example.myapplication;

import static com.example.myapplication.Common.allContacts;
import static com.example.myapplication.Common.contactCopy;
import static com.example.myapplication.Common.galleryAdapter;
import static com.example.myapplication.Common.mAdapter;
import static com.example.myapplication.Common.mContactList;
import static com.example.myapplication.Common.mGalleryList;
import static com.example.myapplication.Common.stack_page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.myapplication.Contact.Contact;
import com.example.myapplication.Contact.ContactAdapter;

import java.util.ArrayList;
import com.example.myapplication.Common;
import com.example.myapplication.Contact.ContactCreateActivity;
import com.example.myapplication.Contact.ContactDetailActivity;
import com.example.myapplication.Gallery.ClickedItemActivity;
import com.example.myapplication.Gallery.ClickedItemEditActivity;
import com.example.myapplication.Gallery.GalleryMainActivity;
import com.example.myapplication.Gallery.ImageFile;


public class FindFriendsActivity extends AppCompatActivity implements View.OnClickListener{

    ClickedItemEditActivity clickedItemEditActivity = (ClickedItemEditActivity) ClickedItemEditActivity.clickedItemEditActivity;

    private ImageView searchIcon;
    private LinearLayout searchLinearLayout;
    private EditText searchET;
    private Spinner tagSpinner;
    private String searchString = null;
    private int tagPosition = -1;
    private boolean searchVisible = false;
    private static RecyclerView mRecyclerView;
    private static ContactAdapter.RecyclerViewClickListener listener;

    int idx = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        searchIcon = findViewById(R.id.searchBtn);
        searchIcon.setOnClickListener(this);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            idx = intent.getIntExtra("index", 0);
        }

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
                String[] stringTag = contact.getTags().replaceAll("[\\[\\]]", "").split(", ");

                if(Boolean.parseBoolean(stringTag[tagPosition])) {
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
            clickedItemEditActivity.finish();

            ImageFile image = mGalleryList.get(idx);
            image.addFriend(position);

            mGalleryList.set(idx, image);
            galleryAdapter.notifyDataSetChanged();

            Contact contact = mContactList.get(position);
            contact.setLastMeet(image.getDate());
            mContactList.set(position, contact);
            mAdapter.notifyDataSetChanged();

            startActivity(new Intent(FindFriendsActivity.this,
                    ClickedItemEditActivity.class).putExtra("index", idx));

            finish();

        };
    }

    @Override
    public void onClick(View v) {

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