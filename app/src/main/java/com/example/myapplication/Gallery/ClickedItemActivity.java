package com.example.myapplication.Gallery;

import static com.example.myapplication.Common.mContactList;
import static com.example.myapplication.Common.mGalleryList;
import static com.example.myapplication.Common.galleryAdapter;
import static com.example.myapplication.Common.stack_page;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.ArrayList;

public class ClickedItemActivity extends AppCompatActivity {

    public static Activity clickedItemActivity;

    ImageButton TrashBtn;
    ImageButton EditBtn;
    ImageButton CheckBtn;

    ImageView imageView;
    TextView textViewDate;
    TextView textViewName;
    TextView textViewTag;

    TextView textFriends;
    TextView Friend1;
    TextView Friend2;
    TextView Friend3;
    TextView Friend4;

    String selectedDate = "";
    String selectedName = "";
    int selectedImage = -1;
    int idx = -1;
    String tagName = "";
    String friend1 = "";
    String friend2 = "";
    String friend3 = "";
    String friend4 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        clickedItemActivity = ClickedItemActivity.this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_item);

        imageView = findViewById(R.id.imageView);
        textViewDate = findViewById(R.id.tvDate);
        textViewName = findViewById(R.id.tvName);
        textViewTag = findViewById(R.id.tvTag);

        textFriends = findViewById(R.id.friends);
        Friend1 = findViewById(R.id.friend1);
        Friend2 = findViewById(R.id.friend2);
        Friend3 = findViewById(R.id.friend3);
        Friend4 = findViewById(R.id.friend4);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            idx = intent.getIntExtra("index", 0);
            selectedDate = mGalleryList.get(idx).getDate();
            selectedName = mGalleryList.get(idx).getName();
            selectedImage= mGalleryList.get(idx).getImage();
            tagName = mGalleryList.get(idx).getTagName();

            ArrayList<Integer> fids = mGalleryList.get(idx).getFriends();
            int numFriends = fids.size();

            if (numFriends>0) {
                friend1 = mContactList.get(fids.get(0)).getName();
                Friend1.setText(friend1);
            }
            if (numFriends>1) {
                friend2 = mContactList.get(fids.get(1)).getName();
                Friend2.setText(friend2);
                Friend2.setVisibility(View.VISIBLE);
            }
            if (numFriends>2) {
                friend3 = mContactList.get(fids.get(2)).getName();
                Friend3.setText(friend3);
                Friend3.setVisibility(View.VISIBLE);
            }
            if (numFriends>3) {
                friend4 = mContactList.get(fids.get(3)).getName();
                Friend4.setText(friend4);
                Friend4.setVisibility(View.VISIBLE);
            }

            textViewDate.setText(selectedDate);
            textViewName.setText(selectedName);
            textViewTag.setText(tagName);
            imageView.setImageResource(selectedImage);
        }

        TrashBtn = findViewById(R.id.ic_delete);
        TrashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ClickedItemActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Gallery");
                builder.setMessage("Delete "+mGalleryList.get(idx).getName()+"?");

                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                galleryAdapter.deleteItem(idx);
                                Toast.makeText(getApplicationContext(), "아이템이 삭제되었습니다", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        EditBtn = findViewById(R.id.ic_edit);
        EditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ClickedItemActivity.this,
                        ClickedItemEditActivity.class).putExtra("index", idx));

            }
        });

        CheckBtn = findViewById(R.id.ic_check);
        CheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

    }
}