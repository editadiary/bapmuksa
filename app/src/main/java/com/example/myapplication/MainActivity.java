package com.example.myapplication;

import static com.example.myapplication.Common.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.example.myapplication.Contact.Contact;
import com.example.myapplication.Gallery.GalleryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getExternalStorage();
        stack_page = new Stack<>(); stack_page.push(0);
        getContacts();
        initGallery();
    }

    private void getExternalStorage() {
        String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)) {
            isAvailable = true;
            isReadable = true;
            isWriteable = true;
        }
    }

    private void getContacts() {
        String contact_JSON_str = getJsonString(this, CONTACT_JSON_FILE_NAME);
        mContactList = parseContact(contact_JSON_str);

        Collections.sort(mContactList);
    }

    private ArrayList<Contact> parseContact(String json) {
        try {
            ArrayList<Contact> newArrayList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray contactArray = jsonObject.getJSONArray("contacts");

            for(int i = 0; i < contactArray.length(); ++i) {
                JSONObject contactObj = contactArray.getJSONObject(i);

                String name = contactObj.getString("name");
                String phone = contactObj.getString("phone");
                boolean[] tags = new boolean[6];
                JSONArray tags_array = contactObj.getJSONArray("tags");

                for(int j = 0; j < 6; ++j) {
                    tags[j] = tags_array.getBoolean(j);
                }

                Contact data = new Contact(Integer.toString(id_num), name, phone, tags, "ic_person", null); ++id_num;

                newArrayList.add(data);
            }

            return newArrayList;
        } catch(JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void initGallery() {
        mGalleryList = Common.initGallery();
        galleryAdapter = new GalleryAdapter(mGalleryList, this);

    }
}