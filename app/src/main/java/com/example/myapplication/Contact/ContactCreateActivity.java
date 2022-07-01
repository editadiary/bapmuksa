package com.example.myapplication.Contact;

import static com.example.myapplication.Common.mAdapter;
import static com.example.myapplication.Common.mContactList;
import static com.example.myapplication.Common.stack_page;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Common;
import com.example.myapplication.R;

public class ContactCreateActivity extends AppCompatActivity {
    private EditText edit_name;
    private EditText edit_phone1;
    private EditText edit_phone2;
    private EditText edit_phone3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_create);

        edit_name = findViewById(R.id.create_name_edit);
        edit_phone1 = findViewById(R.id.create_phone_edit1);
        edit_phone2 = findViewById(R.id.create_phone_edit2);
        edit_phone3 = findViewById(R.id.create_phone_edit3);
    }


    public void addClick(View view) {
        String name = edit_name.getText().toString();
        String phone = edit_phone1.getText().toString() + "-" + edit_phone2.getText().toString() + "-" + edit_phone3.getText().toString();

        AddContact(name, phone);
        stack_page.push(1);
        finish();
    }

    private void AddContact(String name, String phone) {
        Contact new_contact = new Contact(name, phone);
        mContactList.add(new_contact);
        mAdapter.notifyDataSetChanged();
    }
}
