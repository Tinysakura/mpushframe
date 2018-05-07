package org.mpushframe.xmpp.listener;

import org.mpushframe.xmpp.node.XmppNode;

/**
 * 处理MessageNode的接口
 * @author Mr.Chen
 * date: 2018年4月28日 下午9:33:32
 */
public interface MessageNodeReadListener extends XmppNodeReadListener{
	public void readMessage(XmppNode xmppNode);

}
