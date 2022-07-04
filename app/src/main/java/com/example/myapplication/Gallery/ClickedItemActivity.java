package com.example.myapplication.Gallery;

import static com.example.myapplication.Common.mGalleryList;
import static com.example.myapplication.Common.galleryAdapter;
import static com.example.myapplication.Common.stack_page;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class ClickedItemActivity extends AppCompatActivity {

    public static Activity clickedItemActivity;

    ImageButton TrashBtn;
    ImageButton EditBtn;
    ImageButton CheckBtn;

    ImageView imageView;
    TextView textViewName;
    TextView textViewTag;

    String selectedName = "";
    int selectedImage = -1;
    int idx = -1;
    String tagName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        clickedItemActivity = ClickedItemActivity.this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_item);

        imageView = findViewById(R.id.imageView);
        textViewName = findViewById(R.id.tvName);
        textViewTag = findViewById(R.id.tvTag);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            idx = intent.getIntExtra("index", 0);
            selectedName = mGalleryList.get(idx).getName();
            selectedImage= mGalleryList.get(idx).getImage();
            tagName = mGalleryList.get(idx).getTagName();

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