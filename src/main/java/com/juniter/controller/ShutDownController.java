package com.juniter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShutDownController {
	private static final Logger logger =LoggerFactory.getLogger(ShutDownController.class);
	
	@RequestMapping("/shutdown")
	public void shutdown() {
		logger.info("关机--------------------------------");
		System.exit(10);
	}
}
