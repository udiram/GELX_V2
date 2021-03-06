package com.gelx.gelx_v2.ui.notifications;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.gelx.gelx_v2.ViewNucValsActivity;
import com.gelx.gelx_v2.PermanentStorage;
import com.gelx.gelx_v2.R;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        String imageb64 = PermanentStorage.getInstance().retrieveString(getActivity(), PermanentStorage.RETURN_IMAGE_KEY);

        ImageView imageView = root.findViewById(R.id.resultImage);

        if (!imageb64.isEmpty()) {
            byte[] decodedString = Base64.decode(imageb64, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
            imageView.setImageBitmap(decodedByte);
        }

        Button sendLadderBtn = root.findViewById(R.id.view_list);
        sendLadderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ViewNucValsActivity.class));
                Toast.makeText(getActivity(), "BTN pressed", Toast.LENGTH_LONG).show();
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