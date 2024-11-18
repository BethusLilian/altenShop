package com.testAlten.produit.controllers;

import com.testAlten.produit.exeptions.ProductNotFoundException;
import com.testAlten.produit.models.Product;
import com.testAlten.produit.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> post(@RequestBody Product newProduct) {
        Product addedProduct = productService.addProduct(newProduct);
        log.info("produit ajouté avec identifiant : {}", addedProduct.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
    }


    @GetMapping
    public ResponseEntity<Iterable<Product>> getAll() {
        Iterable<Product> allProduct = productService.getAllProduct();
        return ResponseEntity.status(HttpStatus.OK).body(allProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        try {
            Product updatedProduct = productService.updateProduct(id, updates);
            return ResponseEntity.ok(updatedProduct);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
