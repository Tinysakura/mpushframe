package org.mpushframe.handler;

import org.mpushframe.core.XmppNodeQueue;
import org.mpushframe.xmpp.listener.MessageNodeReadListener;
import org.mpushframe.xmpp.node.MessageNode;
import org.mpushframe.xmpp.node.XmppNode;

/**
 * 实现了最基本的阅读message节点的逻辑
 * @author Mr.Chen
 * date: 2018年5月6日 下午3:49:18
 */
public class SimpleMessageHandler implements MessageNodeReadListener{
	@SuppressWarnings("unused")
	private XmppNodeQueue queue;
	
	public SimpleMessageHandler(XmppNodeQueue queue){
		this.queue=queue;
	}

	@Override
	public void readXmpp(XmppNode xmppNode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readMessage(XmppNode xmppNode) {
		// TODO Auto-generated method stub
		MessageNode messageNode=(MessageNode)xmppNode;
		
		System.out.println(messageNode.toString());
	}

}
