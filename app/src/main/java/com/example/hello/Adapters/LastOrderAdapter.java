package com.example.hello.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hello.Models.LastOrderModel;
import com.example.hello.Models.OrderModel;
import com.example.hello.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LastOrderAdapter extends FirebaseRecyclerAdapter<LastOrderModel,LastOrderAdapter.viewHolder > {
    Context context;
    FirebaseAuth auth;
    String userId;
    public LastOrderAdapter(@NonNull FirebaseRecyclerOptions<LastOrderModel> options) {
        super(options);

    }

    @NonNull
    @Override
    public LastOrderAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_sample,parent,false);
        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        return new viewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull LastOrderAdapter.viewHolder holder, int position, @NonNull  LastOrderModel model) {
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


        }
    }
}
