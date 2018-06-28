package com.listandsell.dynamicrecyclerview.classes;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.listandsell.dynamicrecyclerview.MainActivity;
import com.listandsell.dynamicrecyclerview.R;
import com.listandsell.dynamicrecyclerview.model_class.IndividualProductModelClass;
import com.listandsell.dynamicrecyclerview.model_class.Products;
import com.listandsell.dynamicrecyclerview.retrofit_api_client.RetrofitClient;
import com.listandsell.dynamicrecyclerview.retrofit_api_interface.ApiInterface;
import com.listandsell.dynamicrecyclerview.sectioned_recylerview_adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerItemClass {

    Context context;
    private SectionedRecyclerViewAdapter sectionAdapter;

    public RecyclerItemClass(Context context, SectionedRecyclerViewAdapter sectionAdapter) {
        this.context = context;
        this.sectionAdapter = sectionAdapter;
    }

    public List<Products> getIndividualProduct(final String category){

        final List<Products> individualProductList = new ArrayList<>();

        ApiInterface productInterface = RetrofitClient.getFormData().create(ApiInterface.class);

        Call<IndividualProductModelClass> productModelClassCall = productInterface.getIndividualProduct(category);

        productModelClassCall.enqueue(new Callback<IndividualProductModelClass>() {
            @Override
            public void onResponse(Call<IndividualProductModelClass> call, Response<IndividualProductModelClass> response) {


                List<Products> IndViaductsList1 = response.body().getProducts();

                for (Products products : IndViaductsList1){
                    if (products.getCategory().equals(category)){
                        individualProductList.add(products);
                    }
                }

                sectionAdapter.addSection(new RecyclerViewAdapter(context,category,IndViaductsList1));

            }

            @Override
            public void onFailure(Call<IndividualProductModelClass> call, Throwable t) {
                System.out.println("Error1 : "+t.getMessage());
            }
        });

        return individualProductList;
    }
}
