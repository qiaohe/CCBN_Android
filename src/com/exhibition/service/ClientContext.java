package com.exhibition.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
/**
 * 该类缓存客户端数据
 * @author clhe
 */

public class ClientContext implements Serializable{
	/**
	 * 客户端配置参数
	 */
	private Properties configProperties;
	
	/**
	 * 单例缓存上下文
	 */
	private static ClientContext context;
	
	/**
	 * 缓存客户端运行时的业务数据
	 */
	private Map<String,Object> businessData;
	
	/**
	 *  缓存当前页面操作信息
	 */
	private Map<String,Object> pageCache;
	
	private ClientContext() {
		businessData=new HashMap<String, Object>();
		pageCache=new HashMap<String, Object>();
	}
	
	public static synchronized ClientContext createClientContext(){
		if(context==null){
			context=new ClientContext();
		}
		return context;
	}
	/**
	 * 设置客户端配置参数属性集
	 */
	public void setConfigProperties(Properties pro){
		this.configProperties=pro;
	}
	
	public Object getBusinessData(String name){
		return businessData.get(name);
	}
	public void addBusinessData(String name,Object data){
		businessData.put(name, data);
	}
	public Object getPageCache(String name){
		return pageCache.get(name);
	}
	public void addPageCache(String name,Object data){
		pageCache.put(name, data);
	}
	public String getSystemProperty(String name){
		return configProperties.getProperty(name);
	}
}
