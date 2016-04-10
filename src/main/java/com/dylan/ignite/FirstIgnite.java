package com.dylan.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CachePeekMode;
//import org.apache.ignite.cache.CachePeekMode;
//import org.apache.ignite.configuration.IgniteConfiguration;

public class FirstIgnite {
	
	public static void main(String[] args) {
		
		Ignite ignite = Ignition.start("com/dylan/ignite/example-cache.xml");
		
//		Ignite ignite = Ignition.ignite();
		
		IgniteCache<Integer, String> cache = ignite.cache("dxCache");  //getOrCreateCache("myCache");//ignite.cache("myCache");
		
		System.out.println(cache.size(CachePeekMode.ALL));
		for (int i = 0; i < 10; i++)
	        cache.putIfAbsent(i, Integer.toString(i));
	    for (int i = 0; i < 10; i++)
	        System.out.println("Got [key=" + i + ", val=" + cache.get(i) + ']');
	    
	    System.out.println(cache.size(CachePeekMode.ALL));
	
	}

}
