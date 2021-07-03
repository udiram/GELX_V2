package com.gelx.gelx_v2.ui.home;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Base64;

import androidx.lifecycle.ViewModel;

import com.gelx.gelx_v2.PermanentStorage;
import com.gelx.gelx_v2.callbacks.CreateUserCallback;
import com.gelx.gelx_v2.callbacks.SendImageDataCallback;
import com.gelx.gelx_v2.models.ImageData;
import com.gelx.gelx_v2.models.LadderData;
import com.gelx.gelx_v2.reposotories.DataProvider;
import com.gelx.gelx_v2.ui.ImageResultActivity;
import com.gelx.gelx_v2.ui.dashboard.DashboardViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;

public class HomeViewModel extends ViewModel {

    public void sendUserRegistrationToServer(Context context, String string, CreateUserCallback createUserCallback) throws UnsupportedEncodingException{
        ImageData imageData = new ImageData();
        imageData.setUsername(PermanentStorage.GOOGLE_GIVEN_NAME_KEY);
        imageData.setPassword(PermanentStorage.PASSWORD_KEY);


        DataProvider.sendUserRegistrationToServer(context, imageData, createUserCallback);
    }

    public void sendImageDataToServer(Context context, Drawable drawable, boolean isZeroCalibration, SendImageDataCallback sendImageDataCallback) throws UnsupportedEncodingException {

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
        imageData.setZeroCalibration(isZeroCalibration);


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



    protected void sendNotification(Context context) {

        int notificationID = 101;

        String channelID = "com.gelx.gelx_v2";


        Intent resultIntent = new Intent(context, ImageResultActivity.class);

        PendingIntent pendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        Notification notification =
                new Notification.Builder(context,
                        channelID)
                        .setContentTitle("New Message")
                        .setContentText("You've received new messages.")
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setChannelId(channelID)
                        .setContentIntent(pendingIntent)
                        .build();

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationID, notification);
    }


    protected void createNotificationChannel(Context context) {


        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel =
                new NotificationChannel("com.gelx.gelx_v2", "GELX Channel", importance);

        channel.setDescription("image returned notification");
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);
        channel.setVibrationPattern(
                new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        notificationManager.createNotificationChannel(channel);
    }
}