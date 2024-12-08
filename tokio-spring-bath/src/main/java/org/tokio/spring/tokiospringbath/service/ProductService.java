package org.tokio.spring.tokiospringbath.service;

import org.tokio.spring.tokiospringbath.domain.Product;

public interface ProductService {
    Product save(Product product);
    int countProducts();
}
