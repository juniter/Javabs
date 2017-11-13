package com.juniter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;

import com.juniter.conf.DefaultConfiguration;

@SpringBootApplication
@EnableConfigurationProperties(DefaultConfiguration.class)
@ServletComponentScan(basePackages= {"com.juniter.conf","com.juniter.filter"})
public class SpringBootEntry {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootEntry.class, args);
	}
}
