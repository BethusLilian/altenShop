package com.testAlten.produit.services;

import com.testAlten.produit.Utils;
import com.testAlten.produit.models.Product;
import com.testAlten.produit.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductServiceImplTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;

    @Test
    void addProduct() {
        //Given
        Product newProduct = Utils.generateRequestProduct();

        //When
        Product savedProduct = productService.addProduct(newProduct);

        //Then
        assertNotNull(savedProduct.getId());
        assertEquals("P001", savedProduct.getCode());

        Optional<Product> productFromDb = productRepository.findById(savedProduct.getId());
        assertTrue(productFromDb.isPresent());
    }
}