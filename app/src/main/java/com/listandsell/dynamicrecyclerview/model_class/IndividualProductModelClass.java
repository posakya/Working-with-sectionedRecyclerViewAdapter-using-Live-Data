package com.listandsell.dynamicrecyclerview.model_class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IndividualProductModelClass {

    private String message;

    private String category;

    private String category_id;

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    @SerializedName("products")
    @Expose
    private List<Products> products;

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getCategory ()
    {
        return category;
    }

    public void setCategory (String category)
    {
        this.category = category;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+",category_id="+category_id+", category = "+category+", products = "+products+"]";
    }
}
