package com.test.licencemgmt.models;

public class Product {
	private String name;
	private String url;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Product(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}
	
}
