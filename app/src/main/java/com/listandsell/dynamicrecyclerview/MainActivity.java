package com.listandsell.dynamicrecyclerview;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.listandsell.dynamicrecyclerview.model_class.Categories;
import com.listandsell.dynamicrecyclerview.model_class.CategoryModelClass;
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

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private SectionedRecyclerViewAdapter sectionAdapter;

    String title;
    String category;
    SwipeRefreshLayout swipe_to_refresh;
    List<Products> individualProductList = new ArrayList<>();
    List<Categories> categories1 = new ArrayList<>();
    List<Categories> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //// call SectionedRecyclerViewAdapter class

        sectionAdapter = new SectionedRecyclerViewAdapter();


        swipe_to_refresh = findViewById(R.id.swipe_to_refresh);

        swipe_to_refresh.setOnRefreshListener(this);
        swipe_to_refresh.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        swipe_to_refresh.post(new Runnable() {

            @Override
            public void run() {

                swipe_to_refresh.setRefreshing(true);

                getData();
            }
        });

        swipe_to_refresh.setRefreshing(true);



    }



    ///////// parsing category from server //////////////////

    private void getData(){
        ApiInterface categoryInterface = RetrofitClient.getFormData().create(ApiInterface.class);
        Call<CategoryModelClass> categoryModelClassCall = categoryInterface.getCategoryData();

        categoryModelClassCall.enqueue(new Callback<CategoryModelClass>() {
            @Override
            public void onResponse(Call<CategoryModelClass> call, Response<CategoryModelClass> response) {


                categories = response.body().getCategories();

                System.out.println("Category : "+categories.size());

                for (Categories categories2 : categories){

                    System.out.println("CategoryName : "+categories2.getCategory());

                    getIndividualProduct(categories2.getCategory());


                }

            }


            @Override
            public void onFailure(Call<CategoryModelClass> call, Throwable t) {
                System.out.println("Error : "+t.getMessage());
            }
        });
    }


    /////// get Individual list from server /////////////

    public List<Products> getIndividualProduct(final String category){

      //  individualProductList = new ArrayList<>();

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


                sectionAdapter.addSection(new RecyclerViewAdapter(getApplicationContext(),category,IndViaductsList1));


                ///// recycler view defining
                RecyclerView recyclerView = findViewById(R.id.recyclerView);


                GridLayoutManager glm = new GridLayoutManager(MainActivity.this, 2);
                glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        switch (sectionAdapter.getSectionItemViewType(position)) {
                            case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                                return 2;
                            default:
                                return 1;
                        }
                    }
                });

                recyclerView.setLayoutManager(glm);
                recyclerView.setHasFixedSize(true);

                recyclerView.setAdapter(sectionAdapter);
                sectionAdapter.notifyDataSetChanged();

                swipe_to_refresh.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<IndividualProductModelClass> call, Throwable t) {
                System.out.println("Error1 : "+t.getMessage());
            }
        });

        return individualProductList;
    }


    @Override
    public void onRefresh() {

            categories1.clear();
            individualProductList.clear();
       getData();

    }
}
