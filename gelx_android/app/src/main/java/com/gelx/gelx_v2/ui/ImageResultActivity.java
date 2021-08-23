package com.gelx.gelx_v2.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.gelx.gelx_v2.PermanentStorage;
import com.gelx.gelx_v2.R;
import com.gelx.gelx_v2.ViewNucValsActivity;
import com.gelx.gelx_v2.reposotories.DataProvider;

import java.io.File;
import java.io.FileOutputStream;

public class ImageResultActivity extends AppCompatActivity {


    private Bitmap decodedByte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_result);

        String imageb64 = PermanentStorage.getInstance().retrieveString(this, PermanentStorage.RETURN_IMAGE_KEY);

        ImageView imageView = findViewById(R.id.resultImage);

        if (!imageb64.isEmpty()) {
            byte[] decodedString = Base64.decode(imageb64, Base64.DEFAULT);

            decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
            imageView.setImageBitmap(decodedByte);

        }

        Button viewresults = findViewById(R.id.view_list);
        viewresults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ImageResultActivity.this, ViewNucValsActivity.class));
//                Toast.makeText(getActivity(), "BTN pressed", Toast.LENGTH_LONG).show();
            }
        });

        Button share = findViewById(R.id.sharebtn);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), ViewNucValsActivity.class));
//                Toast.makeText(getActivity(), "BTN pressed", Toast.LENGTH_LONG).show();

                File mypath = new File(ImageResultActivity.this.getCacheDir(), "thumbnail.png");

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(mypath);
                    decodedByte.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();

                    Uri imageUri = FileProvider.getUriForFile(
                            ImageResultActivity.this,
                            "com.example.homefolder.example.provider",
                            mypath);

                    Intent shareit=new Intent(Intent.ACTION_SEND);
                    shareit.setType("image/png");
                    shareit.putExtra(Intent.EXTRA_STREAM, imageUri);
                    startActivity(Intent.createChooser(shareit, "Share Image Via"));


                } catch (Exception e) {
                    Log.e("SAVE_IMAGE", e.getMessage(), e);
                }
            }
        });


    }
}