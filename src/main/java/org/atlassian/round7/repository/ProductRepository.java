package org.atlassian.round7.repository;

import org.atlassian.round7.model.Product;

import java.util.Optional;

public interface ProductRepository {
    void save(Product product);

    Optional<Product> getByName(String productName);
}
