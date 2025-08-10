package com.mamadou.campusgallery.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DataSourceConfig {

    @Value("${DATABASE_URL:}")
    private String databaseUrl;

    @Bean
    @Primary
    public DataSource dataSource() {
        if (databaseUrl == null || databaseUrl.isEmpty()) {
            // Fallback to default local configuration if DATABASE_URL is not provided
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:postgresql://localhost:5432/campus_gallery");
            config.setDriverClassName("org.postgresql.Driver");
            return new HikariDataSource(config);
        }

        try {
            URI dbUri = new URI(databaseUrl);
            
            // Convert postgresql:// to jdbc:postgresql://
            String jdbcUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(jdbcUrl);
            config.setUsername(username);
            config.setPassword(password);
            config.setDriverClassName("org.postgresql.Driver");
            
            // Add some Hikari optimizations
            config.setMaximumPoolSize(20);
            config.setMinimumIdle(5);
            config.setConnectionTimeout(30000);
            config.setIdleTimeout(600000);
            config.setMaxLifetime(1800000);
            
            return new HikariDataSource(config);
                    
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Invalid DATABASE_URL format: " + databaseUrl, e);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create DataSource from DATABASE_URL: " + databaseUrl, e);
        }
    }
}
