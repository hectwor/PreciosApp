package hect.preciosapp.Models;

import android.text.Editable;

/**
 * Created by hect on 17/02/18.
 */

public class Product {

    private String id;
    private String name;
    private String category;
    private String photourl;
    private String price;

    public Product(String name, String category, String photourl, String price) {
        this.name = name;
        this.category = category;
        this.photourl = photourl;
        this.price = price;
    }

    public Product() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
