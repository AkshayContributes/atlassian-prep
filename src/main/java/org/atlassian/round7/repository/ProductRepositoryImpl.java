package org.atlassian.round7.repository;

import org.atlassian.round7.model.Product;

import java.util.Map;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {
    private final Map<String, Product> products;

    public ProductRepositoryImpl(Map<String, Product> products) {
        this.products = products;
    }

    @Override
    public void save(Product product) {
        this.products.put(product.getName(), product);
    }

    @Override
    public Optional<Product> getByName(String productName) {
        return Optional.ofNullable(this.products.get(productName));
    }
}
