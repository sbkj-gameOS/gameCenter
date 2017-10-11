package com.bradypod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import com.bradypod.config.web.StartedEventListener;
import com.bradypod.core.BMDataContext;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableAutoConfiguration
@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableJpaRepositories("com.bradypod.web.service.repository.jpa")
@EnableElasticsearchRepositories("com.bradypod.web.service.repository.es")
public class Application {
    
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(Application.class) ;
		springApplication.addListeners(new StartedEventListener());
		BMDataContext.setApplicationContext(springApplication.run(args));
	}
	
}
