package com.example.myapplication.Gallery;

import static com.example.myapplication.Common.galleryAdapter;
import static com.example.myapplication.Common.mGalleryList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.FindFriendsActivity;
import com.example.myapplication.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        clickedItemEditActivity = ClickedItemEditActivity.this;

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

            finish();
        }

        if(v.getId() == R.id.friend1) {
            Friend2.setVisibility(View.VISIBLE);
            startActivity(new Intent(ClickedItemEditActivity.this,
                    FindFriendsActivity.class).putExtra("index", idx));



        }
        if(v.getId() == R.id.friend2) {
            Friend3.setVisibility(View.VISIBLE);


        }
        if(v.getId() == R.id.friend3) {
            Friend4.setVisibility(View.VISIBLE);

        }
        if(v.getId() == R.id.friend4) {

        }

    }
}