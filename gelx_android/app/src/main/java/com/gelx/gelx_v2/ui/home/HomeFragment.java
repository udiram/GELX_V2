package com.gelx.gelx_v2.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.gelx.gelx_v2.PermanentStorage;
import com.gelx.gelx_v2.R;
import com.gelx.gelx_v2.callbacks.SendImageDataCallback;
import com.gelx.gelx_v2.models.LaneData;
import com.gelx.gelx_v2.models.XY;
import com.gelx.gelx_v2.reposotories.DataProvider;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class HomeFragment extends Fragment {
    private static final int RESULT_LOAD_IMAGE = 9703;
    private static final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;
    private PhotoView uploadImg;
    private Button sendDataBtn;
    private TextView instructionsTxt;
    private SpinKitView spinner;
    private Switch zerocb;
    private Drawable originalImage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);



        final Button uploadBtn = root.findViewById(R.id.uploadBtn);
        uploadImg = (PhotoView) root.findViewById(R.id.uploadedImageView);
        uploadImg.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {


                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                BitmapDrawable drawable = (BitmapDrawable) uploadImg.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                int width = mutableBitmap.getWidth();
                int height = mutableBitmap.getHeight();

                Canvas canvas = new Canvas(mutableBitmap);
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(Color.RED);
                canvas.drawCircle(x*width, y*height, 10, paint);
                uploadImg.setImageBitmap(mutableBitmap);

                Log.i(TAG, x + "::" + y);
                XY data = new XY();
                data.x = x;
                data.y = y;
                DataProvider.addData(data);

            }
        });
        zerocb = root.findViewById(R.id.zerocbSwitch);
        sendDataBtn = root.findViewById(R.id.sendDataBtn);
        spinner = root.findViewById(R.id.spinner);
        spinner.setVisibility(View.GONE);
        sendDataBtn.setVisibility(View.GONE);
        zerocb.setVisibility(View.GONE);

        instructionsTxt = root.findViewById(R.id.instructions);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataProvider.clearDataList();
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                DataProvider.getXyDataList();


//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }


//                Toast.makeText(getContext(), "please click on your ladders, from right to left, followed by your column of interest", Toast.LENGTH_LONG).show();
            }
        });
        zerocb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                }else{

                }
            }
        });
        sendDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String ladderString = PermanentStorage.getInstance().retrieveString(getActivity(), PermanentStorage.LADDER_KEY);
                if(ladderString.isEmpty() || ladderString.equals("[]")){

                    Snackbar noladder =  Snackbar.make(getView(), "please set your ladders before sending image", Snackbar.LENGTH_LONG)
                            .setAction("SET LADDERS", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                                    navController.navigate(R.id.navigation_dashboard);
                                }
                            });
                    noladder.show();


                 return;

                }
                    spinner.setVisibility(View.VISIBLE);
                    try {
                        ColorMatrix matrix = new ColorMatrix();
                        matrix.setSaturation(0); // this just makes it look like grayscale
                        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);

                        uploadImg.setColorFilter(filter);
                        homeViewModel.sendImageDataToServer(getContext(), originalImage, zerocb.isChecked(), new SendImageDataCallback(){
                            @Override
                            public void OnSuccess(String response) {
                                uploadImg.setImageDrawable(null);
                                originalImage = null;

                                instructionsTxt.setVisibility(View.VISIBLE);

                                Toast.makeText(getActivity(), "Data Processed successfully, please check your email!", Toast.LENGTH_LONG).show();

                                spinner.setVisibility(View.GONE);

                                try {
                                    JSONObject responseObj = new JSONObject(response);

                                    DataProvider.parseSaveLaneData(getActivity(), responseObj.getJSONObject("laneData").toString());

                                    DataProvider.parseSaveNucVals(getActivity(), responseObj.getJSONArray("nucValMap").toString());


                                    homeViewModel.createNotificationChannel(getActivity());
                                    PermanentStorage.getInstance().storeString(getActivity(), PermanentStorage.RETURN_IMAGE_KEY, responseObj.getString("image"));
                                    homeViewModel.sendNotification(getActivity());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        @Override
                        public void OnFailure() {

                            Toast.makeText(getActivity(), "Server Error, Please check points or try again later", Toast.LENGTH_LONG).show();
                            DataProvider.clearDataList();
                            spinner.setVisibility(View.GONE);

                        }

                        @Override
                        public void OnEmptyData() {

                            spinner.setVisibility(View.GONE);

                            Toast.makeText(getActivity(), "please select your points", Toast.LENGTH_LONG).show();

                        }
                    });

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });


        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (data == null) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE) {
            Uri selectedImageUri = data.getData();
            uploadImg.setImageURI(selectedImageUri);

            originalImage = uploadImg.getDrawable();

            Snackbar snackbar =  Snackbar.make(getView(), "please click on your ladders, followed by a column", Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DataProvider.clearDataList();
                            sendDataBtn.setVisibility(View.GONE);
                            uploadImg.setImageDrawable(null);
                        }
                    });
            snackbar.show();
            sendDataBtn.setVisibility(View.VISIBLE);
            zerocb.setVisibility(View.VISIBLE);
            instructionsTxt.setVisibility(View.GONE);
        }
    }

}