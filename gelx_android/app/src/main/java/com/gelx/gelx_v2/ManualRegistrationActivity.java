package com.gelx.gelx_v2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ManualRegistrationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_registration);

        String name = PermanentStorage.getInstance().retrieveString(this, PermanentStorage.GOOGLE_GIVEN_NAME_KEY);
        String serverError = PermanentStorage.getInstance().retrieveString(this, PermanentStorage.ERROR_KEY);

        if (name.isEmpty()) {
            Fragment regFragment = new RegistrationFragment();

            loadFragment(regFragment);
        } else {
            if (serverError.isEmpty()) {

//                Toast.makeText(this, "Server Error, could not create new user profile", Toast.LENGTH_LONG).show();
                Intent menuActivity = new Intent(ManualRegistrationActivity.this, MainActivity.class);
//
                startActivity(menuActivity);
//
            }else{
                    Toast.makeText(this, "Secure Sign on Error, could not create new user profile", Toast.LENGTH_LONG).show();
                Intent restart = new Intent(ManualRegistrationActivity.this, LoginActivity.class);
//
                startActivity(restart);
            }
//            Fragment menuFragment = new MenuFragment();
//
//            loadFragment(menuFragment);

        }
        }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit(); // save the changes
    }
}
