package com.gelx.gelx_v2.ui.notifications;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.gelx.gelx_v2.ViewNucValsActivity;
import com.gelx.gelx_v2.PermanentStorage;
import com.gelx.gelx_v2.R;

import java.io.File;
import java.io.FileOutputStream;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    private Bitmap decodedByte;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        String imageb64 = PermanentStorage.getInstance().retrieveString(getActivity(), PermanentStorage.RETURN_IMAGE_KEY);

        ImageView imageView = root.findViewById(R.id.resultImage);

        if (!imageb64.isEmpty()) {
            byte[] decodedString = Base64.decode(imageb64, Base64.DEFAULT);
             decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
            imageView.setImageBitmap(decodedByte);




        }

        Button viewresults = root.findViewById(R.id.view_list);
        viewresults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ViewNucValsActivity.class));
//                Toast.makeText(getActivity(), "BTN pressed", Toast.LENGTH_LONG).show();
            }
        });

        Button share = root.findViewById(R.id.sharebtn);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), ViewNucValsActivity.class));
//                Toast.makeText(getActivity(), "BTN pressed", Toast.LENGTH_LONG).show();

                File mypath = new File(getActivity().getCacheDir(), "thumbnail.png");

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(mypath);
                    decodedByte.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();

                    Uri imageUri = FileProvider.getUriForFile(
                            getActivity(),
                            "com.example.homefolder.example.provider",
                            mypath);

                    Intent shareit=new Intent(Intent.ACTION_SEND);
                    shareit.setType("image/png");
                    shareit.putExtra(Intent.EXTRA_STREAM, imageUri);
                    startActivity(Intent.createChooser(shareit, "Share Image Via"));


                } catch (Exception e) {
                    Log.e("SAVE_IMAGE", e.getMessage(), e);
                }
            }
        });




//        final TextView textView = root.findViewById(R.id.text_notifications);
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}