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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ContactDetailActivity extends AppCompatActivity implements View.OnClickListener {
    String pos, name, phone, tags, lastMeet;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ImageView editBtn, deleteBtn;
    TextView nameTV, phoneTV, dayTV;
    TextView[] tagTV = new TextView[6];
    Bundle extras;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        // findViewById
        initViews();
        initListeners();
        setViews();
        initLauncher();
    }

    private void initLauncher(){
        activityResultLauncher = setResultLauncher();
    }

    private void initViews() {
        editBtn = findViewById(R.id.ic_edit);
        deleteBtn = findViewById(R.id.ic_delete);

        nameTV = findViewById(R.id.detail_name);
        phoneTV = findViewById(R.id.detail_phone);
        dayTV = findViewById(R.id.dDay);

        for(int i = 0; i < 6; ++i)
            tagTV[i] = findViewById(getResources().getIdentifier(Common.food_tags_id[i], "id", getPackageName()));

    }

    private void initListeners() {
        editBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
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
                    lastMeet = extras.getString("lastMeet");
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
            lastMeet = extras.getString("lastMeet");
            pos = extras.getString("pos");
        }

        String day = lastMeet == null || lastMeet.equals("") ? "? 일" : getDays() + "일";

        nameTV.setText(name);
        phoneTV.setText(phone);
        dayTV.setText(day);

        Common.setTagsColor(tagTV, tags);
    }

    private String getDays() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            Date current = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            Date prev = simpleDateFormat.parse(lastMeet);

            assert current != null;
            long days = (current.getTime() - prev.getTime()) / (24 * 60 * 60 * 1000);
            return Long.toString(days);
        } catch (ParseException e) {
            e.printStackTrace();

            return null;
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent;

        if(id == R.id.ic_edit){
            goIntent(6);

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
                toPrev(this);
                finish();
                mAdapter.notifyDataSetChanged();
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {

            });
            AlertDialog dlg = builder.create();
            dlg.show();
        }
    }

    @Override
    public void onBackPressed() {
        Common.toPrev(this);
    }
}
