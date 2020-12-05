package com.gelx.gelx_v2.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gelx.gelx_v2.models.ImageData;
import com.gelx.gelx_v2.reposotories.DataProvider;

import java.io.ByteArrayOutputStream;
import java.util.Random;

public class HomeViewModel extends ViewModel {

    public void sendImageToServer(Context context, Drawable drawable) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String imageString = Base64.encodeToString(imageBytes, Base64.URL_SAFE);

        final Random numRandom = new Random();

        int job_id = numRandom.nextInt(10000);

        ImageData imageData = new ImageData();
        imageData.setJob_id(job_id);
        imageData.setImage(imageString);


        DataProvider.sendImageToServer(context, imageData);
    }
}