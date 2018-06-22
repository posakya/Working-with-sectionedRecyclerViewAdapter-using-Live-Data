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
                    sectionAdapter.addSection(new ContactSection(title,IndViaductsList1));
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

                ///////// horizontal scroll view method //////////
//                LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
//                recyclerView.setLayoutManager(horizontalLayoutManager);

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

    ///////// dynamic recycler view with headers
    private class ContactSection extends StatelessSection{

        String title;
        List<Products> list;

        ContactSection(String title, List<Products> list) {
            super(SectionParameters.builder()
                    .itemResourceId(R.layout.section_ex1_item)
                    .headerResourceId(R.layout.section_ex1_header)
                    .build());

            this.title = title;
            this.list = list;
        }

        @Override
        public int getContentItemsTotal() {
            return list.size();
        }


        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            //////// return a custom instance of ViewHolder for the items of this section

            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {


            // bind your view here
                final ItemViewHolder itemHolder = (ItemViewHolder) holder;

                Products products = list.get(position);

                itemHolder.tvItem.setText(products.getProduct_code());
                Glide.with(getApplicationContext()).load(products.getProduct_image().replaceAll("localhost:8000","192.168.2.1:81")).into(itemHolder.imgItem);

                itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, (sectionAdapter.getPositionInSection(itemHolder.getAdapterPosition())), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            //////// return a custom instance of ViewHolder for the header of this section
            return new HeaderViewHolder(view);
        }


        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {

            // bind your header view here
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;


            headerHolder.tvTitle.setText(title);
        }
    }

    ///// creating class for Items

    class ItemViewHolder extends RecyclerView.ViewHolder{

        private final View rootView;
        private final ImageView imgItem;
        private final TextView tvItem;

        ItemViewHolder(View view) {
            super(view);

            rootView = view;
            imgItem = (ImageView) view.findViewById(R.id.imgItem);
            tvItem = (TextView) view.findViewById(R.id.tvItem);
        }
    }

    ///// creating class for header
    class HeaderViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvTitle;

        HeaderViewHolder(View view) {
            super(view);

            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        }
    }
}
