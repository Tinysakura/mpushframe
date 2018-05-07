package org.mpushframe.handler;

import org.mpushframe.core.ChatGroup;
import org.mpushframe.core.UserFactory;
import org.mpushframe.core.XmppNodeQueue;
import org.mpushframe.dao.User;
import org.mpushframe.xmpp.filter.BlackListFilter;
import org.mpushframe.xmpp.listener.PrecenseNodeReadListener;
import org.mpushframe.xmpp.node.PresenceNode;
import org.mpushframe.xmpp.node.XmppNode;

/**
 * 实现了最基本的阅读Presence节点的逻辑
 * @author Mr.Chen
 * date: 2018年5月6日 下午3:48:46
 */
public class SimplePresenceHandler implements PrecenseNodeReadListener{
	/**
	 * 表示handle工作的Queue
	 */
	private XmppNodeQueue queue;
	private ChatGroup chatGroup;
	
	public SimplePresenceHandler(XmppNodeQueue queue){
		this.queue=queue;
		this.chatGroup=ChatGroup.getInstance();
	}

	@Override
	public void readXmpp(XmppNode xmppNode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readPrecense(XmppNode xmppNode) {
		PresenceNode presenceNode=(PresenceNode)xmppNode;
		
		String id=queue.getId();
		
		/**
		 * 如果之前已经离线，则创建一个新用户加入ChatGroup并向所有订阅了自己的在线用户广播出席信息
		 */
		if(!chatGroup.isOnline(id)){
			User user=UserFactory.getUser(presenceNode.getFrom(), id);
			chatGroup.rigister(user);
			
			/**
			 * 为queue配置过滤器
			 */
			BlackListFilter blackListFilter=new BlackListFilter(user.getBlacklist());
			queue.setFilterChain(blackListFilter);
			
			/**
			 * 向roster的成员广播自己的出席消息
			 */
			user.Presence(presenceNode.getShow(),presenceNode.getStatus());
		}else{
			/**
			 * 根据presence原语的type类型分发处理逻辑
			 */
			switch (presenceNode.getType()) {
			/**
			 * 处理订阅请求
			 * 收到订阅请求后向被订阅者发送一个pubsub的iq原语
			 * 被订阅者收到iq请求后根据实际情况向订阅者写回接收订阅的presence原语"subscribed"
			 */
			case "subscribe":
				
				break;

			default:
				User me=chatGroup.getUser(id);
				/**
				 * 确认自己被通知有联系人出席
				 */
				if(presenceNode.getTo().equal(me.getJid())){
					/**
					 * 向view层发出一个更新视图的intent
					 */
					System.out.println("send a intent to view");
				}
				
				break;
			}
		}
	}

}
