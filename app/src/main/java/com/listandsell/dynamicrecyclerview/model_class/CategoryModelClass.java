package com.listandsell.dynamicrecyclerview.model_class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryModelClass {


    @SerializedName("categories")
    @Expose
    private List<Categories> categories =null;

    public List<Categories> getCategories() {
        return categories;
    }

}
