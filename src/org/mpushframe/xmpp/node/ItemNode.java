package org.mpushframe.xmpp.node;

import java.util.HashMap;
import java.util.Map;

/**
 * query节点的载荷，表示query所需的相关条件或者query的结果
 * ItemNode的属性是非固定的因此使用一个Map维护
 * @author Mr.Chen
 * date: 2018年4月29日 下午1:48:37
 */
public class ItemNode {
	Map<String, String> map;
	
	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	
	public ItemNode(){
		map=new HashMap<String, String>();
	}
	
	/**
	 * 添加ItemNode的属性
	 * @param key
	 * @param value
	 */
	public void insert(String key,String value){
		this.map.put(key, value);
	}
	
	/**
	 * 判断属性列表中是否有某个特定的值
	 * 通常会对某个特定的值采取特殊的操作
	 * @param key
	 * @return
	 */
	public boolean isContainKey(String key){
		return map.containsKey(key);
	}
		
}
