package com.mamadou.campusgallery.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties(@Value("${DATABASE_URL:}") String databaseUrl) {
        DataSourceProperties properties = new DataSourceProperties();
        
        if (databaseUrl != null && !databaseUrl.isEmpty()) {
            // Convert postgresql:// to jdbc:postgresql:// if needed
            if (databaseUrl.startsWith("postgresql://")) {
                databaseUrl = databaseUrl.replace("postgresql://", "jdbc:postgresql://");
            }
            properties.setUrl(databaseUrl);
        }
        
        return properties;
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource dataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }
}
