package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {

    private ArrayList<Contact> mContactList;
    private Contact_Adapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        mRecyclerView = findViewById(R.id.contact_rv);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        String contact_JSON = getJsonString();
        mContactList = jsonParsing(contact_JSON);
        mAdapter = new Contact_Adapter(mContactList);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    private String getJsonString() {
        String contacts = "";

        try {
            InputStream IS = getAssets().open("contact.json");
            int f_size = IS.available();

            byte[] buffer = new byte[f_size];
            IS.read(buffer);
            IS.close();

            contacts = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contacts;
    }

    private ArrayList<Contact> jsonParsing(String json) {
        try {
            ArrayList<Contact> newArrayList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray contactArray = jsonObject.getJSONArray("contacts");

            for(int i = 0; i < contactArray.length(); ++i) {
                JSONObject contactObj = contactArray.getJSONObject(i);

                int id = Integer.parseInt(contactObj.getString("id"));
                String name = contactObj.getString("name");
                String phone = parsePhone(contactObj.getString("phone"));

                Contact data = new Contact(id, name, phone);

                newArrayList.add(data);
            }

            return newArrayList;
        } catch(JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String parsePhone(String phone) {
        if(phone == null) return "";

        return phone.replaceFirst("^([0-9]{3})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
    }
}
