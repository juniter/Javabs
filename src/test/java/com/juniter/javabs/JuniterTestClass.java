package com.juniter.javabs;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class JuniterTestClass {
	public static void main(String args[]) {
		System.out.println(isInteger("1a22.7"));;
	}
	 public static String isInteger(String param) {
		 String result = null;
			try {
				Double.valueOf(param);
				return param;
			} catch (NumberFormatException e) {
				StringBuffer temp = new StringBuffer("%22");
				try {
					String value = URLEncoder.encode(param, "UTF-8");
					return temp.append(value).append("%22").toString();
				} catch (UnsupportedEncodingException eu) {
				}
			}
			return null;
	 }
}
