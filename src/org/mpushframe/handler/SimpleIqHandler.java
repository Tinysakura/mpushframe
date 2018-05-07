package org.mpushframe.handler;

import org.mpushframe.core.XmppNodeQueue;
import org.mpushframe.xmpp.listener.IqNodeReadListener;
import org.mpushframe.xmpp.node.XmppNode;

public class SimpleIqHandler implements IqNodeReadListener{
	@SuppressWarnings("unused")
	private XmppNodeQueue queue;
	
	public SimpleIqHandler(XmppNodeQueue queue){
		this.queue=queue;
	}

	@Override
	public void readXmpp(XmppNode xmppNode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readIq(XmppNode xmppNode) {
		// TODO Auto-generated method stub
		
	}

}
