package com.example.myapplication.Contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Common;
import com.example.myapplication.R;

public class ContactDetailActivity extends AppCompatActivity implements View.OnClickListener {
    String pos = "-1";
    String name = "이름이 없습니다.";
    String phone = "전화번호가 없습니다.";
    ImageView editBtn;
    ImageView deleteBtn;
    ActivityResultLauncher<Intent> activityResultLauncher;
    Bundle extras;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        editBtn = findViewById(R.id.ic_edit);
        deleteBtn = findViewById(R.id.ic_delete);

        editBtn.setOnClickListener(this);
        editBtn.setOnClickListener(this);

        TextView nameTV = findViewById(R.id.detail_name);
        TextView phoneTV = findViewById(R.id.detail_phone);

        extras = getIntent().getExtras();
        if(extras != null) {
            name = extras.getString("name");
            phone = extras.getString("phone");
            pos = extras.getString("pos");
        }

        nameTV.setText(name);
        phoneTV.setText(phone);


        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == 200) {
                assert result.getData() != null;
                extras = result.getData().getExtras();

                if(extras != null) {
                    name = extras.getString("name");
                    phone = extras.getString("phone");
                    pos = extras.getString("pos");
                }

                nameTV.setText(name);
                phoneTV.setText(phone);
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent;

        if(id == R.id.ic_edit){
            Common.btn = 6;
            intent = new Intent(getApplicationContext(), ContactUpdateActivity.class);
            intent.putExtra("name", name)
                    .putExtra("phone", phone)
                            .putExtra("pos", pos);

            activityResultLauncher.launch(intent);
        }
    }
}
