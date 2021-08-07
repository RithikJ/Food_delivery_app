package com.example.hello.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hello.Models.Cart;
import com.example.hello.Models.OrderModel;
import com.example.hello.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OrderAdapter extends FirebaseRecyclerAdapter<OrderModel,OrderAdapter.viewHolder> {

    Context context;
    FirebaseAuth auth;
    String userId;
    public OrderAdapter(@NonNull  FirebaseRecyclerOptions<OrderModel> options) {
        super(options);

    }

    @NonNull
    @Override
    public OrderAdapter.viewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_sample,parent,false);
        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        return new viewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderAdapter.viewHolder holder, int position, @NonNull  OrderModel model) {
        holder.productName.setText(model.getProductName());
        holder.price.setText(model.getPrice());
        holder.quantity.setText(model.getQuantity());
    }







    public class viewHolder extends RecyclerView.ViewHolder {

        TextView productName,price,quantity;
        public viewHolder(@NonNull  View itemView) {
            super(itemView);

            productName = (TextView) itemView.findViewById(R.id.order_name);
            price = (TextView) itemView.findViewById(R.id.price_order);

            quantity = (TextView) itemView.findViewById(R.id.total);

            itemView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference prod = FirebaseDatabase.getInstance().getReference().child(userId).child("Cart List").child(productName.getText().toString());
                    prod.removeValue();
                }
            });
        }
    }



}
