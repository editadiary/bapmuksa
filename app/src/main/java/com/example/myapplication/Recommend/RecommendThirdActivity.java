package com.example.myapplication.Recommend;

import static com.example.myapplication.Common.allContacts;
import static com.example.myapplication.Common.stack_page;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Contact.Contact;
import com.example.myapplication.Contact.ContactDetailActivity;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class RecommendThirdActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecommendedContactAdapter contactAdapter;
    private RecommendedContactAdapter.RecyclerViewClickListener listener;
    private LinearLayoutManager mLinearLayoutManager;
    private ArrayList<Contact> filteredContact;
    private Date currentDate;
    private int tagPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_thirdpage);

        initVariables();
        initViews();
        initSettings();
        initDecoration();
    }

    private void initVariables() {
        getExtras();
        currentDate = new Date();
        initRecyclerViewListener();
        filteredContact = filterByTag(allContacts);
        contactAdapter = new RecommendedContactAdapter(allContacts, listener);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    private void getExtras() {
        Bundle extra = getIntent().getExtras();
        if(extra != null) {
            tagPosition = extra.getInt("selectedTagPosition");
        }
    }

    @SuppressLint("NewApi")
    private ArrayList<Contact> filterByTag(ArrayList<Contact> contacts) {
        if(tagPosition == 0) return contacts;

        return (ArrayList<Contact>) contacts.stream().filter(contact -> {
            String[] tagString = contact.getTags().replaceAll("[\\[\\]]", "").split(", ");
            return tagString[tagPosition - 1].equals("true");
        }).collect(Collectors.toList());
    }

    private ArrayList<Contact> filterByDate(Date currentDate) {
        return allContacts;
    }

    private void initRecyclerViewListener() {
        listener = (v, position) -> {
            if(stack_page.peek() == 5) return;

            stack_page.push(5);
            Intent intent = new Intent(this, ContactDetailActivity.class);

            intent.putExtra("name", filteredContact.get(position).getName())
                    .putExtra("phone", filteredContact.get(position).getPhone())
                    .putExtra("tags", filteredContact.get(position).getTags())
                    .putExtra("lastMeet", filteredContact.get(position).getLastMeet())
                    .putExtra("pos", Integer.toString(position));

            startActivity(intent);
        };
    }

    private void initViews() {
        mRecyclerView = findViewById(R.id.recommenedContacts);
    }

    private void initSettings() {
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(contactAdapter);
    }

    private void initDecoration(){
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }
}
