package org.mpushframe.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mpushframe.dao.User;
import org.mpushframe.xmpp.node.PresenceNode;
import org.mpushframe.xmpp.rw.XmppWriter;

/**
 * 使用中介者模式实现的用户交互类
 * 用户之间的直接交互交由ChatGroup中间类完成以降低系统的复杂度
 * 由于系统只需要一个中介者对象所以设计成单例类
 * 只实现了部分核心功能实际应用需要继续扩展
 * @author Mr.Chen
 * date: 2018年5月6日 下午4:14:09
 */
public class ChatGroup {
	private static volatile ChatGroup chatGroup;
	
	/**
	 * 持有所有加入ChatGroup的用户的引用
	 */
	private Map<String,User> allUsers;
	/**
	 * 作为消息收发的中转站需要拥有直接操作通信管道session的能力
	 */
	private SessionMap sessionMap;
	
	private ChatGroup(){
		allUsers=new HashMap<String,User>();
		sessionMap=SessionMap.getInstance();
	}
	
	public static ChatGroup getInstance(){
		if(chatGroup==null){
			synchronized (ChatGroup.class) {
				if(chatGroup==null){
					chatGroup=new ChatGroup();
				}
			}
		}
		
		return chatGroup;
	}
	
	public void rigister(User user){
		allUsers.put(user.getId(), user);
	}
	
	public void rigister(User user,PresenceNode presenceNode){
		
	}
	
	public void unrigister(User user){
		allUsers.remove(user);
	}
	
	public boolean isOnline(String id){
		return allUsers.containsKey(id);
	}
	
	public User getUser(String id){
		return allUsers.get(id);
	}
	
	public void notifyPresence(User presencer,String show,String status){
		List<User> roster=presencer.getRoster();
		
		/**
		 * 向所有当前在线的目标用户的消息处理队列发送一个(高优先级的)PrecenseNode
		 */
		for(User linkMan:roster){
			if(isOnline(linkMan.getId())){
				/**
				 * 根据已知的消息构建Xmpp的xml字节流
				 */
				PresenceNode presenceNode=null;
				String xmlStream=XmppWriter.writePresence(presenceNode);
				sessionMap.get(linkMan.getId()).write(xmlStream);
			}
		}
	}

}
