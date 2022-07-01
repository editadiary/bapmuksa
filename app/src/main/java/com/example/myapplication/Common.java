package com.example.myapplication;

import android.content.Context;

import com.example.myapplication.Contact.Contact;
import com.example.myapplication.Contact.ContactAdapter;
import com.example.myapplication.Gallery.GalleryAdapter;
import com.example.myapplication.Gallery.ImageFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Stack;


public class Common {
    public static final String CONTACT_JSON_FILE_NAME = "contact.json";
    public static Stack<Integer> stack_page;
    public static ArrayList<Contact> mContactList;
    public static ContactAdapter mAdapter;
    public static int currentTab = 0;
    public static ArrayList<ImageFile> mGalleryList;
    public static GalleryAdapter galleryAdapter;
    public static int id_num = 12;

    // btn: 0: Home, 1: Contact, 2: Gallery, 3:IDK, 4: Contact Create, 5: Contact Detail, 6: Contact Update

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

    public static ArrayList<ImageFile> initGallery() {
        ArrayList<ImageFile> items = new ArrayList<>();
        items.add(new ImageFile("image1", R.drawable.image1));
        items.add(new ImageFile("image2", R.drawable.image2));
        items.add(new ImageFile("image3", R.drawable.image3));
        items.add(new ImageFile("image4", R.drawable.image4));
        items.add(new ImageFile("image5", R.drawable.image5));
        items.add(new ImageFile("image6", R.drawable.image6));
        items.add(new ImageFile("image7", R.drawable.image7));
        items.add(new ImageFile("image8", R.drawable.image8));
        items.add(new ImageFile("image9", R.drawable.image9));
        items.add(new ImageFile("image10", R.drawable.image10));
        items.add(new ImageFile("image11", R.drawable.image11));
        items.add(new ImageFile("image12", R.drawable.image12));
        items.add(new ImageFile("image13", R.drawable.image13));
        items.add(new ImageFile("image14", R.drawable.image14));
        items.add(new ImageFile("image15", R.drawable.image15));
        items.add(new ImageFile("image16", R.drawable.image16));

        return items;

    }

}
