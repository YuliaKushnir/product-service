package org.example.productservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductServiceApplication {

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load();
        System.setProperty("spring_datasource_username", dotenv.get("spring_datasource_username"));
        System.setProperty("spring_datasource_password", dotenv.get("spring_datasource_password"));
        System.setProperty("nebius", dotenv.get("nebius"));
        System.setProperty("cloudinary_cloud-name", dotenv.get("cloudinary_cloud-name"));
        System.setProperty("cloudinary_api-key", dotenv.get("cloudinary_api-key"));
        System.setProperty("cloudinary_api_secret", dotenv.get("cloudinary_api_secret"));

        SpringApplication.run(ProductServiceApplication.class, args);
    }

}
