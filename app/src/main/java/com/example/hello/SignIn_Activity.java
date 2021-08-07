package com.example.hello;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.hello.databinding.ActivitySignInBinding;
import com.example.hello.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignIn_Activity extends AppCompatActivity {
    String mailpattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";
    ActivitySignInBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        binding.mail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailAndPassword();
            }
        });
        binding.textAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(SignIn_Activity.this,SignUp_Activity.class);
                startActivity(mainIntent);
                SignIn_Activity.this.finish();
            }
        });

    }
    void checkInputs() {
        if(!TextUtils.isEmpty(binding.mail.getText()) && !TextUtils.isEmpty(binding.password.getText()) )
        {
            binding.btnSignIn.setEnabled(true) ;
        }
    }
    void emailAndPassword(){
        if(binding.mail.getText().toString().matches(mailpattern))
        {
            firebaseAuth.signInWithEmailAndPassword(binding.mail.getText().toString(), binding.password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Intent mainIntent = new Intent(SignIn_Activity.this,MainActivity.class);
                        startActivity(mainIntent);
                        SignIn_Activity.this.finish();

                    }
                    else {
                        Toast.makeText(SignIn_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
    }
}