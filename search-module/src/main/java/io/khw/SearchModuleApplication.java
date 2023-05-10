package io.khw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.blockhound.BlockHound;

@SpringBootApplication(scanBasePackages = {"io.khw.domain", "io.khw.common", "io.khw.search"})
@EnableR2dbcRepositories(basePackages = {"io.khw.domain.*.repository_r2dbc"})
@EntityScan(basePackages = {"io.khw.domain"})
@ConfigurationPropertiesScan(basePackages = "io.khw.common")
@EnableWebFlux
public class SearchModuleApplication {
    public static void main(String[] args) {
        BlockHound.install();
        SpringApplication.run(SearchModuleApplication.class, args);
    }

}
