package com.pizza_shop.project;

import com.pizza_shop.project.config.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({StorageConfig.class,
        StoragePizzaConfig.class,
        StoragePizzaSizeConfig.class,
        StorageDrinkConfig.class,
        StorageUserConfig.class,
        StoragePromoConfig.class,
        StorageSnackConfig.class})
public class ProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

}
