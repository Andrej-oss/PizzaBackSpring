package com.pizza_shop.project.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "storage.pizza.size")
@Getter
@Setter
public class StoragePizzaSizeConfig {
    private String location;
}
