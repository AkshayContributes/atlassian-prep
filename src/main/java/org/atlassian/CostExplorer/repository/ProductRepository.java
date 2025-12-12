package org.atlassian.CostExplorer.repository;

import org.atlassian.CostExplorer.model.Product;

import java.util.Optional;

public interface ProductRepository {
    void save(Product product);

    Optional<Product> getByName(String productName);
}
