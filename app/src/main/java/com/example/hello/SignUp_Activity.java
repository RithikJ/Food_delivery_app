package com.example.hello;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hello.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp_Activity extends AppCompatActivity {
    String mailpattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";
    ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
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
        binding.name.addTextChangedListener(new TextWatcher() {
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
        binding.confirmPassword.addTextChangedListener(new TextWatcher() {
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

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailAndPassword();
            }
        });
        binding.textAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(SignUp_Activity.this,SignIn_Activity.class);
                startActivity(mainIntent);
                SignUp_Activity.this.finish();
            }
        });

    }
    void checkInputs() {
        if(!TextUtils.isEmpty(binding.mail.getText()) && !TextUtils.isEmpty(binding.name.getText()) && !TextUtils.isEmpty(binding.password.getText()) && !TextUtils.isEmpty(binding.confirmPassword.getText())  )
        {
            binding.btnSignUp.setEnabled(true) ;
        }
    }

    void emailAndPassword(){

        if(binding.mail.getText().toString().matches(mailpattern))
        {
            if(binding.password.getText().toString().equals(binding.confirmPassword.getText().toString()))
            {

                auth.createUserWithEmailAndPassword(binding.mail.getText().toString(), binding.password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            Map<Object,String > userdata = new HashMap<>();

                            userdata.put("fullname",binding.name.getText().toString());
                            Toast.makeText(SignUp_Activity.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                            firebaseFirestore.collection("Users")
                                    .add(userdata)
                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull  Task<DocumentReference> task) {

                                            if(task.isSuccessful())
                                            {
                                                Intent mainIntent = new Intent(SignUp_Activity.this,MainActivity.class);
                                                startActivity(mainIntent);
                                                SignUp_Activity.this.finish();
                                            }
                                            else
                                            {
                                                Toast.makeText(SignUp_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });




                        }
                        else
                        {
                            Toast.makeText(SignUp_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
            else
            {
                Toast.makeText(SignUp_Activity.this, "password_error", Toast.LENGTH_SHORT).show();
            }

        }
        else
        {
            Toast.makeText(SignUp_Activity.this, "email_error", Toast.LENGTH_SHORT).show();

        }

    }
}