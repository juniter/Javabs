package com.juniter.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

	@Override
	public String get(Map<String, String> map) {
		this.method = HttpMethod.GET;
		logger.info("REQUEST METHOD:  {}", HttpMethod.GET);
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

	@Override
	public <T> String post(T t) {
		logger.info("REQUEST METHOD:  {}", HttpMethod.POST);
		this.method = HttpMethod.POST;
		OutputStreamWriter printer = null;
		BufferedReader reader = null;
		StringBuffer response = new StringBuffer();
		logger.info("URL:{}", this.url);
		try {
			this.realURL = new URL(this.url);
			this.connection = this.realURL.openConnection();
			this.initHeader();
			this.connection.connect();
			printer = new OutputStreamWriter(this.connection.getOutputStream(),"UTF-8");
			printer.write(new Gson().toJson(t));
			printer.flush();
			this.logResponseHeader(connection.getHeaderFields());
			reader = new BufferedReader(new InputStreamReader(this.connection.getInputStream(), "UTF-8"));
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

	private void logRequestHeader() {
		Map<String, String> headerMap = this.requestHeader.getAllHeaderMap();
		Set<String> keySet = headerMap.keySet();
		keySet.forEach((key) -> {
			logger.info(key.toUpperCase() + ": {}", headerMap.get(key));
		});
	}

	private void initPOSTHeader() {
		this.connection.setRequestProperty("Content-Type", this.requestHeader.getContentType());
		this.connection.setDoOutput(true);
		this.connection.setDoInput(true);
	}

	private void initGETHeader() {
	}

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

	private String encodeRequestParams(String param) {
		try {
			Double.valueOf(param);
			return param;
		} catch (NumberFormatException e) {
			StringBuffer temp = new StringBuffer("%22");
			try {
				String value = URLEncoder.encode(param, "UTF-8");
				return temp.append(value).append("%22").toString();
			} catch (UnsupportedEncodingException eu) {
				logger.warn("FAILED ENCODE PARAM:{}, CAUSE FOR:", param, eu.getMessage());
			}
		}
		return null;
	}

	private void setRandomParameter(StringBuffer buffer) {
		buffer.append("dc_").append("=%22").append(System.nanoTime()).append("%22");
	}

	private void logResponseHeader(Map<String, List<String>> map) {
		Set<String> keySet = map.keySet();
		keySet.forEach((rh) -> {
			if (rh == null)
				logger.info("HTTP:  {}", map.get(rh));
			else
				logger.info(rh.toUpperCase() + ": {}", map.get(rh));
		});
	}

	public HttpClient setUrl(String url) {
		this.url = url;
		return this;
	}

	public HttpClient setRequestHeader(Header requestHeader) {
		this.requestHeader = requestHeader;
		return this;
	}

}
