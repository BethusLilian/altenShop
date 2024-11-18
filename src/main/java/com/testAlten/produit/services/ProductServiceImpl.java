package com.testAlten.produit.services;

import com.testAlten.produit.exeptions.ProductNotFoundException;
import com.testAlten.produit.models.Product;
import com.testAlten.produit.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product addProduct(Product newProduct) {
        return productRepository.save(newProduct);
    }

    @Override
    public Iterable<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produit avec id=" + id + " non trouvé"));
    }

    @Override
    public Product updateProduct(Long id, Map<String, Object> updates) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produit avec id=" + id + " non trouvé"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "code":
                    product.setCode((String) value);
                    break;
                case "name":
                    product.setName((String) value);
                    break;
                case "description":
                    product.setDescription((String) value);
                    break;
                case "image":
                    product.setImage((String) value);
                    break;
                case "category":
                    product.setCategory((String) value);
                    break;
                case "price":
                    product.setPrice(((Number) value).doubleValue());
                    break;
                case "quantity":
                    product.setQuantity(((Number) value).intValue());
                    break;
                case "internalReference":
                    product.setInternalReference((String) value);
                    break;
                case "shellId":
                    product.setShellId(((Number) value).longValue());
                    break;
                case "inventoryStatus":
                    product.setInventoryStatus(Product.InventoryStatus.valueOf((String) value));
                    break;
                case "rating":
                    product.setRating(((Number) value).intValue());
                    break;
                case "createdAt":
                    product.setCreatedAt(((Number) value).longValue());
                    break;
                case "updatedAt":
                    product.setUpdatedAt(((Number) value).longValue());
                    break;
                default:
                    throw new IllegalArgumentException("Champ non reconnu : " + key);
            }
        });

        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
