package com.gelx.gelx_v2.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Base64;

import androidx.lifecycle.ViewModel;

import com.gelx.gelx_v2.PermanentStorage;
import com.gelx.gelx_v2.callbacks.SendImageDataCallback;
import com.gelx.gelx_v2.models.ImageData;
import com.gelx.gelx_v2.models.LadderData;
import com.gelx.gelx_v2.reposotories.DataProvider;
import com.gelx.gelx_v2.ui.dashboard.DashboardViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;

public class HomeViewModel extends ViewModel {

    public void sendUserRegistrationToServer(Context context, String string, SendImageDataCallback sendImageDataCallback) throws UnsupportedEncodingException{
        ImageData imageData = new ImageData();
        imageData.setUsername(PermanentStorage.GOOGLE_GIVEN_NAME_KEY);
        imageData.setPassword(PermanentStorage.PASSWORD_KEY);


        DataProvider.sendUserRegistrationToServer(context, imageData, sendImageDataCallback);
    }

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

            PermanentStorage.getInstance().storeString(context, PermanentStorage.GOOGLE_GIVEN_NAME_KEY, personGivenName );
//            imageData.setUsername(personGivenName);
            imageData.setEmail(personEmail);
            imageData.setLname(personFamilyName);
            imageData.setUser_id(personId);
            imageData.setFname(personGivenName);
        }else{
            imageData.setEmail(PermanentStorage.getInstance().retrieveString(context, PermanentStorage.EMAIL_KEY));
            imageData.setFname(PermanentStorage.getInstance().retrieveString(context, PermanentStorage.GOOGLE_GIVEN_NAME_KEY));
            imageData.setLname(PermanentStorage.getInstance().retrieveString(context, PermanentStorage.GOOGLE_FAMILY_NAME_KEY));
            imageData.setUser_id(PermanentStorage.getInstance().retrieveString(context, PermanentStorage.ACCOUNT_ID_KEY));


        }
        List<LadderData> ladders = DashboardViewModel.retrieveLadderData(context);

        imageData.setLadderData(ladders);

        DataProvider.sendImageDataToServer(context, imageData, sendImageDataCallback);
    }
}