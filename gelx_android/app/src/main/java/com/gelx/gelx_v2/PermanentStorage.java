package com.gelx.gelx_v2;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PermanentStorage {

    public static final String USER_KEY = "user";
    public static final String USER_ID_KEY = "user_id";
    public static final String PHONE_KEY = "phone";
    public static final String EMAIL_KEY = "email";
    public static final String ACCOUNT_ID_KEY = "random_account";
    public static final String PASSWORD_KEY = "company";
    public static final String ADDRESS_KEY = "address";
    public static final String GOOGLE_EMAIL_KEY = "gmail";
    public static final String GOOGLE_NAME_KEY = "gname";
    public static final String GOOGLE_GIVEN_NAME_KEY = "ggivenname";
    public static final String GOOGLE_FAMILY_NAME_KEY = "gfamilyame";
    public static final String GOOGLE_ID_KEY = "gid";
    public static final String ERROR_KEY = "ERROR_KEY";
    public static final String LADDER_KEY = "LADDER_KEY";
    public static final String RETURN_IMAGE_KEY = "RETURN_IMAGE_KEY";






    private static PermanentStorage permanentStorage;

    public static synchronized PermanentStorage getInstance() {
        if (permanentStorage == null) {
            permanentStorage = new PermanentStorage();
        }
        return permanentStorage;
    }

    public void storeString(Context context, String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String retrieveString(Context context, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String result = preferences.getString(key, "");
        return result;
    }

//    public void storeInt(Context context, String key, int value) {
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putInt(key, value);
//        editor.apply();
//    }
//
//    public String retrieveInt(Context context, String key) {
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        int result = preferences.getInt(key, -1);
//        return result;
//    }


}



