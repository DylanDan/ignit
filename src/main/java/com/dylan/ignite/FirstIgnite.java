package com.dylan.ignite;

import java.net.URL;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CachePeekMode;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.internal.util.typedef.internal.U;

import com.dylan.ignite.bo.MyOrder;
import com.dylan.ignite.bo.OrderFactoty;


public class FirstIgnite {
	
	public static void main(String[] args) {
		
		
		IgniteConfiguration cfg = new IgniteConfiguration();
		URL xml = U.resolveIgniteUrl("config/custom-log4j.xml");	
		
		Ignite ignite = Ignition.start("com/dylan/ignite/example-cache.xml");
		
//		IgniteDataStreamer<Integer, String> stmr =  ignite.dataStreamer("dxCache");
//		stmr.allowOverwrite(true);
////		stmr.receiver(rcvr);
		
//		Ignite ignite = Ignition.ignite();
		
		IgniteCache<String,MyOrder> cache = ignite.cache("dxCache");
		
		
		
		System.out.println(cache.size(CachePeekMode.PRIMARY));
		
		cache.loadCache(null, null);
/*		OrderFactoty of = new OrderFactoty();
		for (int i = 0; i < 10000; i++){
			
			MyOrder order = of.create();
			
			cache.put(order.getOrderId(),order);
			System.out.println("put ["+order.getOrderId()+"] into cache");
		}*/
		
	    new Thread(new Monitor(cache)).start();
	}
	

	static class Monitor implements Runnable{
		
		IgniteCache<String,MyOrder> m_cache;
		
		public Monitor(IgniteCache<String,MyOrder> p_cache){
			m_cache = p_cache;
		}
		
		@Override
		public void run() {
			while(true){
				System.out.println("*********************start******************************");
				System.out.println(m_cache.size(CachePeekMode.PRIMARY));
				System.out.println(m_cache.size(CachePeekMode.NEAR));
				System.out.println(m_cache.size(CachePeekMode.ALL));
				System.out.println(m_cache.size(CachePeekMode.BACKUP));
				System.out.println(m_cache.localSize());
				System.out.println("***********************end******************************");
				try {
					Thread.sleep(2*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
}
