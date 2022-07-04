package com.example.myapplication.Recommend;

import static com.example.myapplication.Common.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Common;
import com.example.myapplication.Contact.Contact;
import com.example.myapplication.Contact.ContactDetailActivity;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class RecommendThirdActivity extends AppCompatActivity {
    private RelativeLayout recommendRL;
    private TextView cannotFindTV;
    private RecyclerView mRecyclerView;
    private RecommendedContactAdapter contactAdapter;
    private RecommendedContactAdapter.RecyclerViewClickListener listener;
    private LinearLayoutManager mLinearLayoutManager;
    private ArrayList<Contact> filteredContacts;
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
        setVisible();
    }

    private void initVariables() {
        getExtras();
        currentDate = new Date();
        initRecyclerViewListener();
        filteredContacts = filterByTag(filterByNoDay(allContacts));
        contactAdapter = new RecommendedContactAdapter(filteredContacts, listener);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    private void setVisible() {
        if(filteredContacts.size() == 0) {
            cannotFindTV.setVisibility(View.VISIBLE);
            recommendRL.setVisibility(View.GONE);
        }

        else {
            cannotFindTV.setVisibility(View.GONE);
            recommendRL.setVisibility(View.VISIBLE);
        }
    }

    private void getExtras() {
        Bundle extra = getIntent().getExtras();
        if(extra != null) {
            tagPosition = extra.getInt("selectedTagPosition");
        }
    }

    @SuppressLint("NewApi")
    private ArrayList<Contact> filterByNoDay(ArrayList<Contact> contacts) {
        return (ArrayList<Contact>) contacts.stream().filter(contact -> contact.getLastMeet() != null && !contact.getLastMeet().equals("")).collect(Collectors.toList());
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
            currentTab = 3;
            goIntent(5);
            Intent intent = new Intent(this, ContactDetailActivity.class);

            intent.putExtra("name", filteredContacts.get(position).getName())
                    .putExtra("phone", filteredContacts.get(position).getPhone())
                    .putExtra("tags", filteredContacts.get(position).getTags())
                    .putExtra("lastMeet", filteredContacts.get(position).getLastMeet())
                    .putExtra("pos", Integer.toString(position));

            startActivity(intent);
        };
    }

    private void initViews() {
        recommendRL = findViewById(R.id.recommendRelativeLayout);
        cannotFindTV = findViewById(R.id.cannotFind);
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

    @Override
    public void onBackPressed() {
        Common.toPrev(this);
    }
}
