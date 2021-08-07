package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hello.Models.Cart;
import com.example.hello.databinding.ActivityDetailBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;

public class Detail_Activity extends AppCompatActivity {
    TextView name;
    TextView price;
    TextView description;
    TextView total;
    ImageView image;
    ActivityDetailBinding binding;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        Intent intent = getIntent();
        String Sname = intent.getStringExtra("name");
        String Sprice = intent.getStringExtra("price");
        String Sdescription = intent.getStringExtra("description");
        int Simage = intent.getIntExtra("image",0);

        binding.name.setText(Sname);
        binding.price.setText(Sprice);
        binding.description.setText(Sdescription);
        binding.foodimage.setImageResource(Simage);

        binding.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val = Integer.parseInt(binding.total.getText().toString());
                val+=1;
                binding.total.setText(String.valueOf(val));
            }
        });
        binding.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val = Integer.parseInt(binding.total.getText().toString());
                val-=1;
                if(val <=0)
                {
                    val =0;
                }
                binding.total.setText(String.valueOf(val));

            }
        });
        binding.orderPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(binding.total.getText().toString()) >0) {
                    addToCart();
                }
                Intent intent = new Intent(Detail_Activity.this, MainActivity.class);
                startActivity(intent);
                Detail_Activity.this.finish();
            }
        });

    }
    private void addToCart()
    {
        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child(userId).child("Cart List");

        final HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("price",binding.price.getText().toString());
        cartMap.put("productName",binding.name.getText().toString());
        cartMap.put("quantity",binding.total.getText().toString());
        cartListRef.child(binding.name.getText().toString()).updateChildren(cartMap);

    }


}