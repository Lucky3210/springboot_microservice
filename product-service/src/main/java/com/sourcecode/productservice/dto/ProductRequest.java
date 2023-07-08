package com.sourcecode.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// this class act as a body of the parameters to passed in as a request-body to the controller
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
}
