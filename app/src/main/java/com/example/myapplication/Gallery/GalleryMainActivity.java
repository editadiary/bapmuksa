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

import com.example.myapplication.Common;
import com.example.myapplication.R;

import static com.example.myapplication.Common.GALLERY_JSON_FILE_NAME;
import static com.example.myapplication.Common.galleryToJson;
import static com.example.myapplication.Common.goIntent;
import static com.example.myapplication.Common.mGalleryList;
import static com.example.myapplication.Common.galleryAdapter;

import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

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
                goIntent(7);
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
                                galleryWrite();
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

                mGalleryList.add(new ImageFile(0,"칵테일", R.drawable.image1, 4, "20180702", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(1,"휘낭시에", R.drawable.image2, 4, "20190701", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(2,"특등심까스", R.drawable.image3, 2, "20200627", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(3,"냉모밀", R.drawable.image4, 2, "20210404", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(4,"감자탕", R.drawable.image5, 0, "20211111", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(5,"감바스", R.drawable.image6, 3, "20211201", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(6,"고르곤졸라", R.drawable.image7, 3, "20211203", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(7,"샐러드", R.drawable.image8, 3, "20220110", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(8,"대한곱창", R.drawable.image9, 0, "20220212", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(9,"김치말이국수", R.drawable.image10, 0, "20220330", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(10,"라면", R.drawable.image11, 0, "20220524", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(11,"홍대카페", R.drawable.image12, 4, "20220611", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(12,"계란후라이", R.drawable.image14, 0, "20220629", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(13,"고등어구이", R.drawable.image15, 0, "20220701", -1, -1, -1, -1));
                mGalleryList.add(new ImageFile(14,"파전", R.drawable.image16, 0, "20220702", -1, -1, -1, -1));

                galleryWrite();
                galleryAdapter.notifyDataSetChanged();

            }
        });
    }
    @Override
    public void onBackPressed() {
        Common.toPrev(this);
    }
    private void galleryWrite() {
        try{
            FileOutputStream os = openFileOutput(GALLERY_JSON_FILE_NAME, MODE_PRIVATE);
            JSONObject jsonFile = galleryToJson();

            assert jsonFile != null;
            os.write(jsonFile.toString().getBytes());
            os.flush();
            os.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}


