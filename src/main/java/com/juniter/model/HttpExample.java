package com.juniter.model;

import com.google.gson.Gson;

public class HttpExample {
	private String name;
	private String password;
	private String phone;

	public String getName() {
		return name;
	}

	public HttpExample setName(String name) {
		this.name = name;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public HttpExample setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public HttpExample setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
	
}
