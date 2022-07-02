package com.example.myapplication.Contact;

import static com.example.myapplication.Common.*;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.util.Collections;

public class ContactCreateActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edit_name, edit_phone1, edit_phone2, edit_phone3;
    private ImageView addBtn, plusTag;
    private LinearLayout ll;
    int tagCnt = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_create);

        ll = findViewById(R.id.create_layout);
        edit_name = findViewById(R.id.create_name_edit);
        edit_phone1 = findViewById(R.id.create_phone_edit1);
        edit_phone2 = findViewById(R.id.create_phone_edit2);
        edit_phone3 = findViewById(R.id.create_phone_edit3);

        addBtn = findViewById(R.id.ic_check);
        addBtn.setOnClickListener(this);

        plusTag = findViewById(R.id.addTag);
        plusTag.setOnClickListener(this);
    }

    private void AddContact(Contact new_contact) {
        mContactList.add(new_contact);
        Collections.sort(mContactList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        LinearLayout tll = new LinearLayout(this);

        if (id == R.id.addTag) {
            String tagName = "Tag" + ++tagCnt;

            TextView tv = new TextView(this);
            EditText et = new EditText(this);

            tll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(pxToDp(0), pxToDp(50), 2.0f));
            LinearLayout.LayoutParams elp = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(pxToDp(0), pxToDp(50), 3.0f));

            tv.setLayoutParams(tlp);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            tv.setText(tagName);
            tv.setTypeface(null, Typeface.BOLD);
            tv.setGravity(Gravity.CENTER);

            et.setLayoutParams(elp);
            et.setId(tagCnt);

            tll.addView(tv);
            tll.addView(et);

            ll.addView(tll);
        }

        if (id == R.id.ic_check) {
            String id_cnt = Integer.toString(id_num);
            ++id_num;
            String name = edit_name.getText().toString();
            String phone = edit_phone1.getText().toString() + "-" + edit_phone2.getText().toString() + "-" + edit_phone3.getText().toString();
            String[] tags = new String[tagCnt];
            for (int i = 1; i <= tagCnt; ++i) {
                int tag_id = getResources().getIdentifier("Tag" + i, "id", getPackageName());
                tags[tag_id] = ((EditText) findViewById(tag_id)).getText().toString();
            }

            stack_page.push(1);

            AddContact(new Contact(id_cnt, name, phone, tags, "ic_person"));

            Intent intent = new Intent(getApplicationContext(), ContactDetailActivity.class);
            intent.putExtra("name", name)
                    .putExtra("phone", phone)
                    .putExtra("id", id);
            setResult(100, intent);
            finish();
        }
    }

    public int pxToDp(final int px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, getResources().getDisplayMetrics());
    }

}
