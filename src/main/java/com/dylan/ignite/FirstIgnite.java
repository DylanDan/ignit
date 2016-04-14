package com.dylan.ignite;

import java.util.Iterator;

import javax.cache.Cache.Entry;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CachePeekMode;
//import org.apache.ignite.cache.CachePeekMode;
//import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.cache.store.CacheStore;

public class FirstIgnite {
	
	public static void main(String[] args) {
		
		Ignite ignite = Ignition.start("com/dylan/ignite/example-cache.xml");
		IgniteDataStreamer<Integer, String> stmr =  ignite.dataStreamer("dxCache");
		stmr.allowOverwrite(true);
//		stmr.receiver(rcvr);
		 for (int i = 0; i < 100000; i++)
		        stmr.addData(i, Integer.toString(i));
		
//		Ignite ignite = Ignition.ignite();
		
		IgniteCache<Integer, String> cache = ignite.cache("dxCache");  //getOrCreateCache("myCache");//ignite.cache("myCache");
		
		System.out.println(cache.size(CachePeekMode.PRIMARY));
		for (int i = 0; i < 10; i++)
	        cache.putIfAbsent(i, Integer.toString(i));
		
		Iterator<Entry<Integer, String>> it = cache.iterator();
		while(it.hasNext()){
			Entry<Integer, String> item = it.next();
			System.out.println("Got [key=" + item.getKey() + ", val=" + item.getValue() + ']');
		}
		cache.rebalance();
		System.out.println(cache.size(CachePeekMode.PRIMARY));
		System.out.println(cache.size(CachePeekMode.NEAR));
	    System.out.println(cache.size(CachePeekMode.ALL));
	    System.out.println(cache.size(CachePeekMode.BACKUP));
	    System.out.println(cache.localSize());
	    new Thread(new Monitor(cache)).start();
	}
	

	static class Monitor implements Runnable{
		
		IgniteCache<Integer, String> m_cache;
		
		public Monitor(IgniteCache<Integer, String> p_cache){
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
