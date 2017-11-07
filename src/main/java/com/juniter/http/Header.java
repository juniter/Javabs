package com.juniter.http;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * Http Header
 * 
 * @author Juniter
 *
 */
@Component
public class Header {
	private String accept = "*/*";
	private String acceptEncoding = "gzip,deflate,br";
	private String connection = "keep-alive";
	private String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36";

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public String getAcceptEncoding() {
		return acceptEncoding;
	}

	public void setAcceptEncoding(String acceptEncoding) {
		this.acceptEncoding = acceptEncoding;
	}

	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public Map<String, String> toMap() {
		Map<String, String> map = new HashMap<>();
		map.put("Accept", this.accept);
		map.put("Accept-Encoding", this.acceptEncoding);
		map.put("User-Agent", this.acceptEncoding);
		map.put("Connection", this.userAgent);
		return map;
	}

}
