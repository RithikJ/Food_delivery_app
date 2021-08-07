package com.example.hello;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hello.databinding.ActivityPhoneAuthenticationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Phone_authentication extends AppCompatActivity {

    ActivityPhoneAuthenticationBinding binding;
    FirebaseAuth auth;
    String verificationCode;
    CountryCodePicker ccp;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneAuthenticationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ccp = (CountryCodePicker) binding.ccp;
        binding.ccp.registerCarrierNumberEditText(binding.mobileNumber);
        auth = FirebaseAuth.getInstance();
     //   userId = auth.getCurrentUser().getUid();
        binding.mobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(binding.mobileNumber.getText().toString().length() == 10)
                {
                    binding.otpGen.setEnabled(true);
                }
                else
                {
                    binding.otpGen.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.otpGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.verifedPhoneBtn.setEnabled(true);
                initiateOtp();

            }
        });
        binding.verifedPhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = binding.otp.getText().toString().trim();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode,code);
                signInWithPhoneAuthCredential(credential);
            }
        });
    }
    void initiateOtp()
    {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(binding.ccp.getFormattedFullNumber().toString().trim())       // Phone number to verify
                        .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onCodeSent(@NonNull  String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationCode = s;

                            }

                            @Override
                            public void onVerificationCompleted(@NonNull  PhoneAuthCredential phoneAuthCredential) {

                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull  FirebaseException e) {
                                Log.d("number",binding.ccp.getFormattedFullNumber().toString().trim() );
                                Toast.makeText(getApplicationContext(),"Why ?? Verification Failed",Toast.LENGTH_LONG).show();
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child(userId).child("User Details");

                            final HashMap<String,Object> cartMap = new HashMap<>();
                            cartMap.put("phone number",binding.mobileNumber.getText().toString());

                            cartListRef.updateChildren(cartMap);
                            Intent intent = new Intent(Phone_authentication.this,MainActivity.class);
                            startActivity(intent);
                            Phone_authentication.this.finish();

                        } else {
                            // Sign in failed, display a message and update the UI

                            Toast.makeText(getApplicationContext(),"Verification Failed here ",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}