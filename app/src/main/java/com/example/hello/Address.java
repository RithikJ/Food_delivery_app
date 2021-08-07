package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.hello.databinding.ActivityAddressBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Address extends AppCompatActivity {
    ActivityAddressBinding binding;
    FirebaseAuth auth;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddressBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        setContentView(binding.getRoot());
        binding.addressTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(binding.addressTxt.getText().toString().length() >= 5)
                {
                    binding.payBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference prod = FirebaseDatabase.getInstance().getReference().child(userId);
                DatabaseReference address = prod.child("User Details");
                final HashMap<String,Object> cartMap = new HashMap<>();
                cartMap.put("Address",binding.addressTxt.getText().toString());

                address.updateChildren(cartMap);

                DatabaseReference orderList = FirebaseDatabase.getInstance().getReference().child(userId).child("Order List");
                prod.child("Cart List").get().addOnSuccessListener(dataSnapshot -> {prod.child("Order List").setValue(dataSnapshot.getValue());
                prod.child("Cart List").removeValue();
                });

                Toast.makeText(Address.this, "Ordered Successfully", Toast.LENGTH_SHORT).show();
                Intent mainIntent = new Intent(Address.this,MainActivity.class);
                startActivity(mainIntent);
                Address.this.finish();

                }
        });
    }
}