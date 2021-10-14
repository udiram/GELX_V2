package com.gelx.gelx_v2;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.gelx.gelx_v2.callbacks.CreateUserCallback;
import com.gelx.gelx_v2.ui.home.HomeViewModel;

import java.io.UnsupportedEncodingException;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment {
    private HomeViewModel homeViewModel;

    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of((FragmentActivity) getContext()).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        final EditText name = view.findViewById(R.id.name);
        final EditText lname = view.findViewById(R.id.lname);
        final EditText number = view.findViewById(R.id.number);
        final EditText emailid = view.findViewById(R.id.email);
        final EditText password = view.findViewById(R.id.password);
        final EditText address = view.findViewById(R.id.address);
        boolean clicked = false;
        final String ischecked = "yes";
        final Button registerBtn = view.findViewById(R.id.register);
        final CheckBox checkboxvariable = (CheckBox) view.findViewById(R.id.terms);


        TextView tctext = (TextView) view.findViewById(R.id.textView2);

        tctext.setText(Html.fromHtml("I have read and agree to the " +
                "<a href='google.com'>TERMS AND CONDITIONS</a>"));
        tctext.setMovementMethod(LinkMovementMethod.getInstance());
        tctext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/synth-med-biotechnology/synthmed_dashboard_server#synthmed_dashboard_server"));
                startActivity(browserIntent);

                checkboxvariable.setText("");

            }
        });
        checkboxvariable.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (checkboxvariable.isChecked()) {

                    registerBtn.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "agreed to terms and conditions", Toast.LENGTH_LONG).show();

                } else {

                    registerBtn.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), "please agree to the terms and conditions to proceed", Toast.LENGTH_LONG).show();

                }
            }

        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidEmail(emailid.getText())) {
                    Toast.makeText(getActivity(), "please enter a valid email address to continue", Toast.LENGTH_LONG).show();
                    return;
                }


                if (name.getText().toString().isEmpty() || lname.getText().toString().isEmpty() || number.getText().toString().isEmpty() || emailid.getText().toString().isEmpty() || password.getText().toString().isEmpty() || address.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill in all fields to continue", Toast.LENGTH_LONG).show();
                } else {
                    PermanentStorage.getInstance().storeString(getActivity(), PermanentStorage.GOOGLE_GIVEN_NAME_KEY, name.getText().toString());
                    PermanentStorage.getInstance().storeString(getActivity(), PermanentStorage.GOOGLE_FAMILY_NAME_KEY, lname.getText().toString());
                    PermanentStorage.getInstance().storeString(getActivity(), PermanentStorage.PHONE_KEY, number.getText().toString());
                    PermanentStorage.getInstance().storeString(getActivity(), PermanentStorage.EMAIL_KEY, emailid.getText().toString());
                    PermanentStorage.getInstance().storeString(getActivity(), PermanentStorage.ACCOUNT_ID_KEY, accountGeneration(name.getText().toString()));
//                  PermanentStorage.getInstance().storeString(getActivity(), PermanentStorage.ACCOUNT_ID_KEY, GenerateRandomString.randomString(10));
                    PermanentStorage.getInstance().storeString(getActivity(), PermanentStorage.PASSWORD_KEY, password.getText().toString());
                    PermanentStorage.getInstance().storeString(getActivity(), PermanentStorage.ADDRESS_KEY, address.getText().toString());


                    Intent report = new Intent(getActivity(), MainActivity.class);
                    startActivity(report);

                    try {
                        homeViewModel.sendUserRegistrationToServer(getActivity(), PermanentStorage.GOOGLE_GIVEN_NAME_KEY, new CreateUserCallback() {
                            @Override
                            public void OnSuccess() {

                                Toast.makeText(getActivity(), "Account Created successfully, please check your email!", Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void OnFailure() {

                                Toast.makeText(getActivity(), "Server Error, could not create new user profile", Toast.LENGTH_LONG).show();
                                PermanentStorage.getInstance().storeString(getActivity(), PermanentStorage.ERROR_KEY, "Error 500");
                                Log.i("Failure", "500");
                            }

                            @Override
                            public void OnEmptyData() {


                                Toast.makeText(getActivity(), "please Register", Toast.LENGTH_LONG).show();

                            }
                        });
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        return view;
    }

    private String accountGeneration(String name) {

        Random random = new Random();
        int randomNumber = random.nextInt(1000000);

        String result = "" + randomNumber;

        return result;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    
}




