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

    ImageView imageView;
    TextView textView;

    String selectedName = "";
    int selectedImage = -1;
    int idx = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        clickedItemActivity = ClickedItemActivity.this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_item);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.tvName);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            selectedName = intent.getStringExtra("name");
            selectedImage = intent.getIntExtra("image", 0);
            idx = intent.getIntExtra("index", 0);

            textView.setText(selectedName);
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
                builder.setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
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
                        ClickedItemEditActivity.class).putExtra("name", selectedName).putExtra("image", selectedImage).putExtra("index", idx));

            }
        });

    }
}