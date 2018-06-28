package com.listandsell.dynamicrecyclerview.classes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.listandsell.dynamicrecyclerview.R;
import com.listandsell.dynamicrecyclerview.model_class.Categories;
import com.listandsell.dynamicrecyclerview.model_class.IndividualProductModelClass;
import com.listandsell.dynamicrecyclerview.model_class.Products;
import com.listandsell.dynamicrecyclerview.retrofit_api_client.RetrofitClient;
import com.listandsell.dynamicrecyclerview.retrofit_api_interface.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HeaderAdapter  extends RecyclerView.Adapter<HeaderAdapter.MyViewHolder>{

    Context context;
    List<Categories> categoriesList;

    public HeaderAdapter(Context context, List<Categories> categoriesList) {
        this.context = context;
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public HeaderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_ex1_header,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HeaderAdapter.MyViewHolder holder, int position) {
            Categories categories = categoriesList.get(position);
            holder.txt_header.setText(categories.getCategory());


        ApiInterface itemInterface = RetrofitClient.getFormData().create(ApiInterface.class);

        Call<IndividualProductModelClass> individualProductModelClassCall = itemInterface.getIndividualProduct(categories.getCategory());

        individualProductModelClassCall.enqueue(new Callback<IndividualProductModelClass>() {
            @Override
            public void onResponse(Call<IndividualProductModelClass> call, Response<IndividualProductModelClass> response) {

                List<Products> products = response.body().getProducts();

                // System.out.println("Response : "+categories);

                ItemAdapter itemAdapter = new ItemAdapter(context,products);

                LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                holder.recyclerView.setLayoutManager(horizontalLayoutManager);

//                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                //holder.recyclerView.setLayoutManager(layoutManager);
                holder.recyclerView.setAdapter(itemAdapter);
                itemAdapter.notifyDataSetChanged();

                //swipe_to_refresh.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<IndividualProductModelClass> call, Throwable t) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txt_header;
        RecyclerView recyclerView;



        public MyViewHolder(View itemView) {
            super(itemView);

            txt_header = itemView.findViewById(R.id.tvTitle);
            recyclerView = itemView.findViewById(R.id.recyclerView);


        }
    }

//    public void getItem(String category){
//
//    }

}
