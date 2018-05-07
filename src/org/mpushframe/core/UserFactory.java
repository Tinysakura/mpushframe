package org.mpushframe.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mpushframe.dao.User;
import org.mpushframe.xmpp.node.JID;

/**
 * 根据传入的jid创建user的工厂方法
 * @author Mr.Chen
 * date: 2018年5月6日 下午5:16:09
 */
public class UserFactory {
	/**
	 * 一个id到jid的转换表
	 */
	public static Map<String, JID> idMap=new HashMap<String, JID>();

	public static User getUser(JID jid,String id){
		/**
		 * 查询数据库或缓存恢复用户的基本信息
		 */
		User user=new User(id,jid);
		idMap.put(id, jid);
		/**
		 * 这里的roster与blacklist都是模拟数据
		 */
		List<JID> blackList=new ArrayList<JID>();
		/**
		 * 这个user需要对应一个通过持久化的jid匹配当前在线用户的过程
		 */
		List<User> roster=new ArrayList<User>();
		
		user.setRoster(roster);
		user.setBlacklist(blackList);
		
		return user;
	}

}
