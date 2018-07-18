package com.leysoft.document;

public class Product {
	
	private String id;
	
	private String name;
	
	private Double price;
	
	private Double storeId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getStoreId() {
		return storeId;
	}

	public void setStoreId(Double storeId) {
		this.storeId = storeId;
	}
}
