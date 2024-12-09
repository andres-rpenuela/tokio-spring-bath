package org.tokio.spring.tokiospringbath.processor;

import org.springframework.batch.item.ItemProcessor;
import org.tokio.spring.tokiospringbath.domain.Product;

public class ProductItemProcessor implements ItemProcessor<Product, Product> {

    @Override
    public Product process(Product product) throws Exception {
        if (product.getStock() == 0 ) {
            return null;
        }

        product.setName(product.getName().toUpperCase());
        return product;
    }
}
