package com.juniter.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

import com.google.gson.Gson;

@Component
public class HttpClient implements HttpExecuter {

	private static final Logger logger = LoggerFactory.getLogger("HTTP_REQUEST_LOGGER");
	private URLConnection connection;
	private String url;
	private URL realURL;
	private Header requestHeader;
	private HttpMethod method;

	/**
	 * @author Juniter Http Get Method
	 * @param parameters
	 *            map
	 */
	@Override
	public String get(Map<String, String> map) {
		this.method = HttpMethod.GET;
		logger.info("Request Method:  {}", HttpMethod.GET);
		BufferedReader reader = null;
		StringBuffer response = new StringBuffer();
		try {
			this.setGetMethodURL(map);
			this.connection = this.realURL.openConnection();
			this.initHeader();
			this.connection.connect();
			this.logResponseHeader(connection.getHeaderFields());
			reader = new BufferedReader(new InputStreamReader(this.connection.getInputStream(), "UTF-8"));
			String metedata;
			while ((metedata = reader.readLine()) != null)
				response.append(metedata);
		} catch (IOException e) {
			logger.warn("AN ERROR HAS OCCURED WHILE SENT HTTP-GET REQUEST:{}", e.getMessage());
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e1) {
				logger.warn("FAILED TO CLOSE INPUTSTREAM:{}", e1.getMessage());
			}
		}
		logger.info("SERVER RESPONSE: {}", response.toString());
		return response.toString();
	}

	/**
	 * @author Juniter
	 * @description http post method
	 * @param T
	 */
	@Override
	public <T> String post(T t) {
		logger.info("Request Method:  {}", HttpMethod.POST);
		logger.info("URL:{}", this.url);
		this.method = HttpMethod.POST;
		PrintWriter printer = null;
		BufferedReader reader = null;
		StringBuffer response = new StringBuffer();
		try {
			this.realURL = new URL(this.url);
			this.connection = this.realURL.openConnection();
			this.initHeader();
			printer = new PrintWriter(this.connection.getOutputStream());
			// send json request
			printer.print(new Gson().toJson(t));
			printer.flush();
			reader = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
			String metedata;
			while ((metedata = reader.readLine()) != null)
				response.append(metedata);
		} catch (IOException e) {
			logger.warn("AN ERROR HAS OCCURED WHILE SENT HTTP-POST REQUEST:{}", e.getMessage());
		} finally {
			try {
				if (reader != null)
					reader.close();
				if (printer != null)
					printer.close();
			} catch (IOException e1) {
				logger.warn("FAILED TO CLOSE INPUTSTREAM:{}", e1.getMessage());
			}
		}
		logger.info("SERVER RESPONSE: {}", response.toString());
		return response.toString();
	}

	/**
	 * set request header
	 * 
	 * @param header
	 * @return
	 * 
	 */
	private void initHeader() {
		Map<String, String> headerMap = this.requestHeader.getCommonHeaderMap();
		Set<String> keySet = headerMap.keySet();
		keySet.forEach((key) -> {
			this.connection.setRequestProperty(key, headerMap.get(key));
		});
		if (this.method.equals(HttpMethod.POST))
			this.initPOSTHeader();
		else if (this.method.equals(HttpMethod.GET))
			this.initGETHeader();
		this.logRequestHeader();
	}

	/**
	 * log request logger
	 * 
	 */
	private void logRequestHeader() {
		Map<String, String> headerMap = this.requestHeader.getAllHeaderMap();
		Set<String> keySet = headerMap.keySet();
		keySet.forEach((key) -> {
			logger.info(key.toUpperCase() + ": {}", headerMap.get(key));
		});
	}

	/**
	 * 
	 * set post method header
	 * 
	 */
	private void initPOSTHeader() {
		this.connection.setRequestProperty("Content-Type", this.requestHeader.getContentType());
		this.connection.setDoOutput(true);
		this.connection.setDoInput(true);
	}

	/**
	 * set get method header
	 */
	private void initGETHeader() {
	}

	/**
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
				String encodeParam = this.encodeRequestParams(params.get(key));
				urlBuffer.append(key).append("=").append(encodeParam).append("&");
			});
			this.setRandomParameter(urlBuffer);
			this.realURL = new URL(urlBuffer.toString());
		} else
			this.realURL = new URL(url);
		logger.info("URL:{}", this.realURL.toExternalForm());

	}

	/**
	 * Encode request parameters
	 * 
	 * @param param
	 * @return
	 * 
	 */
	private String encodeRequestParams(String param) {
		StringBuffer temp = new StringBuffer("%22");
		try {
			String value = URLEncoder.encode(param, "UTF-8");
			temp.append(value).append("%22");
		} catch (UnsupportedEncodingException e) {
			logger.warn("FAILED ENCODE PARAM:{}, CAUSE FOR:", param, e.getMessage());
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
		Set<String> keySet = map.keySet();
		keySet.forEach((rh) -> {
			if (rh==null)
				logger.info("HTTP:  {}", map.get(rh));
			else
				logger.info(rh.toUpperCase()+": {}",map.get(rh));
		});
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
