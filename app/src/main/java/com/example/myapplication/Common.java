package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.myapplication.Contact.Contact;
import com.example.myapplication.Contact.ContactAdapter;
import com.example.myapplication.Gallery.GalleryAdapter;
import com.example.myapplication.Gallery.ImageFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Common {
    public static final String[] food_tags_id = {"tagKorean", "tagChinese", "tagJapanese", "tagItalian", "tagDessert", "tagETC"};
    public static final int[] food_tags_color = {R.drawable.checked_circle_korean, R.drawable.checked_circle_chinese, R.drawable.checked_circle_japanese, R.drawable.checked_circle_italian, R.drawable.checked_circle_dessert, R.drawable.checked_circle_etc};
    public static final String CONTACT_JSON_FILE_NAME = "contact.json";
    public static final String GALLERY_JSON_FILE_NAME = "gallery.json";
    public static Stack<Integer> stack_page;
    public static ArrayList<Contact> allContacts, mContactList, starContacts;
    public static ContactAdapter mAdapter;
    public static ArrayList<ImageFile> mGalleryList;
    public static GalleryAdapter galleryAdapter;

    // btn: 0: Home, 1: Contact, 2: Gallery, 3:Recommend, 4: ContactCreate, 5: ContactDetail, 6: ContactUpdate,
    // btn: 7: ClickedItem, 8: ClickedItemEdit, 9: FindFriends,  11:RecommendThird
    public static int currentTab = 0;
    public static int id_num = 0;

    public static int id_num_img = 0;

    public static void goIntent(int next_activity) {
        stack_page.push(next_activity);
    }

    public static void toPrev(Activity activity) {
        if(stack_page.size() <= 1) System.exit(0);
        stack_page.pop();

        int top = stack_page.peek();

        if(top == 0) currentTab = 0;

        if(top == 1 || top == 4 || top == 5 || top == 6) currentTab = 1;
        if(top == 2 || top == 7 || top == 8 || top == 9) currentTab = 2;
        if(top == 3 || top == 11) currentTab = 3;

        activity.finish();
    }

    public static void contactCopy(ArrayList<Contact> src, ArrayList<Contact> dest) {
        if(src != null && dest != null) {
            Collections.sort(src);

            dest.clear();
            dest.addAll(src);
        }
    }

    /*
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

     */

    public static JSONObject contactsToJson() {
        try {
            JSONObject retObject = new JSONObject();
            JSONArray arr = new JSONArray();

            for(Contact contact: allContacts) {
                JSONObject content = new JSONObject();

                content.put("id", Integer.toString(contact.getId()))
                        .put("name", contact.getName())
                        .put("phone", contact.getPhone())
                        .put("tags", contact.getTags())
                        .put("profileImage", contact.getProfileImage())
                        .put("isStar", Boolean.toString(contact.getIsStar()));

                if(contact.getLastMeet() == null) {
                    content.put("lastMeet", "");
                } else {
                    content.put("lastMeet", contact.getLastMeet());
                }

                arr.put(content);
            }
            retObject.put("contacts", arr);

            return retObject;
        } catch(JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static JSONObject galleryToJson() {
        try {
            JSONObject retObject = new JSONObject();
            JSONArray arr = new JSONArray();

            for(ImageFile imageFile: mGalleryList) {
                JSONObject content = new JSONObject();

                content.put("id", Integer.toString(imageFile.getId()))
                        .put("name", imageFile.getName())
                        .put("image", imageFile.getImage())
                        .put("tag", imageFile.getTag())
                        .put("date", imageFile.getDate())
                        .put("f1", imageFile.getF1())
                        .put("f2", imageFile.getF2())
                        .put("f3", imageFile.getF3())
                        .put("f4", imageFile.getF4());

                arr.put(content);
            }
            retObject.put("gallery", arr);

            return retObject;
        } catch(JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setTagsColor(TextView[] views, String checked) {
        String[] tagString = checked.replaceAll("[\\[\\]]", "").split(", ");
        for(int i = 0; i < 6; i++) {
            if(tagString[i].equals("true")){
                views[i].setBackgroundResource(food_tags_color[i]);
            }
        }
    }

//    public static ArrayList<ImageFile> initGallery() {
//        ArrayList<ImageFile> items = new ArrayList<>();
//
//        items.add(new ImageFile(0,"칵테일", R.drawable.image1, 4, "20180702", -1, -1, -1, -1));
//        items.add(new ImageFile(1,"휘낭시에", R.drawable.image2, 4, "20190701", -1, -1, -1, -1));
//        items.add(new ImageFile(2,"특등심까스", R.drawable.image3, 2, "20200627", -1, -1, -1, -1));
//        items.add(new ImageFile(3,"냉모밀", R.drawable.image4, 2, "20210404", -1, -1, -1, -1));
//        items.add(new ImageFile(4,"감자탕", R.drawable.image5, 0, "20211111", -1, -1, -1, -1));
//        items.add(new ImageFile(5,"감바스", R.drawable.image6, 3, "20211201", -1, -1, -1, -1));
//        items.add(new ImageFile(6,"고르곤졸라", R.drawable.image7, 3, "20211203", -1, -1, -1, -1));
//        items.add(new ImageFile(7,"샐러드", R.drawable.image8, 3, "20220110", -1, -1, -1, -1));
//        items.add(new ImageFile(8,"대한곱창", R.drawable.image9, 0, "20220212", -1, -1, -1, -1));
//        items.add(new ImageFile(9,"김치말이국수", R.drawable.image10, 0, "20220330", -1, -1, -1, -1));
//        items.add(new ImageFile(10,"라면", R.drawable.image11, 0, "20220524", -1, -1, -1, -1));
//        items.add(new ImageFile(11,"홍대카페", R.drawable.image12, 4, "20220611", -1, -1, -1, -1));
//        items.add(new ImageFile(12,"계란후라이", R.drawable.image14, 0, "20220629", -1, -1, -1, -1));
//        items.add(new ImageFile(13,"고등어구이", R.drawable.image15, 0, "20220701", -1, -1, -1, -1));
//        items.add(new ImageFile(14,"파전", R.drawable.image16, 0, "20220702", -1, -1, -1, -1));
//
//        return items;
//    }

}
