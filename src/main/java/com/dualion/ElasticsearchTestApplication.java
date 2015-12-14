package com.dualion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories("com.dualion.repository")
@EnableTransactionManagement
@EnableElasticsearchRepositories("com.dualion.repository.search")
public class ElasticsearchTestApplication {

	private static final Logger log = LoggerFactory.getLogger(ElasticsearchTestApplication.class);
	
	public static void main(String[] args) {
        SpringApplication.run(ElasticsearchTestApplication.class, args);
    }
	
}
