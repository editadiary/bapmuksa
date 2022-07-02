package com.example.myapplication.Contact;

import static com.example.myapplication.Common.mAdapter;
import static com.example.myapplication.Common.mContactList;
import static com.example.myapplication.Common.stack_page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Common;
import com.example.myapplication.R;

public class ContactDetailActivity extends AppCompatActivity implements View.OnClickListener {
    String pos = "-1";
    String name = "이름이 없습니다.";
    String phone = "전화번호가 없습니다.";
    ImageView editBtn, deleteBtn;
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
            stack_page.pop(); stack_page.push(6);

            intent = new Intent(getApplicationContext(), ContactUpdateActivity.class);
            intent.putExtra("name", name)
                    .putExtra("phone", phone)
                            .putExtra("pos", pos);

            activityResultLauncher.launch(intent);
        }

        else if(id == R.id.ic_delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Alert");
            builder.setMessage("Do you really delete this?");
            builder.setPositiveButton("Confirm", (dialog, which) -> {
                mContactList.remove(Integer.parseInt(pos));
                Toast.makeText(this, "This Contact is deleted.", Toast.LENGTH_LONG).show();
                mAdapter.notifyDataSetChanged();
                stack_page.pop(); stack_page.push(1);
                finish();
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {
               return;
            });
            AlertDialog dlg = builder.create();
            dlg.show();
        }
    }
}
