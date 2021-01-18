package com.pizza_shop.project.dto;

import com.pizza_shop.project.entity.Purchase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchasePageDto {

    private List<Purchase> purchases;
    private long totalElements;
    private int size;
    private int totalPages;
    private int number;
}
