package com.pizza_shop.project.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "storage.user")
@Getter
@Setter
public class StorageUserConfig {
    private String location;
}
