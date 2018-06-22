package com.listandsell.dynamicrecyclerview.retrofit_api_interface;


import com.listandsell.dynamicrecyclerview.model_class.CategoryModelClass;
import com.listandsell.dynamicrecyclerview.model_class.IndividualProductModelClass;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by lokesh on 6/1/18.
 */

public interface ApiInterface {

    ///// get category list api interface
    @Headers({"x-api-order-key: OrDeRlY_crystal"})
    @GET("categoryList")
    Call<CategoryModelClass> getCategoryData();

    ///// get product list api interface
    @Headers({"x-api-order-key: OrDeRlY_crystal"})
    @GET("productList/{category}")
    Call<IndividualProductModelClass> getIndividualProduct(@Path("category") String category);

}
