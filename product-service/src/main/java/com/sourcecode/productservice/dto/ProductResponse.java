package com.sourcecode.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// for this class, it is the parameters that will be return when we want to get all product
public class ProductResponse {

    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
