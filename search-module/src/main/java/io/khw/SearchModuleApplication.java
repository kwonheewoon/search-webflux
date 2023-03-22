package io.khw;

import io.khw.common.config.PropertiesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"io.khw.domain", "io.khw.common", "io.khw.search"})
@EnableJpaRepositories(basePackages = {"io.khw.domain"})
@EntityScan(basePackages = {"io.khw.domain"})
@ConfigurationPropertiesScan(basePackages = "io.khw.common")
public class SearchModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchModuleApplication.class, args);
    }

}
