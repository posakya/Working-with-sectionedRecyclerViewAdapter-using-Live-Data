package com.listandsell.dynamicrecyclerview.classes;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.listandsell.dynamicrecyclerview.R;
import com.listandsell.dynamicrecyclerview.model_class.Categories;
import com.listandsell.dynamicrecyclerview.model_class.CategoryModelClass;
import com.listandsell.dynamicrecyclerview.model_class.IndividualProductModelClass;
import com.listandsell.dynamicrecyclerview.model_class.Products;
import com.listandsell.dynamicrecyclerview.retrofit_api_client.RetrofitClient;
import com.listandsell.dynamicrecyclerview.retrofit_api_interface.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    RecyclerView recyclerView;
    SwipeRefreshLayout swipe_to_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        recyclerView = findViewById(R.id.recyclerView);

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

               getHeader();
               //getItem("Fast Food");
            }
        });

        swipe_to_refresh.setRefreshing(true);

    }

    public void getHeader(){
        ApiInterface headerInterface = RetrofitClient.getFormData().create(ApiInterface.class);

        Call<CategoryModelClass> categoryModelClassCall = headerInterface.getCategoryData();

        categoryModelClassCall.enqueue(new Callback<CategoryModelClass>() {
            @Override
            public void onResponse(Call<CategoryModelClass> call, Response<CategoryModelClass> response) {

                List<Categories> categories = response.body().getCategories();

                System.out.println("Response : "+categories);

                HeaderAdapter headerAdapter = new HeaderAdapter(getApplicationContext(),categories);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(headerAdapter);
                headerAdapter.notifyDataSetChanged();

                swipe_to_refresh.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<CategoryModelClass> call, Throwable t) {

            }
        });
    }


    @Override
    public void onRefresh() {
        getHeader();
      //  getItem("Fast Food");
    }
}
