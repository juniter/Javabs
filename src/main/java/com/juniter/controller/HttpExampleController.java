package com.juniter.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.juniter.model.HttpExample;

@Controller
@RequestMapping("/test")
public class HttpExampleController {
	private static final Logger logger = LoggerFactory.getLogger(HttpExampleController.class);
	
	@RequestMapping(value = "/example", method = RequestMethod.GET)
	public void example(HttpServletResponse response, @RequestParam(name = "name") String name, @RequestParam(name = "password") String password) throws IOException {
		logger.info("Request Parameters:name:{},password:{}",name,password);
		HttpExample example = new HttpExample();
		example.setName(name).setPassword(password).setPhone("177-8073-8463");
		response.getWriter().write(example.toString());
	}
}
