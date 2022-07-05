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
import com.example.myapplication.Home.HomeActivity;
import com.example.myapplication.R;

import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ContactDetailActivity extends AppCompatActivity implements View.OnClickListener {
    int contactId, position;
    boolean isStar;
    Contact selectedContact;
    String pos, name, phone, tags, lastMeet;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ImageView editBtn, deleteBtn, starImage;
    TextView nameTV, phoneTV, dayTV;
    TextView[] tagTV = new TextView[6];

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
        starImage = findViewById(R.id.star);

        nameTV = findViewById(R.id.detail_name);
        phoneTV = findViewById(R.id.detail_phone);
        dayTV = findViewById(R.id.dDay);

        for(int i = 0; i < 6; ++i)
            tagTV[i] = findViewById(getResources().getIdentifier(Common.food_tags_id[i], "id", getPackageName()));
    }

    private void initListeners() {
        editBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        starImage.setOnClickListener(this);
    }

    private ActivityResultLauncher<Intent> setResultLauncher() {
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == 200) {
                Intent data = result.getData();

                assert data != null;
                Bundle extra = data.getExtras();

                contactId = extra.getInt("id");
                for(int i = 0; i < allContacts.size(); ++i) {
                    Contact c = allContacts.get(i);
                    if(c.getId() == contactId) {
                        selectedContact = c;
                        position = i;
                        break;
                    }
                }

                nameTV.setText(selectedContact.getName());
                phoneTV.setText(selectedContact.getPhone());
                Common.setTagsColor(tagTV, selectedContact.getTags());
            }
        });
    }

    private void setViews() {
        Bundle extra = getIntent().getExtras();
        if(extra != null)
            contactId = extra.getInt("id");

        for(int i = 0; i < allContacts.size(); ++i) {
            if(allContacts.get(i).getId() == contactId) {
                selectedContact = allContacts.get(i);
                position = i;
            }
        }

        if(selectedContact == null) return;

        name = selectedContact.getName();
        phone = selectedContact.getPhone();
        tags = selectedContact.getTags();
        lastMeet = selectedContact.getLastMeet();
        isStar = selectedContact.getIsStar();

        String day = lastMeet == null || lastMeet.equals("") ? "? 일" : getDays() + "일";

        if(isStar) starImage.setImageResource(R.drawable.ic_star_filled);

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
            assert prev != null;
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
        if(v.getId() == R.id.ic_edit){
            goIntent(6);

            Intent intent = new Intent(getApplicationContext(), ContactUpdateActivity.class);
            intent.putExtra("id", contactId)
                    .putExtra("name", name)
                    .putExtra("phone", phone)
                    .putExtra("tags", tags)
                    .putExtra("pos", pos);

            activityResultLauncher.launch(intent);
        }

        if(v.getId() == R.id.ic_delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Alert");
            builder.setMessage("Do you really delete this?");
            builder.setPositiveButton("Confirm", (dialog, which) -> {
                allContacts.remove(position);
                contactCopy(allContacts, mContactList);
                mAdapter.notifyDataSetChanged();
                HomeActivity.notifyDataSet();
                Toast.makeText(this, "This Contact is deleted.", Toast.LENGTH_LONG).show();
                toPrev(this);
                finish();
                contactsWrite();
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {

            });
            AlertDialog dlg = builder.create();
            dlg.show();
        }

        if(v.getId() == R.id.star) {
            int allPosition = -1;
            Contact contact = null;
            for(int i = 0; i < allContacts.size(); ++i) {
                if(allContacts.get(i).getId() == contactId) {
                    allPosition = i;
                    contact = allContacts.get(i);
                    break;
                }
            }
            if(allPosition == -1 || contact == null) return;

            boolean isStar = contact.getIsStar();
            contact.setIsStar(!isStar);
            allContacts.set(allPosition, contact);

            HomeActivity.getStarContacts();
            HomeActivity.notifyDataSet();
            if(isStar) {
                starImage.setImageResource(R.drawable.ic_star_empty);
            } else {
                starImage.setImageResource(R.drawable.ic_star_filled);
            }

            contactsWrite();
        }
    }

    @Override
    public void onBackPressed() {
        Common.toPrev(this);
    }
}
