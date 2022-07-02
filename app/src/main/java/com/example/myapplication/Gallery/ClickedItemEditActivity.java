package com.example.myapplication.Gallery;

import static com.example.myapplication.Common.galleryAdapter;
import static com.example.myapplication.Common.mGalleryList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;

public class ClickedItemEditActivity extends AppCompatActivity implements View.OnClickListener{

    ClickedItemActivity clickedItemActivity = (ClickedItemActivity)ClickedItemActivity.clickedItemActivity;

    ImageButton CheckBtn;

    ImageView imageView;
    EditText editName;

    int selectedImage = -1;
    int idx = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_item_edit);

        clickedItemActivity.finish();

        imageView = findViewById(R.id.imageView);
        editName = findViewById(R.id.tvNameEdit);

        String selectedName = "";

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            selectedName = intent.getStringExtra("name");
            selectedImage = intent.getIntExtra("image", 0);
            idx = intent.getIntExtra("index", 0);

            imageView.setImageResource(selectedImage);
        }else{
            finish();
        }

        CheckBtn = findViewById(R.id.ic_check);
        CheckBtn.setOnClickListener(this);

        editName.setText(selectedName);
    }

    public void onClick(View v) {
        String name = editName.getText().toString();

        ImageFile image = mGalleryList.get(idx);
        image.setName(name);

        mGalleryList.set(idx, image);
        galleryAdapter.notifyDataSetChanged();

        startActivity(new Intent(ClickedItemEditActivity.this,
                ClickedItemActivity.class).putExtra("name", mGalleryList.get(idx).getName()).putExtra("image", mGalleryList.get(idx).getImage()).putExtra("index", idx));

        finish();

    }
}