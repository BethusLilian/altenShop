package com.testAlten.produit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.testAlten.produit.Utils;
import com.testAlten.produit.models.Product;
import com.testAlten.produit.repositories.ProductRepository;
import com.testAlten.produit.services.ProductService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void addProduct() throws Exception {
        Product newProduct = Utils.generateRequestProduct();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonProduct = ow.writeValueAsString(newProduct);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProduct))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.code").value(newProduct.getCode()))
                .andExpect(jsonPath("$.price").value(newProduct.getPrice()));
    }


    @Test
    void getAllProduct() throws Exception {
        Product newProduct = Utils.generateRequestProduct();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonProduct = ow.writeValueAsString(newProduct);

        //ajout d'un premier item
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonProduct));

        mockMvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").exists()
                );
    }

    @Test
    void getOneProduct() throws Exception {
        Product newProduct = Utils.generateRequestProduct();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonProduct = ow.writeValueAsString(newProduct);

        //ajout d'un premier item
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonProduct));

        mockMvc.perform(get("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists()
                );
    }

    @Test
    void getOneProductNotFound() throws Exception {
        mockMvc.perform(get("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void patchProduct() throws Exception {
        // Préparer un produit dans la base
        Product product = new Product();
        product.setCode("P001");
        product.setName("Produit Original");
        product.setPrice(100.0);
        product.setInventoryStatus(Product.InventoryStatus.LOWSTOCK);
        product = productRepository.save(product);

        // Mise à jour partielle
        String partialUpdateJson = "{\"price\": 150.0, \"inventoryStatus\": \"INSTOCK\"}";

        // Appeler la méthode PATCH
        mockMvc.perform(patch("/api/products/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(partialUpdateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(150.0))
                .andExpect(jsonPath("$.inventoryStatus").value("INSTOCK"));
    }

    @Test
    void deleteProduct() throws Exception {
        Product newProduct = Utils.generateRequestProduct();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonProduct = ow.writeValueAsString(newProduct);

        //ajout d'un premier item
        String response = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProduct))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extraire l'ID du produit créé
        ObjectMapper mapper = new ObjectMapper();
        Product createdProduct = mapper.readValue(response, Product.class);
        Long productId = createdProduct.getId();

        // Vérifier que le produit existe avant suppression
        mockMvc.perform(get("/api/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/products/"+ productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/products/"+ productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}