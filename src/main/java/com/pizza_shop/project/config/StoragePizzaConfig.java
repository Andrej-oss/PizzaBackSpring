package com.pizza_shop.project.config;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "storage.pizza")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoragePizzaConfig {
    private String location;
}
