package org.tokio.spring.tokiospringbath.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tokio.spring.tokiospringbath.domain.Product;
import org.tokio.spring.tokiospringbath.repository.ProductRepository;
import org.tokio.spring.tokiospringbath.service.ProductService;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        return productRepository.addProduct(product);
    }

    @Override
    public int countProducts() {
        System.out.println(productRepository.countProducts());
        return 0;
    }
}
