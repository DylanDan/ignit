package com.dylan.ignite;

import java.util.List;

import javax.cache.Cache.Entry;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.cache.query.SqlQuery;

import com.dylan.ignite.bo.MyOrder;


public class FirstIgniteQuery<K,V> {
	
	
	private Ignite ignite;
	
	public FirstIgniteQuery(){
		ignite = Ignition.start("com/dylan/ignite/example-cache.xml");
	}
	
	@SuppressWarnings("rawtypes")
	public void sqlJoinQuery(){
		IgniteCache<String,MyOrder> cache = ignite.cache("dxCache");
		@SuppressWarnings("unchecked")
		SqlQuery sql = new SqlQuery(MyOrder.class,"from MyOrder " + "where "+ "and upper(MyOrder.orderId) = upper(?)");
		QueryCursor<Entry<String, MyOrder>> cursor = null;
		try{
			cursor = cache.query(sql.setArgs("590073fd-bd5c-4950-8993-58958c126dbb"));
			for (Entry<String, MyOrder> e : cursor)
			   System.out.println(e.getValue().getVloum());
		}finally{
			cursor.close();
		}
	}
	
	public void sqlQuery(Class clazz){
		IgniteCache<K,V> cache = ignite.cache("dxCache");
		SqlQuery sql = new SqlQuery(clazz, "vloum > ?");
		QueryCursor<Entry<String, MyOrder>> cursor = null;
		
		try{
			cursor = cache.query(sql.setArgs(20));
			for (Entry<String, MyOrder> e : cursor)
			   System.out.println(e.getValue().getVloum());
		}finally{
			cursor.close();
		}
	}
	
	public void SqlFieldsQuery(){
		IgniteCache<String,MyOrder> cache = ignite.cache("dxCache");
		SqlFieldsQuery sql = new SqlFieldsQuery("select price,vloum from MyOrder where vloum > ? and price > ?");
		QueryCursor<List<?>> cursor = null;
		try{
			cursor = cache.query(sql.setArgs(1000,0.5));
			for (List<?> e : cursor)
			   System.out.println(e.get(0)+"="+e.get(1));
		}finally{
			cursor.close();
		}
	}
	
	public void stop(){
		
//		ignite.destroyCache("dxCache");
//		ignite.close();
		System.exit(0);
	}
	
	public static void main(String[] args) {
		
		System.out.println(MyOrder.class);
		
//		FirstIgniteQuery fiq = new FirstIgniteQuery();
//		fiq.SqlFieldsQuery();
//		fiq.stop();	
	}
}
