package com.inventory.inventory_management.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponseDTO implements Serializable {

    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
}