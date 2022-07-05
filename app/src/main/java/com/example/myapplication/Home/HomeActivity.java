package com.example.myapplication.Home;

import static com.example.myapplication.Common.allContacts;
import static com.example.myapplication.Common.goIntent;
import static com.example.myapplication.Common.mAdapter;
import static com.example.myapplication.Common.mContactList;
import static com.example.myapplication.Common.stack_page;
import static com.example.myapplication.Common.starContacts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Common;
import com.example.myapplication.Contact.Contact;
import com.example.myapplication.Contact.ContactAdapter;
import com.example.myapplication.Contact.ContactDetailActivity;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class HomeActivity extends AppCompatActivity {
    private static ContactAdapter contactAdapter;
    private static RecyclerView starContactsRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initVar();
        initViews();
        initRVSetting();
        notifyDataSet();
    }

    private void initVar() {
        getStarContacts();
        setAdapter();
    }

    private void initViews() {
        starContactsRV = findViewById(R.id.starContactsRV);
    }

    private void initRVSetting() {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        starContactsRV.setLayoutManager(mLinearLayoutManager);
        starContactsRV.setAdapter(contactAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(starContactsRV.getContext(), mLinearLayoutManager.getOrientation());
        starContactsRV.addItemDecoration(dividerItemDecoration);
    }

    private void setAdapter() {
        contactAdapter = new ContactAdapter(starContacts, (v, position) -> {
            Log.d("getId", v.getId() + "");
            if(stack_page.peek() == 5) return;

            goIntent(5);
            Intent intent = new Intent(this, ContactDetailActivity.class);

            intent.putExtra("id", mContactList.get(position).getId());

            startActivity(intent);
        });
    }

    public static void notifyDataSet() {
        contactAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NewApi")
    public static void getStarContacts() {
        if(allContacts == null || allContacts.size() == 0)
            starContacts = new ArrayList<>();

        else
            starContacts = (ArrayList<Contact>) allContacts.stream().filter(Contact::getIsStar).collect(Collectors.toList());
    }

    @Override
    public void onBackPressed() {
        Common.toPrev(this);
    }
}
