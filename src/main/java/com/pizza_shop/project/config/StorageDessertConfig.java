package com.pizza_shop.project.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "storage.dessert")
@Getter
@Setter
public class StorageDessertConfig {
    private String location;
}
