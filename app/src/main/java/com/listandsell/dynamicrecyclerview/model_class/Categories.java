package com.listandsell.dynamicrecyclerview.model_class;

public class Categories
{
    private String id;

    private String category;

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

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", category = "+category+"]";
    }
}
