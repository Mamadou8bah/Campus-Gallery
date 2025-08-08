package com.mamadou.campusgallery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CampusGalleryApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusGalleryApplication.class, args);
    }

}
