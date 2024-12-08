package org.tokio.spring.tokiospringbath.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Product {

    private long id;
    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private BigDecimal discount;
    private BigDecimal taxes;
    private boolean visible;
    private int stock;
    private String image;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;

}
