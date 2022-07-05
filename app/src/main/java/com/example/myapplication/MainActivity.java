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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        currentTab = 0;
        allContacts = new ArrayList<>();
        mContactList = new ArrayList<>();
        stack_page = new Stack<>(); stack_page.push(0);
        getContacts();
        initGallery();
    }

    private void findMaxIdNum() {
        int maxId = -1;
        for(Contact contact: allContacts) {
            if(maxId < contact.getId())
                maxId = contact.getId();
        }
        id_num = maxId+1;
    }

    private void getContacts() {
        String contactsJsonString = readContactJson();
        if(!contactsJsonString.equals("")) {
            allContacts = parseContact(contactsJsonString);
            contactCopy(allContacts, mContactList);
        }
        findMaxIdNum();
        // String contactsJsonString = getJsonString(this, CONTACT_JSON_FILE_NAME);
    }


    private String readContactJson() {
        try {
            File file = getFileStreamPath(CONTACT_JSON_FILE_NAME);

            if(file.exists()) {
                FileInputStream is = openFileInput(CONTACT_JSON_FILE_NAME);
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();

                while(true) {
                    String line = bufferedReader.readLine();
                    if(line == null) break;
                    sb.append(line).append("\n");
                }

                return sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
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
                String tags = contactObj.getString("tags");
                String profileImage = contactObj.getString("profileImage");
                String lastMeet = contactObj.getString("lastMeet");
                Log.d("lastMeet", lastMeet);

                Contact data = new Contact(id_num++, name, phone, tags, profileImage, lastMeet);

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