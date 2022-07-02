package com.example.myapplication.Gallery;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.myapplication.R;

import static com.example.myapplication.Common.mGalleryList;
import static com.example.myapplication.Common.galleryAdapter;

public class GalleryMainActivity extends AppCompatActivity {

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_main);

        gridView = findViewById(R.id.gridView);

        gridView.setAdapter(galleryAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedName = mGalleryList.get(i).getName();
                int selectedImage = mGalleryList.get(i).getImage();

                startActivity(new Intent(GalleryMainActivity.this,
                        ClickedItemActivity.class).putExtra("name", selectedName).putExtra("image", selectedImage).putExtra("index", i));


            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                AlertDialog.Builder builder = new AlertDialog.Builder(GalleryMainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Gallery");
                builder.setMessage("Delete "+mGalleryList.get(i).getName()+"?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                galleryAdapter.deleteItem(i);
                                Toast.makeText(getApplicationContext(), "아이템이 삭제되었습니다", Toast.LENGTH_LONG).show();
                            }
                        });
                builder.setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            }
        });
    }
}


