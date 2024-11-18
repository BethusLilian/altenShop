package com.testAlten.produit.services;

import com.testAlten.produit.models.Product;

import java.util.Map;

public interface ProductService {

    Product addProduct(Product newProduct);

    Iterable<Product> getAllProduct();

    Product getProductById(Long id);

    Product updateProduct(Long id, Map<String, Object> updates);

    void deleteById(Long id);
}
