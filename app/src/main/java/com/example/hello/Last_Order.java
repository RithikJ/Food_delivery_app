package com.example.hello;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hello.Adapters.LastOrderAdapter;
import com.example.hello.Adapters.OrderAdapter;
import com.example.hello.Models.LastOrderModel;
import com.example.hello.Models.OrderModel;
import com.example.hello.databinding.ActivityLastOrderBinding;
import com.example.hello.databinding.ActivityOrderBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Last_Order extends AppCompatActivity {

    ActivityLastOrderBinding binding;
    ArrayList<LastOrderModel> orders = new ArrayList<>();
    FirebaseFirestore fStore;
    LastOrderAdapter adapter;
    RecyclerView review;
    FirebaseAuth fAuth;
    String userId;
    public int mon =0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLastOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        review = binding.orderRecyclerView;
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        review.setHasFixedSize(true);
        review.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<LastOrderModel> options =
                new FirebaseRecyclerOptions.Builder<LastOrderModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(userId).child("Order List"),LastOrderModel.class)
                        .build();

        adapter = new LastOrderAdapter(options);
        review.setAdapter(adapter);



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