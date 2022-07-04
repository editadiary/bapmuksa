package com.example.myapplication.Contact;

import static com.example.myapplication.Common.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Common;
import com.example.myapplication.R;

import java.util.Collections;

public class ContactUpdateActivity extends AppCompatActivity implements View.OnClickListener {
    String pos = "-1";
    EditText editName, editPhone1, editPhone2, editPhone3;
    private TextView[] tagTV;
    private boolean[] tags;
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

        tagTV = new TextView[6];
        tagsID = new int[6]; getTagId();

        for(int i = 0; i < 6; ++i) {
            tagTV[i] = findViewById(tagsID[i]);
            tagTV[i].setOnClickListener(this);
        }

        check.setOnClickListener(this);

        String name = "";
        String phone1 = "", phone2 = "", phone3 = "";

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            name = extras.getString("name");

            String[] split_phone = extras.getString("phone").split("-");
            phone1 = split_phone[0];
            phone2 = split_phone[1];
            phone3 = split_phone[2];
            tags = extras.getBooleanArray("tags");
            pos = extras.getString("pos");
        } else {
            finish();
        }

        Common.setTagsColor(tagTV, tags);

        editName.setText(name);
        editPhone1.setText(phone1);
        editPhone2.setText(phone2);
        editPhone3.setText(phone3);
    }

    private void getTagId() {
        for(int i = 0; i < 6; ++i)
            tagsID[i] = getResources().getIdentifier(food_tags_id[i], "id", getPackageName());
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ic_check) {
            int position = Integer.parseInt(pos);
            stack_page.pop();

            String name = editName.getText().toString();
            String phone = editPhone1.getText().toString() + "-" + editPhone2.getText().toString() + "-" + editPhone3.getText().toString();
            Contact contact = mContactList.get(position);
            int contactId = contact.getId();
            int curSize = allContacts.size();
            contact.setName(name);
            contact.setPhone(phone);
            contact.setTags(tags);

            for(int i = 0; i < curSize; ++i) {
                if(allContacts.get(i).getId() == contactId) {
                    allContacts.set(i, contact);
                }
            }
            contactCopy(allContacts, mContactList);
            mAdapter.notifyDataSetChanged();

            Intent intent = new Intent(getApplicationContext(), ContactDetailActivity.class);
            intent.putExtra("name", name)
                    .putExtra("phone", phone)
                    .putExtra("tags", tags)
                    .putExtra("pos", pos);
            setResult(200, intent);
            finish();
        }

        for(int i = 0; i < 6; ++i) {
            if(v.getId() == tagsID[i]) {
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
}
