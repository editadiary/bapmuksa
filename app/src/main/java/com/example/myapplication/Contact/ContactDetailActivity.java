package com.example.myapplication.Contact;

import static com.example.myapplication.Common.*;

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

import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;

public class ContactDetailActivity extends AppCompatActivity implements View.OnClickListener {
    String pos = "-1", name = "이름이 없습니다.", phone = "전화번호가 없습니다.";
    String tags;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ImageView editBtn, deleteBtn;
    TextView nameTV, phoneTV;
    TextView[] tagTV = new TextView[6];
    Bundle extras;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        // findViewById
        editBtn = findViewById(R.id.ic_edit);
        deleteBtn = findViewById(R.id.ic_delete);

        nameTV = findViewById(R.id.detail_name);
        phoneTV = findViewById(R.id.detail_phone);

        editBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);

        for(int i = 0; i < 6; ++i)
            tagTV[i] = findViewById(getResources().getIdentifier(Common.food_tags_id[i], "id", getPackageName()));

        setViews();

        activityResultLauncher = setResultLauncher();
    }

    private ActivityResultLauncher<Intent> setResultLauncher() {
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == 200) {
                assert result.getData() != null;
                extras = result.getData().getExtras();

                if(extras != null) {
                    name = extras.getString("name");
                    phone = extras.getString("phone");
                    tags = extras.getString("tags");
                    pos = extras.getString("pos");
                }
                nameTV.setText(name);
                phoneTV.setText(phone);
                Common.setTagsColor(tagTV, tags);
            }
        });
    }
    private void setViews() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            name = extras.getString("name");
            phone = extras.getString("phone");
            tags = extras.getString("tags");
            pos = extras.getString("pos");
        }

        nameTV.setText(name);
        phoneTV.setText(phone);

        Common.setTagsColor(tagTV, tags);
    }

    @Override
    public void onBackPressed() {
        Common.toPrev(this);
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
                    .putExtra("tags", tags)
                    .putExtra("pos", pos);

            activityResultLauncher.launch(intent);
        }

        else if(id == R.id.ic_delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Alert");
            builder.setMessage("Do you really delete this?");
            builder.setPositiveButton("Confirm", (dialog, which) -> {
                int contactId = mContactList.get(Integer.parseInt(pos)).getId();
                for(int i = 0; i < allContacts.size(); ++i) {
                    if(allContacts.get(i).getId() == contactId) {
                        allContacts.remove(i);
                    }
                }
                contactCopy(allContacts, mContactList);
                contactsWrite();
                Toast.makeText(this, "This Contact is deleted.", Toast.LENGTH_LONG).show();
                stack_page.pop(); stack_page.push(1);
                finish();
                mAdapter.notifyDataSetChanged();
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {

            });
            AlertDialog dlg = builder.create();
            dlg.show();
        }
    }

    private void contactsWrite() {
        try{
            FileOutputStream os = openFileOutput(CONTACT_JSON_FILE_NAME, MODE_PRIVATE);
            JSONObject jsonFile = contactsToJson();

            assert jsonFile != null;
            os.write(jsonFile.toString().getBytes());
            os.flush();
            os.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
