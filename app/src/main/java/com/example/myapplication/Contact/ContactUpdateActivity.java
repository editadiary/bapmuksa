package com.example.myapplication.Contact;

import static com.example.myapplication.Common.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Common;
import com.example.myapplication.R;

import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class ContactUpdateActivity extends AppCompatActivity implements View.OnClickListener {
    int contactId, allPosition;
    Contact contact;
    String pos = "-1";
    EditText editName, editPhone1, editPhone2, editPhone3;
    private boolean[] tag = new boolean[6];
    private int[] tagsID;
    ImageView check;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_update);

        editName = findViewById(R.id.update_name_edit);
        editPhone1 = findViewById(R.id.update_phone_edit1);
        editPhone2 = findViewById(R.id.update_phone_edit2);
        editPhone3 = findViewById(R.id.update_phone_edit3);
        check = findViewById(R.id.ic_check);

        TextView[] tagTV = new TextView[6];
        tagsID = new int[6]; getTagId();

        for(int i = 0; i < 6; ++i) {
            tagTV[i] = findViewById(tagsID[i]);
            tagTV[i].setOnClickListener(this);
        }

        check.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            contactId = extras.getInt("id");
            pos = extras.getString("pos");
        } else {
            finish();
        }

        for(int i = 0; i < allContacts.size(); ++i) {
            Contact c = allContacts.get(i);
            if(c.getId() == contactId) {
                allPosition = i;
                contact = c;
                break;
            }
        }

        String name = contact.getName();
        String[] phone = contact.getPhone().split("-");
        String tags = contact.getTags();

        String[] tagStrArray = tags.replaceAll("[\\[\\]]", "").split(", ");
        for(int i = 0; i < 6; ++i)
            tag[i] = Boolean.parseBoolean(tagStrArray[i]);

        editName.setText(name);
        editPhone1.setText(phone[0]);
        editPhone2.setText(phone[1]);
        editPhone3.setText(phone[2]);
        Common.setTagsColor(tagTV, tags);
    }

    private void getTagId() {
        for(int i = 0; i < 6; ++i)
            tagsID[i] = getResources().getIdentifier(food_tags_id[i], "id", getPackageName());
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ic_check) {
            String name = editName.getText().toString();
            String phone1 = editPhone1.getText().toString();
            String phone2 = editPhone2.getText().toString();
            String phone3 = editPhone3.getText().toString();

            if(name.equals("")){
                Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }

            else if(!phone1.equals("010") || phone2.length() != 4 || phone3.length() != 4) {
                Toast.makeText(this, "전화번호를 입력해주세요.", Toast.LENGTH_LONG).show();
            }

            else {
                String phone = phone1 + "-" + phone2 + "-" + phone3;
                Contact c = allContacts.get(allPosition);
                c.setName(name);
                c.setPhone(phone);
                c.setTags(Arrays.toString(tag));

                allContacts.set(allPosition, c);
                contactCopy(allContacts, mContactList);
                mAdapter.notifyDataSetChanged();

                Intent intent = new Intent(getApplicationContext(), ContactDetailActivity.class);
                intent.putExtra("id", contactId);
                setResult(200, intent);
                toPrev(this);
                contactsWrite();
            }


        }

        for(int i = 0; i < 6; ++i) {
            if(v.getId() == tagsID[i]) {
                if(tag[i]) {
                    tag[i] = false;
                    v.setBackgroundResource(R.drawable.circle);
                }
                else {
                    tag[i] = true;
                    v.setBackgroundResource(food_tags_color[i]);
                }
            }
        }
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
    public void onBackPressed() {
        toPrev(this);
    }
}
