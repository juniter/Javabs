package com.juniter.http;

import java.net.MalformedURLException;
import java.util.Map;

/**
 * Http Interface
 * @author Juniter
 *
 */
public interface HttpExecuter {
	String get(Map<String,String> map) throws MalformedURLException ;
	<T extends Object> String post(T t);
}
