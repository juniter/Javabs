package com.juniter.javabs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Hello world!
 * 
 * Juniter
 */

@SpringBootApplication
@EnableConfigurationProperties(DefaultConfiguration.class)
public class Application implements CommandLineRunner{
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		System.out.println("Hello World!");
		logger.info("输出信息：{},Email:{}","Hello World");		
	}
}
