package com.juniter.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.juniter.http.Header;
import com.juniter.http.HttpClient;
import com.juniter.model.HttpExample;

@Controller
@RequestMapping("/test")
public class HttpExampleController {
	private static final Logger logger = LoggerFactory.getLogger(HttpExampleController.class);
	@Autowired
	private HttpClient httpClient;
	
	@RequestMapping(value = "/example", method = RequestMethod.GET)
	public void exampleGet(HttpServletResponse response, @RequestParam(name = "name") String name, @RequestParam(name = "password") String password) throws IOException {
		logger.info("GET REQUEST: name: {}, password: {}",name,password);
		HttpExample example = new HttpExample();
		example.setName(name).setPassword(password).setPhone("177-8073-8463");
		response.getWriter().write(example.toString());
	}
	
	@RequestMapping(value = "/example/post", method = RequestMethod.POST)
	public void examplePost(HttpServletResponse response, @RequestBody HttpExample body) throws IOException {
		logger.info("POST REQUEST: {}",body.toString());
		response.getWriter().write("SERVER SIDE RECEIVED YOUR REQUEST!接收到来自服务端的信息返回");
	}
	
	@RequestMapping(value= "/send")
	public void sendHttp() throws MalformedURLException {
		Map<String,String> map = new HashMap<>();
		map.put("name", "heiheihei");
		map.put("password", "xxoo");
		String message = this.httpClient.setUrl("http://localhost:8088/test/example").setRequestHeader(new Header()).get(map);
		logger.info("REQUEST SIDE RESEIVED: {}",message);
		HttpExample example = new HttpExample();
		example.setName("POST NAME").setPassword("POST PASSWORD").setPhone("177-8073-8463");
		message = this.httpClient.setUrl("http://localhost:8088/test/example/post").setRequestHeader(new Header()).post(example);
	}
}
