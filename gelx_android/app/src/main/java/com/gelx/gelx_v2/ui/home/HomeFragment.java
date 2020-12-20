package com.gelx.gelx_v2.ui.home;

import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.gelx.gelx_v2.R;
import com.gelx.gelx_v2.models.XY;
import com.gelx.gelx_v2.reposotories.DataProvider;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.UnsupportedEncodingException;

public class HomeFragment extends Fragment {
    private static final int RESULT_LOAD_IMAGE = 9703;
    private static final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;
    private PhotoView uploadImg;
    private Button sendDataBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        final Button uploadBtn = root.findViewById(R.id.uploadBtn);
        uploadImg = (PhotoView) root.findViewById(R.id.uploadedImageView);
        uploadImg.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                Log.i(TAG, x + "::" + y);
                XY data = new XY();
                data.x = x;
                data.y = y;
                DataProvider.addData(data);
            }
        });
        sendDataBtn = root.findViewById(R.id.sendDataBtn);
        sendDataBtn.setVisibility(View.GONE);

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        sendDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ColorMatrix matrix = new ColorMatrix();
                    matrix.setSaturation(0); // this just makes it look like grayscale
                    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                    uploadImg.setColorFilter(filter);

                    homeViewModel.sendImageDataToServer(getContext(), uploadImg.getDrawable());
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

            sendDataBtn.setVisibility(View.VISIBLE);
        }
    }

}