package org.mpushframe.xmpp.listener;

import org.mpushframe.xmpp.node.XmppNode;

/**
 * 处理XmppNode的最底层的接口
 * @author Mr.Chen
 * date: 2018年4月28日 下午9:32:35
 */
public interface XmppNodeReadListener {
	public void readXmpp(XmppNode xmppNode);
}
