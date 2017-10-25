package com.juniter.javabs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger logger =LoggerFactory.getLogger(Application.class);
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		for (int i =0;i<2;i++) {
			logger.info("输出信息：{},Email:{}","Hello World");		
			logger.debug("输出信息：{},Email:{}","Hello World");
			logger.warn("输出信息：{},Email:{}","Hello World");
			logger.error("输出信息：{},Email:{}","Hello World");
		}
	}
}
