package com.example.myapplication.Contact;

import static com.example.myapplication.Common.id_num;
import static com.example.myapplication.Common.mAdapter;
import static com.example.myapplication.Common.mContactList;
import static com.example.myapplication.Common.stack_page;

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

import com.example.myapplication.Common;
import com.example.myapplication.R;

import java.util.Collection;
import java.util.Collections;

public class ContactCreateActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edit_name, edit_phone1, edit_phone2, edit_phone3;
    private ImageView plusTag;
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
        plusTag = findViewById(R.id.addTag);

        plusTag.setOnClickListener(this);
    }


    public void addClick(View view) {
        String id = Integer.toString(id_num); ++id_num;
        String name = edit_name.getText().toString();
        String phone = edit_phone1.getText().toString() + "-" + edit_phone2.getText().toString() + "-" + edit_phone3.getText().toString();
        String[] tags = new String[tagCnt];
        for(int i = 1; i <= tagCnt; ++i) {
            int tag_id = getResources().getIdentifier("Tag"+i, "id", getPackageName());
            tags[tag_id] = ((EditText)findViewById(tag_id)).getText().toString();
        }

        stack_page.push(1);

        AddContact(new Contact(id, name, phone, tags));

        Intent intent = new Intent(getApplicationContext(), ContactDetailActivity.class);
        intent.putExtra("name", name)
                .putExtra("phone", phone)
                        .putExtra("id", id);
        setResult(100, intent);
        finish();
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

        if(id == R.id.addTag) {
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
    }

    public int pxToDp(final int px) {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, getResources().getDisplayMetrics());
    }
}
