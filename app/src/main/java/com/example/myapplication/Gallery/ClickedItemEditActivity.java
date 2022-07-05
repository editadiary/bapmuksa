package com.example.myapplication.Gallery;

import static com.example.myapplication.Common.galleryAdapter;
import static com.example.myapplication.Common.goIntent;
import static com.example.myapplication.Common.mContactList;
import static com.example.myapplication.Common.mGalleryList;
import static com.example.myapplication.Common.toPrev;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.Common;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ClickedItemEditActivity extends AppCompatActivity implements View.OnClickListener{
    public static Activity clickedItemEditActivity;

    ClickedItemActivity clickedItemActivity = (ClickedItemActivity)ClickedItemActivity.clickedItemActivity;

    private Spinner spinner;

    ImageButton CheckBtn;

    ImageView imageView;
    EditText editName;

    TextView textViewDate;

    TextView textFriends;
    TextView Friend1;
    TextView Friend2;
    TextView Friend3;
    TextView Friend4;

    String selectedDate = "";
    int selectedImage = -1;
    int idx = -1;
    int tag = -1;
    String friend1 = "";
    String friend2 = "";
    String friend3 = "";
    String friend4 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        clickedItemEditActivity = ClickedItemEditActivity.this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_item_edit);

        clickedItemActivity.finish();

        CheckBtn = findViewById(R.id.ic_check);

        spinner = findViewById(R.id.spinnerTag);

        imageView = findViewById(R.id.imageView);
        editName = findViewById(R.id.tvNameEdit);

        textViewDate = findViewById(R.id.tvDate);
        textFriends = findViewById(R.id.friends);
        Friend1 = findViewById(R.id.friend1);
        Friend2 = findViewById(R.id.friend2);
        Friend3 = findViewById(R.id.friend3);
        Friend4 = findViewById(R.id.friend4);

        String selectedName = "";

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            idx = intent.getIntExtra("index", 0);
            selectedDate = mGalleryList.get(idx).getDate();
            selectedName = mGalleryList.get(idx).getName();
            selectedImage = mGalleryList.get(idx).getImage();
            tag = mGalleryList.get(idx).getTag();

            ArrayList<Integer> fids = mGalleryList.get(idx).getFriends();
            int numFriends = fids.size();

            if (numFriends>0) {
                friend1 = mContactList.get(fids.get(0)).getName();
                Friend1.setText(friend1);
                Friend2.setVisibility(View.VISIBLE);
            }
            if (numFriends>1) {
                friend2 = mContactList.get(fids.get(1)).getName();
                Friend2.setText(friend2);
                Friend3.setVisibility(View.VISIBLE);

            }
            if (numFriends>2) {
                friend3 = mContactList.get(fids.get(2)).getName();
                Friend3.setText(friend3);
                Friend4.setVisibility(View.VISIBLE);
            }
            if (numFriends>3) {
                friend4 = mContactList.get(fids.get(3)).getName();
                Friend4.setText(friend4);
            }

            textViewDate.setText(selectedDate);
            imageView.setImageResource(selectedImage);
        }else{
            finish();
        }

        spinner.setSelection(tag);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tag = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        Friend1.setOnClickListener(this);
        Friend2.setOnClickListener(this);
        Friend3.setOnClickListener(this);
        Friend4.setOnClickListener(this);

        CheckBtn.setOnClickListener(this);

        editName.setText(selectedName);
    }

    public void onClick(View v) {
        if(v.getId() == R.id.ic_check){
            String name = editName.getText().toString();

            ImageFile image = mGalleryList.get(idx);
            image.setName(name);
            image.setTag(tag);

            mGalleryList.set(idx, image);
            galleryAdapter.notifyDataSetChanged();

            startActivity(new Intent(ClickedItemEditActivity.this,
                    ClickedItemActivity.class).putExtra("index", idx));

            toPrev(this);
        }

        if(v.getId() == R.id.friend1) {
            goIntent(9);
            startActivity(new Intent(ClickedItemEditActivity.this,
                    FindFriendsActivity.class).putExtra("index", idx));

        }
        if(v.getId() == R.id.friend2) {
            goIntent(9);
            startActivity(new Intent(ClickedItemEditActivity.this,
                    FindFriendsActivity.class).putExtra("index", idx));


        }
        if(v.getId() == R.id.friend3) {
            goIntent(9);
            startActivity(new Intent(ClickedItemEditActivity.this,
                    FindFriendsActivity.class).putExtra("index", idx));

        }
        if(v.getId() == R.id.friend4) {
            goIntent(9);
            startActivity(new Intent(ClickedItemEditActivity.this,
                    FindFriendsActivity.class).putExtra("index", idx));
        }

    }

    @Override
    public void onBackPressed() {
        Common.toPrev(this);
    }
}