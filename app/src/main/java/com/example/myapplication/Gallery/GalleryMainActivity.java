package com.example.myapplication.Gallery;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;

import static com.example.myapplication.Common.mGalleryList;
import static com.example.myapplication.Common.galleryAdapter;

public class GalleryMainActivity extends AppCompatActivity {

    GridView gridView;
    ImageView addImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_main);

        gridView = findViewById(R.id.gridView);

        gridView.setAdapter(galleryAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(GalleryMainActivity.this,
                        ClickedItemActivity.class).putExtra("index", i));


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
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            }
        });

        addImage = findViewById(R.id.ic_plus);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Not implemented yet
            }
        });
    }
}


