package org.mpushframe.xmpp.listener;

import org.mpushframe.xmpp.node.XmppNode;

/**
 * 处理PrecenceNode的接口
 * @author Mr.Chen
 * date: 2018年4月28日 下午9:34:25
 */
public interface PrecenseNodeReadListener extends XmppNodeReadListener{
	public void readPrecense(XmppNode xmppNode);
}
