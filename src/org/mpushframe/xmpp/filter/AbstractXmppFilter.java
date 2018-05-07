  package org.mpushframe.xmpp.filter;

import org.mpushframe.exception.IllegalDomainExcetion;
import org.mpushframe.exception.MessageFromBlackListException;
import org.mpushframe.xmpp.node.XmppNode;

/**
 * 一个抽象的XmppFilter
 * XmppFilter的实现使用了设计模式的责任链模式
 * @author Mr.Chen
 * date: 2018年4月28日 下午9:05:26
 */
public abstract class AbstractXmppFilter {
	private AbstractXmppFilter nextFilter=null;
	
	public AbstractXmppFilter(){
		
	}

	/**
	 * 设置下一个责任
	 */
	public void setNextFilter(AbstractXmppFilter nextFilter){
		this.nextFilter=nextFilter;
	}
	
	public AbstractXmppFilter getNextFilter(){
		return nextFilter;
	}
	
	/**
	 * 对XmppNode进行过滤
	 * 若过滤通过则不改变Node的合法性字段,否则将Node的合法性置为false
	 * 根据过滤情况决定是否将Node交由下一个过滤器过滤
	 */
	public abstract void Filter(XmppNode xmppNode) throws IllegalDomainExcetion,MessageFromBlackListException;

}
