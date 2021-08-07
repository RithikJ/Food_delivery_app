package com.example.hello.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hello.Detail_Activity;
import com.example.hello.MainActivity;
import com.example.hello.Models.Recipe_model;
import com.example.hello.Order_Activity;
import com.example.hello.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.viewHolder>{

    ArrayList<Recipe_model> list;
    Context context;

    public RecipeAdapter(ArrayList<Recipe_model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_recyclerview,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  RecipeAdapter.viewHolder holder, int position) {
        final Recipe_model model = list.get(position);
        holder.foodImage.setImageResource(model.getPic());
        holder.name.setText(model.getName());
        holder.price.setText(model.getPrice());
        holder.description.setText(model.getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage;
        TextView name;
        TextView price;
        TextView description;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage = itemView.findViewById(R.id.foodimage);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            description = itemView.findViewById(R.id.description);


            foodImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), Detail_Activity.class);
                    intent.putExtra("name",list.get(getAdapterPosition()).getName());
                    intent.putExtra("price",list.get(getAdapterPosition()).getPrice());
                    intent.putExtra("description",list.get(getAdapterPosition()).getDescription());
                    intent.putExtra("image",list.get(getAdapterPosition()).getPic());
                    context.startActivity(intent);
                }
            });
        }



    }
}


