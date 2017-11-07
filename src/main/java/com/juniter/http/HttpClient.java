package com.juniter.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClient implements HttpExecuter {

	private static final Logger logger = LoggerFactory.getLogger("HTTP_REQUEST_LOGGER");
	private URLConnection connection;
	private String url;
	private URL realURL;
	private Header requestHeader;

	@Override
	public String get(Map<String, String> map) throws MalformedURLException {
		logger.info("Request Method:{}", HttpMethod.GET);
		BufferedReader in = null;
		StringBuffer response = new StringBuffer();
		this.setRealURL(map);
		try {
			this.connection = this.realURL.openConnection();
			this.setHeader(this.requestHeader);
			this.connection.connect();
			this.logResponseHeader(connection.getHeaderFields());
			System.out.println("####################\nXXXX:{}"+this.realURL.toString());
			in = new BufferedReader(new InputStreamReader(this.connection.getInputStream(),"UTF-8"));
			String metedata;
			while ((metedata = in.readLine()) != null)
				response.append(metedata);
		} catch (IOException e) {
			logger.warn("An error has occured while sent GET request:{}", e.getMessage());
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e1) {
				logger.warn("Failed to close InputStream:{}", e1.getMessage());
			}
		}
		logger.info("Response:{}",response.toString());
		return response.toString();
	}

	@Override
	public <T> String post(T t) {
		return null;
	}

	@Override
	public <T> String delete(T t) {
		return null;
	}

	@Override
	public <T> String put(T t) {
		return null;
	}

	/**
	 * Set Http Request Header
	 * 
	 * @param header
	 * @return
	 * 
	 */
	private HttpClient setHeader(Header header) {
		Map<String, String> headerMap = header.toMap();
		Set<String> keySet = headerMap.keySet();
		keySet.forEach((key) -> {
			this.connection.setRequestProperty(key, headerMap.get(key));
		});
		logger.info("Accept:{}\nAccept-Encoding:{}\nUser-Agent:{}\nConnection:{}", header.getAccept(),
				header.getAcceptEncoding(), header.getUserAgent(), header.getConnection());
		return this;
	}

	/**
	 * Get Reqeust URL
	 * 
	 * @return
	 */
	public String getRequestURL() {
		return realURL == null ? "" : realURL.toExternalForm();
	}

	/**
	 * Set Real Request URL
	 * 对空格进行编码，对请求进行编码吗
	 * 
	 * @param url
	 * @throws MalformedURLException
	 * 
	 * 
	 */
	private void setRealURL(Map<String, String> params) throws MalformedURLException {
		if (params != null) {
			StringBuffer urlBuffer = new StringBuffer(url);
			urlBuffer.append("?");
			Set<String> keySet = params.keySet();
			keySet.forEach((key) -> urlBuffer.append(key).append("=%22").append(params.get(key)).append("%22&"));
			urlBuffer.append("dc_").append("=%22").append(System.currentTimeMillis()).append("%22");
			this.realURL = new URL(urlBuffer.toString());
			logger.info("Request URL:{}", urlBuffer.toString());
		} else {
			this.realURL = new URL(url);
			logger.info("Request URL:{}", url);
		}

	}

	private void logResponseHeader(Map<String, List<String>> map) {
		if(map!=null) {
			Set<String> keySet = map.keySet();
			keySet.forEach((rh) -> {
				if(rh==null)
					logger.info("{HTTP}:{}", map.get("null"));
				else
					logger.info("{}:{}", rh, map.get(rh.toString()));
			});
		}
	}

	/**
	 * Set Origin URL Without Parameters
	 * 
	 * @param url
	 */
	public HttpClient setUrl(String url) {
		this.url = url;
		return this;
	}

	/**
	 * Set Request Header
	 * 
	 * @param requestHeader
	 * @return
	 */
	public HttpClient setRequestHeader(Header requestHeader) {
		this.requestHeader = requestHeader;
		return this;
	}

}
