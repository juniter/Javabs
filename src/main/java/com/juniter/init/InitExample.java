package com.juniter.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.juniter.conf.DefaultConfiguration;

@Component
public class InitExample implements CommandLineRunner{
	private static final Logger logger = LoggerFactory.getLogger(InitExample.class);
	private DefaultConfiguration config;
	@Override
	public void run(String... arg0) throws Exception {
		logger.info("Hello ~ World!Email:{}",config.getEmail());
	}
	
	@Autowired
	public void setConfig(DefaultConfiguration config) {
		this.config = config;
	}
	
}
