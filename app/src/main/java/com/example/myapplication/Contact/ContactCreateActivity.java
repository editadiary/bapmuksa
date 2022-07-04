package com.example.myapplication.Contact;

import static com.example.myapplication.Common.*;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Common;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;

public class ContactCreateActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edit_name, edit_phone1, edit_phone2, edit_phone3;
    private TextView[] tagTV;
    private boolean[] tags;
    private int[] tagsID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_create);

        ImageView addBtn, plusTag;

        tags = new boolean[6];
        tagTV = new TextView[6];
        tagsID = new int[6]; getTagId();
// R.id.id_name
        for(int i = 0; i < 6; ++i) {
            tags[i] = false;
            tagTV[i] = findViewById(tagsID[i]);
            tagTV[i].setOnClickListener(this);
        }
        edit_name = findViewById(R.id.create_name_edit);
        edit_phone1 = findViewById(R.id.create_phone_edit1);
        edit_phone2 = findViewById(R.id.create_phone_edit2);
        edit_phone3 = findViewById(R.id.create_phone_edit3);

        addBtn = findViewById(R.id.ic_check);
        addBtn.setOnClickListener(this);
    }

    private void getTagId() {
        for(int i = 0; i < 6; ++i)
            tagsID[i] = getResources().getIdentifier(food_tags_id[i], "id", getPackageName());
    }

    private void AddContact(Contact new_contact) {
        allContacts.add(new_contact);
        contactsWrite();
        contactCopy(allContacts, mContactList);
        mAdapter.notifyDataSetChanged();
    }

    private void contactsWrite() {
        try{
            FileOutputStream os = openFileOutput(CONTACT_JSON_FILE_NAME, MODE_PRIVATE);
            JSONObject jsonFile = contactsToJson();

            assert jsonFile != null;
            os.write(jsonFile.toString().getBytes());
            os.flush();
            os.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.ic_check) {
            String name = edit_name.getText().toString();
            String phone1 = edit_phone1.getText().toString();
            String phone2 = edit_phone2.getText().toString();
            String phone3 = edit_phone3.getText().toString();

            if(name.equals("")){
                Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }

            else if(!phone1.equals("010") || phone2.length() != 4 || phone3.length() != 4) {
                Toast.makeText(this, "전화번호를 입력해주세요.", Toast.LENGTH_LONG).show();
            }
            
            else{
                String phone = phone1 + "-" + phone2 + "-" + phone3;

                stack_page.push(1);

                Log.d("tags__", Arrays.toString(tags));
                AddContact(new Contact(id_num++, name, phone, Arrays.toString(tags), "ic_person", null));
                Intent intent = new Intent(getApplicationContext(), ContactDetailActivity.class);
                intent.putExtra("name", name)
                        .putExtra("phone", phone)
                        .putExtra("tags", Arrays.toString(tags))
                        .putExtra("id", id);
                setResult(100, intent);
                finish();
            }
        }

        for(int i = 0; i < 6; ++i) {
            if(id == tagsID[i]) {
                if(tags[i]) {
                    tags[i] = false;
                    v.setBackgroundResource(R.drawable.circle);
                }
                else {
                    tags[i] = true;
                    v.setBackgroundResource(food_tags_color[i]);
                }
            }
        }
    }

    /*
    public int pxToDp(final int px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, getResources().getDisplayMetrics());
    }
     */

    @Override
    public void onBackPressed() {
        Common.toPrev(this);
    }
}
