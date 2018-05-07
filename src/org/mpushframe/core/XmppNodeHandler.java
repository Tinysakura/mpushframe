package org.mpushframe.core;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.mpushframe.handler.SimpleIqHandler;
import org.mpushframe.handler.SimpleMessageHandler;
import org.mpushframe.handler.SimplePresenceHandler;
import org.mpushframe.xmpp.rw.XmppReader;

/**
 * 针对解析xmpp协议所获得的node定制的Handler类
 * @author Mr.Chen
 * date: 2018年4月28日 下午8:35:44
 */
public class XmppNodeHandler extends IoHandlerAdapter{
	/**
	 * 注入adapter
	 */
	SessionMap sessionMap=SessionMap.getInstance();
	XmppNodeQueueList xmppNodeQueueList=XmppNodeQueueList.getInstance();

	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(session, cause);
	}

	public void inputClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.inputClosed(session);
	}

	/**
	 * 在第一次创建与客户端建立联系后保存session，并赋予session一个独一无二的标识
	 */
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
	}

	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionOpened(session);
	}

	/**
	 * 将为这个连接分配的处理队列销毁
	 */
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
		System.out.println("close stream link");
		xmppNodeQueueList.stop((String)session.getAttribute("id"));
		session.removeAttribute("id");
	}

	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		// TODO Auto-generated method stub
		super.sessionIdle(session, status);
	}

	/**
	 * 收到<stream:stream>标签则建立连接
	 * 收到消息后根据消息的id将消息加入相应的队列中等待处理
	 */
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String temp=(String)message;
		System.out.println(temp);
		
		switch (temp) {
		case "<stream:stream>":
			System.out.println("create stream link");
			String id=sessionMap.getUniqueId();
			session.setAttribute("id", id);
			sessionMap.put(id, session);
			XmppNodeQueue queue=new XmppNodeQueue(id);
			//为queue配置处理消息的代理...
			queue.setMessageHandler(new SimpleMessageHandler(queue));
			queue.setPrecenseHandler(new SimplePresenceHandler(queue));
			queue.setIqHandler(new SimpleIqHandler(queue));
			
			xmppNodeQueueList.put(queue);
			break;

		case "</stream:stream>":
			sessionClosed(session);
			break;
			
		default:
			if(session.containsAttribute("id")){
				String id1=(String) session.getAttribute("id");
				XmppNodeQueue queue1=xmppNodeQueueList.get(id1);
				
				queue1.put(XmppReader.read((String)message));
			}
			break;
		}
	}

	public void messageSent(IoSession session, Object message) throws Exception {
	}

}
