package com.pizza_shop.project.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "storage")
@Getter
@Setter
public class StorageConfig {
    private String location;
}
