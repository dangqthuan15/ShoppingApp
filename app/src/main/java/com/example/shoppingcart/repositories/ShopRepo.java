package com.example.shoppingcart.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shoppingcart.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopRepo {

    private MutableLiveData<List<Product>> mutableProductList;

    public LiveData<List<Product>> getProducts() {
        if (mutableProductList == null) {
            mutableProductList = new MutableLiveData<>();
            loadProducts();
        }
        return mutableProductList;
    }

    private void loadProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(UUID.randomUUID().toString(), "Áo Fear Of God", 1500000, true,"https://sneakerdaily.vn/wp-content/uploads/2021/11/ao-thun-fear-of-god-essentials-t-shirt-ss21-light-heather-oatmeal-1.png"));
        productList.add(new Product(UUID.randomUUID().toString(), "Áo Fear Of God", 1500000, true,"https://sneakerdaily.vn/wp-content/uploads/2021/11/ao-thun-fear-of-god-essentials-t-shirt-ss21-light-heather-oatmeal-1.png"));
        productList.add(new Product(UUID.randomUUID().toString(), "Áo Fear Of God", 1500000, true,"https://sneakerdaily.vn/wp-content/uploads/2021/11/ao-thun-fear-of-god-essentials-t-shirt-ss21-light-heather-oatmeal-1.png"));
        productList.add(new Product(UUID.randomUUID().toString(), "Áo Fear Of God", 1500000, true,"https://sneakerdaily.vn/wp-content/uploads/2021/11/ao-thun-fear-of-god-essentials-t-shirt-ss21-light-heather-oatmeal-1.png"));
        productList.add(new Product(UUID.randomUUID().toString(), "Áo Fear Of God", 1500000, true,"https://sneakerdaily.vn/wp-content/uploads/2021/11/ao-thun-fear-of-god-essentials-t-shirt-ss21-light-heather-oatmeal-1.png"));
        productList.add(new Product(UUID.randomUUID().toString(), "Áo Fear Of God", 1500000, true,"https://sneakerdaily.vn/wp-content/uploads/2021/11/ao-thun-fear-of-god-essentials-t-shirt-ss21-light-heather-oatmeal-1.png"));
        productList.add(new Product(UUID.randomUUID().toString(), "Áo Fear Of God", 1500000, true,"https://sneakerdaily.vn/wp-content/uploads/2021/11/ao-thun-fear-of-god-essentials-t-shirt-ss21-light-heather-oatmeal-1.png"));
        productList.add(new Product(UUID.randomUUID().toString(), "Áo Fear Of God", 1500000, true,"https://sneakerdaily.vn/wp-content/uploads/2021/11/ao-thun-fear-of-god-essentials-t-shirt-ss21-light-heather-oatmeal-1.png"));
        productList.add(new Product(UUID.randomUUID().toString(), "Áo Fear Of God", 1500000, true,"https://sneakerdaily.vn/wp-content/uploads/2021/11/ao-thun-fear-of-god-essentials-t-shirt-ss21-light-heather-oatmeal-1.png"));

        mutableProductList.setValue(productList);
    }
}
