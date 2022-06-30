package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ClickedItemActivity extends AppCompatActivity {

    ImageButton TrashBtn;

    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_item);

        TrashBtn = findViewById(R.id.ic_delete);
        TrashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "아이템이 삭제되었습니다", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.tvName);

        Intent intent = getIntent();

        if(intent.getExtras() != null){
            String selectedName = intent.getStringExtra("name");
            int selectedImage = intent.getIntExtra("image", 0);

            textView.setText(selectedName);
            imageView.setImageResource(selectedImage);
        }



        //set value

    }
}