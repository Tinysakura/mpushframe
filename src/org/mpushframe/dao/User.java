package org.mpushframe.dao;

import java.util.ArrayList;
import java.util.List;

import org.mpushframe.core.ChatGroup;
import org.mpushframe.xmpp.node.JID;

/**
 * 对通讯网络中用户的建模
 * @author Mr.Chen
 * date: 2018年5月6日 下午3:52:58
 */
public class User {
	/**
	 * 用户的基本状态
	 */
	private String id;
	private JID jid;

	/**
	 * chat：聊天中
	 * away：暂时离开
	 * xa：eXtend Away，长时间离开
	 * dnd：勿打扰
	 */
	private String show;
	/**
	 * 格式自由，可阅读的文本，表示用户当前的具体状态
	 */
	private String status;
	/**
	 * 订阅用户的花名册
	 */
	private List<User> roster;
	/**
	 * 黑名单
	 */
	private List<JID> blacklist;
	/**
	 * ...
	 */
	private ChatGroup chatGroup;
	
	public User(String id,JID jid){
		this.id=id;
		this.jid=jid;
		this.roster=new ArrayList<User>();
		this.chatGroup=ChatGroup.getInstance();
	}
	
	public String getId(){
		return id;
	}
	
	public String getShow() {
		return show;
	}
	
	public void setShow(String show) {
		this.show = show;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public JID getJid() {
		return jid;
	}

	public void setJid(JID jid) {
		this.jid = jid;
	}
	
	public void setRoster(List<User> roster){
		this.roster=roster;
	}
 	
	public List<User> getRoster() {
		return roster;
	}
	
	public List<JID> getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(List<JID> blacklist) {
		this.blacklist = blacklist;
	}
	
	public void subscribe(User user){
		roster.add(user);
	}
	
	public void unsubscribe(User user){
		roster.remove(user);
	}
	
	public boolean isSubscribe(User user){
		return roster.contains(user);
	}
	
	/**
	 * @param message
	 */
	public void sendMessage(User to,String message){
		
	}
	
	/**
	 * 向roster中的user发送自己的出席消息
	 */
	public void Presence(String show,String status){
		chatGroup.notifyPresence(this,show,status);
	}
}
