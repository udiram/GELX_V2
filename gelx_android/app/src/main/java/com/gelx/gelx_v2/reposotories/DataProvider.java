package com.gelx.gelx_v2.reposotories;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gelx.gelx_v2.PermanentStorage;
import com.gelx.gelx_v2.callbacks.SendImageDataCallback;
import com.gelx.gelx_v2.models.ImageData;
import com.gelx.gelx_v2.models.LadderData;
import com.gelx.gelx_v2.models.XY;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DataProvider {

    private static final String SEND_IMAGE_URL = "http://10.0.2.2:8000/polls/image/";
    private static final String SEND_DATA_URL = "http://10.0.2.2:8000/polls/analysis/";
    private static final String USER_REG_URL = "http://10.0.2.2:8000/polls/user/";
    private static final String LADDER_DATA_URL = "http://10.0.2.2:8000/polls/ladder/";


    private static List<XY> xyDataList = new ArrayList<>();
    private static String user = new String();

    public static List<XY> getXyDataList() {
        return xyDataList;
    }

    public static void clearDataList() {
        xyDataList.clear();
    }

    public static boolean addData(XY xy) {
        if (xyDataList.size() < 10) {

            xyDataList.add(xy);

            return true;

        } else {
            return false;
        }
    }

    public static void sendLadderDataToServer(final Context context, final List<LadderData> ladderDataList){

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LADDER_DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Ladder Data Sent", response);
                    }
                },
                new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Ladder Created", "error: " + error.getMessage());
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String ladderString = new Gson().toJson(ladderDataList);

                return ladderString.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("auth-key", "1234");
                return params;
            }
        };
        // Add the request to the RequestQueue.
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);

    }


    public static void sendUserRegistrationToServer(final Context context, final ImageData imageData, final SendImageDataCallback sendImageDataCallback){

        final String userKey = PermanentStorage.getInstance().retrieveString(context, PermanentStorage.GOOGLE_GIVEN_NAME_KEY);
        Log.i("userkey", userKey);

        final String password = PermanentStorage.getInstance().retrieveString(context, PermanentStorage.PASSWORD_KEY);
        Log.i("password", password);

        RequestQueue queue = Volley.newRequestQueue(context);

        if (!userKey.isEmpty() && !password.isEmpty()){
            imageData.setUsername(userKey);
            imageData.setPassword(password);
        }



        final String userJson = new Gson().toJson(imageData);
        Log.i("userJSON", userJson);

//          final String userJson = new Gson().toJson("username " + ":" + " newtest" + "," + " password " + ":" +  " newtestpword");
//          Log.i("userJSON", userJson);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, USER_REG_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        clearDataList();
                        sendImageDataCallback.OnSuccess();
                        Log.i("User Created", response);

                        try {
                            JSONObject responseJson = new JSONObject(response);
                            int user_id = responseJson.getInt("user_id");
//                            PermanentStorage.getInstance().storeInt(context, PermanentStorage.USER_ID_KEY, user_id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                sendImageDataCallback.OnFailure();
                Log.e("User Created", "error: " + error.getMessage());
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return userJson.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("auth-key", "1234");
                return params;
            }
        };
        // Add the request to the RequestQueue.
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);

    }




    public static void sendImageDataToServer(final Context context, final ImageData imageData, final SendImageDataCallback sendImageDataCallback) {

        imageData.setLadderPercents(xyDataList);

        final String xystring = new Gson().toJson(xyDataList);
        Log.i("xy", xystring);

        final String jsonString = new Gson().toJson(imageData);
        Log.i("JSON", jsonString);

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SEND_IMAGE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        clearDataList();
                        sendImageDataCallback.OnSuccess();
                        Log.i("Image Sent", response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                sendImageDataCallback.OnFailure();
                Log.e("Image Sent", "error: " + error.getMessage());
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return jsonString.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("auth-key", "1234");
//                params.put("user_id", PermanentStorage.getInstance().retrieveInt(context, PermanentStorage.USER_ID_KEY));
                return params;
            }
        };
        // Add the request to the RequestQueue.
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);

    }



//    public static void sendDataToServer(Context context) {
//        final String jsonArrayString = new Gson().toJson(xyDataList);
//        Log.i("JSON", jsonArrayString);
//
//        RequestQueue queue = Volley.newRequestQueue(context);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, SEND_DATA_URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        Log.i("DataSent", response);
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("Data Sent", "error: " + error.getMessage());
//            }
//        }) {
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                return jsonArrayString.getBytes();
//            }
//        };
//        // Add the request to the RequestQueue.
//        queue.add(stringRequest);
//
//    }
}
