package com.example.myapplication;

import static com.example.myapplication.Common.*;
import static com.example.myapplication.Contact.ContactActivity.CONTACT_JSON_FILE_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.Gallery.GalleryAdapter;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getContacts();
        initGallery();
    }

    private void getContacts() {
        String contact_JSON_str = getJsonString(this, CONTACT_JSON_FILE_NAME);
        mContactList = parseContact(contact_JSON_str);
    }

    private void initGallery() {
        mGalleryList = Common.initGallery();
        galleryAdapter = new GalleryAdapter(mGalleryList, this);

    }
}