package com.example.myapplication.Recommend;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Contact.ContactDetailActivity;
import com.example.myapplication.R;

public class RecommendFirstActivity extends AppCompatActivity implements View.OnClickListener {
    // private final String[] string_id = {"spinnerTag", "minimumDate", "maximumDate", "searchBtn"};
    private Spinner tagSpinner;
    private EditText minimumET, maximumET;
    private Button searchBtn;
    private int tagIdx = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_firstpage);

        tagSpinner = findViewById(R.id.spinnerTag);
        minimumET = findViewById(R.id.minimumDate);
        maximumET = findViewById(R.id.maximumDate);
        searchBtn = findViewById(R.id.searchBtn);

        searchBtn.setOnClickListener(this);

        tagSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tagIdx = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                tagIdx = 0;
            }
        });

    }

    @Override
    public void onClick(View v) {
        int min = -1, max = -1;
        String tag = tagSpinner.getSelectedItem().toString();
        if(minimumET.getText() != null) min = Integer.parseInt(minimumET.getText().toString());
        if(maximumET.getText() != null) max = Integer.parseInt(maximumET.getText().toString());

        if(min == -1 || max == -1) {
            Toast.makeText(this, "기간을 입력해주세요", Toast.LENGTH_LONG).show();
        }

        else if(max < min) {
            Toast.makeText(this, "기간을 제대로 입력해주세요", Toast.LENGTH_LONG).show();
        }

        else {
            Intent intent = new Intent(getApplicationContext(), RecommendSecondActivity.class);
            intent.putExtra("min", min)
                    .putExtra("max", max)
                    .putExtra("tag", tag);

            startActivity(intent);
        }
    }
}
