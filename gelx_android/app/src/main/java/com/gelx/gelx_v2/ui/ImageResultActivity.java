package com.gelx.gelx_v2.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import com.gelx.gelx_v2.R;
import com.gelx.gelx_v2.reposotories.DataProvider;

public class ImageResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_result);

        ImageView imageView = findViewById(R.id.resultImage);

        if (!DataProvider.returnedImage.isEmpty()) {
            byte[] decodedString = Base64.decode(DataProvider.returnedImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
            imageView.setImageBitmap(decodedByte);
        }

    }
}