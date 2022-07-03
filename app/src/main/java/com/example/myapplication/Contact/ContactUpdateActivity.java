package com.example.myapplication.Contact;

import static com.example.myapplication.Common.mAdapter;
import static com.example.myapplication.Common.mContactList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Common;
import com.example.myapplication.R;

import java.util.Collections;

public class ContactUpdateActivity extends AppCompatActivity implements View.OnClickListener {
    String pos = "-1";
    EditText editName, editPhone1, editPhone2, editPhone3;
    boolean[] tags;
    ImageView check;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_update);

        editName = findViewById(R.id.update_name_edit);
        editPhone1 = findViewById(R.id.update_phone_edit1);
        editPhone2 = findViewById(R.id.update_phone_edit2);
        editPhone3 = findViewById(R.id.update_phone_edit3);
        check = findViewById(R.id.ic_check);

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

        editName.setText(name);
        editPhone1.setText(phone1);
        editPhone2.setText(phone2);
        editPhone3.setText(phone3);
    }


    @Override
    public void onClick(View v) {
        int position = Integer.parseInt(pos);

        String name = editName.getText().toString();
        String phone = editPhone1.getText().toString() + "-" + editPhone2.getText().toString() + "-" + editPhone3.getText().toString();

        Contact contact = mContactList.get(position);
        contact.setName(name);
        contact.setPhone(phone);

        mContactList.set(position, contact);
        Collections.sort(mContactList);
        mAdapter.notifyDataSetChanged();
        Common.stack_page.pop();

        Intent intent = new Intent(getApplicationContext(), ContactDetailActivity.class);
        intent.putExtra("name", name)
                .putExtra("phone", phone)
                .putExtra("tags", tags)
                .putExtra("pos", pos);
        setResult(200, intent);
        finish();
    }
}
