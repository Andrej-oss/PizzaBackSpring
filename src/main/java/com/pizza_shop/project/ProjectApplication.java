package com.pizza_shop.project;

import com.pizza_shop.project.config.StorageConfig;
import com.pizza_shop.project.config.StoragePizzaConfig;
import com.pizza_shop.project.config.StoragePizzaSizeConfig;
import com.pizza_shop.project.config.StorageUserConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({StorageConfig.class,
        StoragePizzaConfig.class,
        StoragePizzaSizeConfig.class, StorageUserConfig.class})
public class ProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

}
