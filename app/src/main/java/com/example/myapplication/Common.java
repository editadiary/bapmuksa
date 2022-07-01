package com.example.myapplication;

import android.content.Context;

import com.example.myapplication.Contact.Contact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Common {
    public static ArrayList<Contact> mContactList;
    public static int id_num = 12;
    public static int btn = 0;

    public static String getJsonString(Context context, String fileName) {
        String str = "";

        try {
            InputStream IS = context.getAssets().open(fileName);
            int f_size = IS.available();

            byte[] buffer = new byte[f_size];
            IS.read(buffer);
            IS.close();

            str = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return str;
    }

    public static ArrayList<Contact> parseContact(String json) {
        try {
            ArrayList<Contact> newArrayList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray contactArray = jsonObject.getJSONArray("contacts");

            for(int i = 0; i < contactArray.length(); ++i) {
                JSONObject contactObj = contactArray.getJSONObject(i);

                String name = contactObj.getString("name");
                String phone = contactObj.getString("phone");

                Contact data = new Contact(name, phone);

                newArrayList.add(data);
            }

            return newArrayList;
        } catch(JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
