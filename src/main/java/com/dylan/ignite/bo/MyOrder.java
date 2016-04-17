package com.dylan.ignite.bo;

import java.io.Serializable;

import org.apache.ignite.cache.query.annotations.QuerySqlField;


public class MyOrder implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@QuerySqlField(index=true)
	private int vloum;
	
	@QuerySqlField(index=true)
	private double price;
	
	@QuerySqlField(index=true)
	private String orderId;

	public int getVloum() {
		return vloum;
	}

	public void setVloum(int vloum) {
		this.vloum = vloum;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
