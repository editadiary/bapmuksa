package com.example.myapplication.Contact;

import static com.example.myapplication.Common.mContactList;
import static com.example.myapplication.Common.mAdapter;
import static com.example.myapplication.Common.stack_page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Common;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {
    private static RecyclerView mRecyclerView;
    private static ContactAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        setOnClickListener();
        mRecyclerView = findViewById(R.id.contact_rv);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new ContactAdapter(mContactList, listener);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void setOnClickListener() {
        listener = (v, position) -> {
            if(stack_page.peek() == 5) return;

            stack_page.push(5);
            Intent intent = new Intent(getApplicationContext(), ContactDetailActivity.class);
            intent.putExtra("name", mContactList.get(position).getName())
                    .putExtra("phone", mContactList.get(position).getPhone())
                            .putExtra("pos", Integer.toString(position));

            startActivity(intent);
        };
    }

    public void addClick(View view) {
        if(stack_page.peek() == 4) return;

        stack_page.push(4);
        Intent intent = new Intent(getApplicationContext(), ContactCreateActivity.class);
        startActivity(intent);
    }


}