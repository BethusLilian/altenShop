package com.testAlten.produit;

import com.testAlten.produit.models.Product;

public class Utils {

    public static Product generateRequestProduct() {
        Product requestProduct = new Product();
        requestProduct.setCode("P001");
        requestProduct.setName("Sample Product");
        requestProduct.setDescription("This is a sample product for testing purposes.");
        requestProduct.setImage("sample-product.jpg");
        requestProduct.setCategory("Electronics");
        requestProduct.setPrice(99.99);
        requestProduct.setQuantity(50);
        requestProduct.setInternalReference("REF001");
        requestProduct.setShellId(100L);
        requestProduct.setInventoryStatus(Product.InventoryStatus.INSTOCK);
        requestProduct.setRating(4);
        requestProduct.setCreatedAt(System.currentTimeMillis() - 86400000L); // créé il y a 1 jour
        requestProduct.setUpdatedAt(System.currentTimeMillis()); // mis à jour à la date actuelle
        return requestProduct;
    }

    public static Product generateSavedProduct(){
        Product savedProduct = generateRequestProduct();
        savedProduct.setId(0);
        return savedProduct;
    }

}
