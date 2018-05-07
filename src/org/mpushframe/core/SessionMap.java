package org.mpushframe.core;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.session.IoSession;
import org.mpushframe.util.GenerateUniqueID;

/**
 * 一个管理session连接的单例服务类
 * @author Mr.Chen
 * date: 2018年4月30日 下午2:08:30
 */
public class SessionMap {
	private static volatile SessionMap sessionMap;
	
	private Map<String,IoSession> map; 
	
	private SessionMap(){
		map=new HashMap<String, IoSession>();
	}
	
	public static SessionMap getInstance(){
		if(sessionMap==null){
			synchronized (SessionMap.class) {
				if(sessionMap==null){
					sessionMap=new SessionMap();
				}
			}
		}
		
		return new SessionMap();
	}
	
	/**
	 * 向map中存入新的session
	 * 存入前要判断系统自动生成的id是否重复
	 */
	public synchronized void put(String key,IoSession ioSession){
		map.put(key, ioSession);
	}
	
	/**
	 * 获取map中的session用于向客户端发送消息
	 */
	public synchronized IoSession get(String key){
		return map.get(key);
	}
	
    /**
     * 判断id是否已存在
     */
	public synchronized String getUniqueId(){
		String key=GenerateUniqueID.generate();
		
		while(map.containsKey(key)){
			key=GenerateUniqueID.generate();
		}
		
		return key;
	}

}
