package com.gelx.gelx_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gelx.gelx_v2.callbacks.SendImageDataCallback;
import com.gelx.gelx_v2.models.ImageData;
import com.gelx.gelx_v2.reposotories.DataProvider;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    private static int RC_SIGN_IN = 9703;
    private static String TAG = LoginActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        final GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        Button signUpLoginBtn = findViewById(R.id.sign_up);

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        findViewById(R.id.sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signuploginIntent = new Intent(LoginActivity.this, ManualRegistrationActivity.class);
                startActivity(signuploginIntent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            updateUI(account);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            Intent launchMain = new Intent(this, MainActivity.class);
            startActivity(launchMain);
        } else{
            Toast.makeText(this, "Login Failed, Please Try again", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            final GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            ImageData imageData = new ImageData();
            imageData.setUsername(account.getGivenName());
            imageData.setPassword(account.getFamilyName());
            // Signed in successfully, show authenticated UI.
            DataProvider.sendUserRegistrationToServer(LoginActivity.this, imageData, new SendImageDataCallback() {
                @Override
                public void OnSuccess() {
                    updateUI(account);
                    Toast.makeText(LoginActivity.this, "Account Created successfully, please check your email!", Toast.LENGTH_LONG).show();

                }

                @Override
                public void OnFailure() {
                    Toast.makeText(LoginActivity.this, "Server Error, could not create new user profile", Toast.LENGTH_LONG).show();

                }

                @Override
                public void OnEmptyData() {
                    Toast.makeText(LoginActivity.this, "please Register", Toast.LENGTH_LONG).show();

                }
            });
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
}