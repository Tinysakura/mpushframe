package org.mpushframe.xmpp.listener;

import org.mpushframe.xmpp.node.XmppNode;

/**
 * 处理IqNode的接口
 * @author Mr.Chen
 * date: 2018年4月28日 下午9:35:16
 */
public interface IqNodeReadListener extends XmppNodeReadListener{
	public void readIq(XmppNode xmppNode);
}
