package com.listandsell.dynamicrecyclerview.model_class;

public class Products
{
    private String id;

    private String category;

    private String updated_at;

    private String tax;

    private String product_sell;

    private String product_price;

    private String product_code;

    private String product_name;

    private String created_at;

    private String category_id;

    private String product_image;

    private String product_desc;

    private String product_stock;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getCategory ()
    {
        return category;
    }

    public void setCategory (String category)
    {
        this.category = category;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public void setUpdated_at (String updated_at)
    {
        this.updated_at = updated_at;
    }

    public String getTax ()
    {
        return tax;
    }

    public void setTax (String tax)
    {
        this.tax = tax;
    }

    public String getProduct_sell ()
    {
        return product_sell;
    }

    public void setProduct_sell (String product_sell)
    {
        this.product_sell = product_sell;
    }

    public String getProduct_price ()
    {
        return product_price;
    }

    public void setProduct_price (String product_price)
    {
        this.product_price = product_price;
    }

    public String getProduct_code ()
    {
        return product_code;
    }

    public void setProduct_code (String product_code)
    {
        this.product_code = product_code;
    }

    public String getProduct_name ()
    {
        return product_name;
    }

    public void setProduct_name (String product_name)
    {
        this.product_name = product_name;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String getCategory_id ()
    {
        return category_id;
    }

    public void setCategory_id (String category_id)
    {
        this.category_id = category_id;
    }

    public String getProduct_image ()
    {
        return product_image;
    }

    public void setProduct_image (String product_image)
    {
        this.product_image = product_image;
    }

    public String getProduct_desc ()
    {
        return product_desc;
    }

    public void setProduct_desc (String product_desc)
    {
        this.product_desc = product_desc;
    }

    public String getProduct_stock ()
    {
        return product_stock;
    }

    public void setProduct_stock (String product_stock)
    {
        this.product_stock = product_stock;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", category = "+category+", updated_at = "+updated_at+", tax = "+tax+", product_sell = "+product_sell+", product_price = "+product_price+", product_code = "+product_code+", product_name = "+product_name+", created_at = "+created_at+", category_id = "+category_id+", product_image = "+product_image+", product_desc = "+product_desc+", product_stock = "+product_stock+"]";
    }
}
