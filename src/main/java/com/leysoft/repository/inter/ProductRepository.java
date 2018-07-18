
package com.leysoft.repository.inter;

import java.util.List;

import com.leysoft.document.Product;

public interface ProductRepository {

    public List<Product> findByStoreId(String storeId);

    public List<Product> findByStoreId(Long storeId);

    public List<Product> findByName(String name, String... fields);

    public List<Product> findByNameAndLtePrice(String name, Double price, int from, int size);
}
