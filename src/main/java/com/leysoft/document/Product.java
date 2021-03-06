
package com.leysoft.document;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

    private String id;

    private String name;

    private Double price;

    @JsonProperty(
            value = "store-id")
    private Long storeId;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", price=" + price + ", storeId=" + storeId
                + "]";
    }
}
