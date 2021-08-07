package com.example.hello;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.example.hello.Adapters.OrderAdapter;
import com.example.hello.Models.Cart;
import com.example.hello.Models.OrderModel;
import com.example.hello.databinding.ActivityOrderBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Order_Activity extends AppCompatActivity {

    ActivityOrderBinding binding;
    ArrayList<OrderModel> orders = new ArrayList<>();
    FirebaseFirestore fStore;
    OrderAdapter adapter;
    RecyclerView review;
    FirebaseAuth fAuth;
    String userId;
    public int mon =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        review = binding.orderRecyclerView;
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        review.setHasFixedSize(true);
        review.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<OrderModel> options =
                new FirebaseRecyclerOptions.Builder<OrderModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child(userId).child("Cart List"),OrderModel.class)
                .build();

        adapter = new OrderAdapter(options);
        review.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child(userId).child("Cart List");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                mon =0;
                for(DataSnapshot item :snapshot.getChildren())
                {
                    int pr = Integer.parseInt(item.child("price").getValue().toString());
                    int qua = Integer.parseInt(item.child("quantity").getValue().toString());
                    mon = mon + pr*qua;

                }
                binding.money.setText("Rs" +String.valueOf(mon));

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

        binding.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Order_Activity.this,Address.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}