
package com.leysoft.service.inter;

import java.util.List;

import com.leysoft.document.Product;

public interface ProductService {

    public List<Product> findByStoreId(String storeId);

    public List<Product> findByName(String name, String... fields);

    public List<Product> findByNameAndLtePrice(String name, Double price, int from, int size);
}
