package com.juniter.http;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class HttpClientTest {

	@Test
	public void testGet(){
		Map<String,String> map = new HashMap<>();
		map.put("name", "Juniter");
		map.put("password", "Juniter sPa ssword");
		HttpClient client = new HttpClient();
		String response = client.setUrl("http://localhost:8088/test/example").setRequestHeader(new Header()).get(map);
		System.out.println(response);
	}

}
