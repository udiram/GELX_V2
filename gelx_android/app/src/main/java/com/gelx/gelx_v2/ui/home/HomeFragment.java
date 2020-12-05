package com.gelx.gelx_v2.ui.home;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gelx.gelx_v2.R;
import com.github.chrisbanes.photoview.PhotoView;

public class HomeFragment extends Fragment {
    private static final int RESULT_LOAD_IMAGE = 9703;

    private HomeViewModel homeViewModel;
    private PhotoView uploadImg;
    private Button sendDataBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        final Button uploadBtn = root.findViewById(R.id.uploadBtn);
        uploadImg = (PhotoView) root.findViewById(R.id.uploadedImageView);

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
                homeViewModel.sendImageToServer(getContext(), uploadImg.getDrawable());
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