package com.juniter.http;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.juniter.model.HttpExample;
import com.juniter.model.Sample;

public class HttpClientTest {

	@Test
	public void testGet() throws UnsupportedEncodingException{
		Map<String,String> map = new HashMap<>();
		map.put("uid", "0");
		//map.put("operator", "admin");
		HttpClient client = new HttpClient();
		String response = client.setUrl("http://localhost:8088/test/example/post").setRequestHeader(new Header()).post(new HttpExample().setName("单元测试").setPassword("Unit Test"));
		System.out.println(response);
		
		/*Map<String,List<Sample>> map$ = new HashMap<>();
		
		List<Sample> list = new ArrayList<>();
		list.add(new Sample().setId(100).setRange(100));
		list.add(new Sample().setId(101).setRange(101));
		map$.put("range", list);
		
		String response$ = client.setUrl("http://localhost:8080/api/product/sort").setRequestHeader(new Header()).post(map$);
		System.out.println(response$);*/
	}

}
