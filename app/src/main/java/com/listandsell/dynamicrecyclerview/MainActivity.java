package com.listandsell.dynamicrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private SectionedRecyclerViewAdapter sectionAdapter;

    List<Products> individualProductList = new ArrayList<>();
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //// call SectionedRecyclerViewAdapter class

        sectionAdapter = new SectionedRecyclerViewAdapter();

        //// call getData method to parse the json data
        getData();

    }



    ///////// parsing category from server //////////////////

    private void getData(){
        ApiInterface categoryInterface = RetrofitClient.getFormData().create(ApiInterface.class);
        Call<CategoryModelClass> categoryModelClassCall = categoryInterface.getCategoryData();

        categoryModelClassCall.enqueue(new Callback<CategoryModelClass>() {
            @Override
            public void onResponse(Call<CategoryModelClass> call, Response<CategoryModelClass> response) {

                List<Categories> categories = response.body().getCategories();

                System.out.println("Category : "+categories);

                for (Categories categories1 : categories){

                    ////// passing category name to getIndividualProduct method ///////
                    getIndividualProduct(categories1.getCategory());

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
        ApiInterface productInterface = RetrofitClient.getFormData().create(ApiInterface.class);
        Call<IndividualProductModelClass> productModelClassCall = productInterface.getIndividualProduct(category);

        productModelClassCall.enqueue(new Callback<IndividualProductModelClass>() {
            @Override
            public void onResponse(Call<IndividualProductModelClass> call, Response<IndividualProductModelClass> response) {

                List<Products> IndViaductsList1 = response.body().getProducts();

                for (Products products : IndViaductsList1){
                    if (products.getCategory().equals(category)){

                        individualProductList.add(products);

                        title = products.getCategory();

                    }

                }

                //// checking the category name

                if (response.body().getCategory().equals(category)){
                    sectionAdapter.addSection(new RecyclerViewAdapter(getApplicationContext(),title,IndViaductsList1));
                }


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



                ///////// horizontal scroll view method //////////
              //  LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
//                recyclerView.setLayoutManager(horizontalLayoutManager);

            //   horizontalLayoutManager.canScrollHorizontally();


               // recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));


                ////// setting section adapter in recyclerview adapter
                recyclerView.setAdapter(sectionAdapter);

            }

            @Override
            public void onFailure(Call<IndividualProductModelClass> call, Throwable t) {
                System.out.println("Error1 : "+t.getMessage());
            }
        });

        return individualProductList;
    }


}
