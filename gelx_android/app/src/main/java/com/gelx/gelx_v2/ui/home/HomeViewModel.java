package com.gelx.gelx_v2.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Base64;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gelx.gelx_v2.callbacks.SendImageDataCallback;
import com.gelx.gelx_v2.models.ImageData;
import com.gelx.gelx_v2.reposotories.DataProvider;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class HomeViewModel extends ViewModel {

    public void sendImageDataToServer(Context context, Drawable drawable, SendImageDataCallback sendImageDataCallback) throws UnsupportedEncodingException {

        if(DataProvider.getXyDataList().isEmpty()){
            sendImageDataCallback.OnEmptyData();
            return;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String imageString = Base64.encodeToString(imageBytes, Base64.URL_SAFE);


        final Random numRandom = new Random();

        int job_id = numRandom.nextInt(10000);


        ImageData imageData = new ImageData();
        imageData.setJob_id(job_id);
        imageData.setImage(imageString);


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(context);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            imageData.setFname(personGivenName);
            imageData.setEmail(personEmail);
            imageData.setLname(personFamilyName);
            imageData.setUser_id(personId);
        }


        DataProvider.sendImageDataToServer(context, imageData, sendImageDataCallback);
    }
}