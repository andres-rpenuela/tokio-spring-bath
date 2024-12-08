package org.tokio.spring.tokiospringbath;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.tokio.spring.tokiospringbath.domain.Product;
import org.tokio.spring.tokiospringbath.service.ProductService;

import java.math.BigDecimal;

@SpringBootApplication
public class TokioSpringBathApplication {


	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(TokioSpringBathApplication.class, args);
		ProductService productService = (ProductService) context.getBean("productServiceImpl");
		productService.countProducts();

		final Product product = Product.builder()
				.name("Coca-Cola")
				.description("description")
				.category("Drink")
				.price(BigDecimal.valueOf(3L))
				.discount(BigDecimal.valueOf(3L))
				.taxes(BigDecimal.valueOf(3L))
				.stock(10).build();
		productService.save(product);
		productService.countProducts();
	}

}
