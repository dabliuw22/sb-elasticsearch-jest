
package com.leysoft.service.imple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.leysoft.document.Product;
import com.leysoft.repository.inter.ProductRepository;
import com.leysoft.service.inter.ProductService;

@Service
public class ProductServiceImp implements ProductService {

    @Value(
            value = "${elasticsearch.scroll.size}")
    private Long scrollSize;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findByStoreId(String storeId) {
        return productRepository.findAllByStoreId(storeId);
    }

    @Override
    public List<Product> findByStoreIdScroll(String storeId) {
        return productRepository.findAllByStoreIdScroll(storeId, scrollSize);
    }

    @Override
    public List<Product> findByName(String name, String... fields) {
        return productRepository.findByName(name, fields);
    }

    @Override
    public List<Product> findByNameAndLtePrice(String name, Double price, int from, int size) {
        return productRepository.findByNameAndLtePrice(name, price, from, size);
    }
}
