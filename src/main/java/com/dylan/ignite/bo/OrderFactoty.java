package com.dylan.ignite.bo;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

public class OrderFactoty {
	
	
	private Random rd = new Random();
	
	public MyOrder create(){
		
		MyOrder  order = new MyOrder();
		order.setVloum(rd.nextInt(100000));
		
		BigDecimal b = new BigDecimal(rd.nextDouble());  
		double f1 = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
		order.setPrice(f1);
		
		order.setOrderId(UUID.randomUUID().toString());
		
		return order;
		
	}

}
