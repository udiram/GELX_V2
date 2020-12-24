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
import com.gelx.gelx_v2.models.ImageData;
import com.gelx.gelx_v2.models.XY;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DataProvider {

    private static final String SEND_IMAGE_URL = "http://10.0.2.2:8000/polls/image/";
    private static final String SEND_DATA_URL = "http://10.0.2.2:8000/polls/analysis/";


    private static List<XY> xyDataList = new ArrayList<>();

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
    public static void sendImageDataToServer(Context context, final ImageData imageData) {

        imageData.setLadderPercents(xyDataList);
        final String jsonString = new Gson().toJson(imageData);
        Log.i("JSON", jsonString);

        final String xystring = new Gson().toJson(xyDataList);
        Log.i("xy", xystring);

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SEND_IMAGE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        clearDataList();
                        Log.i("Image Sent", response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
