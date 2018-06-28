package com.listandsell.dynamicrecyclerview.classes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.listandsell.dynamicrecyclerview.R;
import com.listandsell.dynamicrecyclerview.model_class.Products;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{

    Context context;
    List<Products> productsList;

    public ItemAdapter(Context context, List<Products> productsList) {
        this.context = context;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_ex1_item,parent,false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder holder, int position) {

        Products products = productsList.get(position);
        holder.txt_title.setText(products.getProduct_code());
        Glide.with(context).load(products.getProduct_image()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView txt_title;
        ImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.tvItem);
            imageView = itemView.findViewById(R.id.imgItem);
        }
    }
}
