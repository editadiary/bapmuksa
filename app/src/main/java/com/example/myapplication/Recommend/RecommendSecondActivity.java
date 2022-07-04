package com.example.myapplication.Recommend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class RecommendSecondActivity extends AppCompatActivity implements View.OnClickListener {
    Button searchBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_secondpage);

        initViews();
        initListeners();
    }

    private void initViews() {
        searchBtn = findViewById(R.id.searchBtn);
    }

    private void initListeners() {
        searchBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.searchBtn) {
            Intent intent = new Intent(this, RecommendThirdActivity.class);
            startActivity(intent);
        }
    }
}
