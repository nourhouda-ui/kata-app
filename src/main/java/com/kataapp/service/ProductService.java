package com.kataapp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kataapp.dao.Product;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static final String FILE_PATH = "products.json";
    private List<Product> products;
    private ObjectMapper objectMapper = new ObjectMapper();

    public ProductService() {
        loadProducts();
    }

    private void loadProducts() {
        try {
            products = objectMapper.readValue(new File(FILE_PATH), new TypeReference<List<Product>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveProducts() {
        try {
            objectMapper.writeValue(new File(FILE_PATH), products);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Product createProduct(Product product) {
        products.add(product);
        saveProducts();
        return product;
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProductById(Long id) {
        return products.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Optional<Product> existingProduct = products.stream().filter(p -> p.getId().equals(id)).findFirst();
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            // Update product fields
            product.setCode(updatedProduct.getCode());
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setImage(updatedProduct.getImage());
            product.setCategory(updatedProduct.getCategory());
            product.setPrice(updatedProduct.getPrice());
            product.setQuantity(updatedProduct.getQuantity());
            product.setInternalReference(updatedProduct.getInternalReference());
            product.setShellId(updatedProduct.getShellId());
            product.setInventoryStatus(updatedProduct.getInventoryStatus());
            product.setRating(updatedProduct.getRating());
            product.setUpdatedAt(System.currentTimeMillis());
            saveProducts();
            return product;
        }
        return null;
    }

    public void deleteProduct(Long id) {
        products.removeIf(p -> p.getId().equals(id));
        saveProducts();
    }
}
