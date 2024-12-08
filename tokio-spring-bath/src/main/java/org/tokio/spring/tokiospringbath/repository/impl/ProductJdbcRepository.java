package org.tokio.spring.tokiospringbath.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.tokio.spring.tokiospringbath.domain.Product;
import org.tokio.spring.tokiospringbath.repository.ProductRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductJdbcRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public int countProducts() {
        log.info("countProducts");
        final String query = "SELECT COUNT(*) FROM product";
        return jdbcTemplate.queryForObject(query, Integer.class);
    }

    @Override
    public Product addProduct(Product product) {
        log.info("addProduct");
        final String queryScheme = "INSERT INTO product (name, description, category, price, discount, taxes, stock_quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";
        // int result = jdbcTemplate.update(queryScheme, product.getName(), product.getDescription(), product.getCategory(), product.getPrice(), product.getDiscount(), product.getTaxes(), product.getStock());
        // log.info("Product inserted: " + product.getName()+ ", result: " + result);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(queryScheme, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setString(3, product.getCategory());
            ps.setBigDecimal(4, product.getPrice());
            ps.setBigDecimal(5, product.getDiscount());
            ps.setBigDecimal(6, product.getTaxes());
            ps.setInt(7,product.getStock());
            return ps;
        }, keyHolder);
        product.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());

        return product;
    }
}
