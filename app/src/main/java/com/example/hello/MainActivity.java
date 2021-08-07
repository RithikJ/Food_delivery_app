package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.hello.Adapters.OrderAdapter;
import com.example.hello.Adapters.RecipeAdapter;
import com.example.hello.Models.Recipe_model;
import com.example.hello.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        ArrayList<Recipe_model> list = new ArrayList<>();
        list.add(new Recipe_model(R.drawable.pizza, "Pizza", "170", "Nice"));
        list.add(new Recipe_model(R.drawable.burger, "Burger", "170", "Nice"));
        list.add(new Recipe_model(R.drawable.pav_bhaji, "Pav Bhaji", "170", "Nice"));
        list.add(new Recipe_model(R.drawable.paneer_chilli, "Paneer Chilli", "170", "Nice"));
        list.add(new Recipe_model(R.drawable.pasta, "Pasta", "170", "Nice"));
        list.add(new Recipe_model(R.drawable.sandwich, "Sandwich", "170", "Nice"));
        list.add(new Recipe_model(R.drawable.dosa, "Dosa", "170", "Nice"));
        list.add(new Recipe_model(R.drawable.noodles, "Hakka Noodles", "170", "Nice"));

        RecipeAdapter adapter= new RecipeAdapter(list,this);
        binding.recycleView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recycleView.setLayoutManager(layoutManager);

        binding.ViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Order_Activity.class);
                startActivity(intent);
            }
        });
        binding.lastOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Last_Order.class);
                startActivity(intent);
            }
        });

    }
}