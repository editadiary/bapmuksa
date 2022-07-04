package com.example.myapplication.Recommend;

import static com.example.myapplication.Common.allContacts;
import static com.example.myapplication.Common.stack_page;

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

public class RecommendThirdActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecommendedContactAdapter contactAdapter;
    private RecommendedContactAdapter.RecyclerViewClickListener listener;
    private LinearLayoutManager mLinearLayoutManager;
    private ArrayList<Contact> filteredContact;
    private Date currentDate;

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
        currentDate = new Date();
        initRecyclerViewListener();
        filteredContact = filterByDate(currentDate);
        contactAdapter = new RecommendedContactAdapter(allContacts, listener);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
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
