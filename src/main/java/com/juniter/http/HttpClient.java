package com.juniter.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HttpClient implements HttpExecuter {

	private static final Logger logger = LoggerFactory.getLogger("HTTP_REQUEST_LOGGER");
	private URLConnection connection;
	private String url;
	private URL realURL;
	private Header requestHeader;

	/**
	 * @author Juniter Http Get Method
	 * @param parameters map
	 * 
	 */
	@Override
	public String get(Map<String, String> map) throws MalformedURLException {
		logger.info("Request Method:  {}", HttpMethod.GET);
		BufferedReader in = null;
		StringBuffer response = new StringBuffer();
		this.setGetMethodURL(map);
		try {
			this.connection = this.realURL.openConnection();
			this.setHeader(this.requestHeader);
			this.connection.connect();
			this.logResponseHeader(connection.getHeaderFields());
			in = new BufferedReader(new InputStreamReader(this.connection.getInputStream(), "UTF-8"));
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
		logger.info("The Server Response: {}", response.toString());
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
		logger.info("RequestHeader\nAccept:  {}\nAccept-Encoding:  {}\nUser-Agent:  {}\nConnection:  {}", header.getAccept(),
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
	 * Set Real Request URL 对空格进行编码，对请求进行编码吗
	 * 
	 * @param url
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 * 
	 * 
	 */
	private void setGetMethodURL(Map<String, String> params) throws MalformedURLException {
		if (params != null) {
			StringBuffer urlBuffer = new StringBuffer(url);
			urlBuffer.append("?");
			Set<String> keySet = params.keySet();
			keySet.forEach((key) -> {
				String encodeParam = this.getEncodeURLParam(params.get(key));
				urlBuffer.append(key).append("=").append(encodeParam).append("&");
			});
			this.setRandomParameter(urlBuffer);
			this.realURL = new URL(urlBuffer.toString());
		} else
			this.realURL = new URL(url);
		logger.info("Request URL:{}", this.realURL.toExternalForm());

	}

	/**
	 * Encode request parameters
	 * 
	 * @param param
	 * @return
	 * 
	 */
	private String getEncodeURLParam(String param) {
		StringBuffer temp = new StringBuffer("%22");
		try {
			String value = URLEncoder.encode(param, "UTF-8");
			// add %22 block
			temp.append(value).append("%22");
		} catch (UnsupportedEncodingException e) {
			logger.warn("Failed To Encode URL:{}", e.getMessage());
		}
		return temp.toString();
	}

	/**
	 * set random parameter to avoid http cache
	 * 
	 * @param buffer
	 */
	private void setRandomParameter(StringBuffer buffer) {
		buffer.append("dc_").append("=%22").append(System.nanoTime()).append("%22");
	}

	private void logResponseHeader(Map<String, List<String>> map) {
		if (map != null) {
			Set<String> keySet = map.keySet();
			keySet.forEach((rh) -> {
				if (rh == null)
					logger.info("HTTP:  {}", map.get(null));
				else
					logger.info("{}:  {}", rh, map.get(rh.toString()));
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
