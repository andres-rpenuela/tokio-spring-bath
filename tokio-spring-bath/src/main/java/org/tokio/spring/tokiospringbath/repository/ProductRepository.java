package org.tokio.spring.tokiospringbath.repository;

import org.tokio.spring.tokiospringbath.domain.Product;

public interface ProductRepository {
    int countProducts();
    Product addProduct(Product product);
}
