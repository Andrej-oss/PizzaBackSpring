package com.pizza_shop.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentIntentDto {
    public enum Currency{
        eur, usd
    }
    private String description;
    private int amount;
    private Currency currency;
}
