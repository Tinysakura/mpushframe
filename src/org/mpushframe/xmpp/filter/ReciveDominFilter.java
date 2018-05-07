package org.mpushframe.xmpp.filter;

import org.mpushframe.exception.IllegalDomainExcetion;
import org.mpushframe.exception.MessageFromBlackListException;
import org.mpushframe.xmpp.node.XmppNode;

/**
 * 域名过滤器
 * 只接受来自指定域名的Xmpp包
 * @author Mr.Chen
 */
public class ReciveDominFilter extends AbstractXmppFilter{
	/**
	 * 指定域
	 */
	private String filterDomain;
	
	public ReciveDominFilter(String filterDomain) {
		this.filterDomain=filterDomain;
	}

	@Override
	public void Filter(XmppNode xmppNode) throws IllegalDomainExcetion, MessageFromBlackListException{
		/**
		 * 如果域名合法则将xmppNode交由下一个可能存在的过滤器检查
		 * 否则抛出IllegalDomainException交由调用者捕获处理
		 */
		if(!xmppNode.getFrom().getDominIdentifier().equals(filterDomain)){	
			if(super.getNextFilter()!=null){
				super.getNextFilter().Filter(xmppNode);
			}
		}else{
			throw new IllegalDomainExcetion("illegal recive domain from:"+xmppNode.getFrom().getDominIdentifier());
		}
	}

}
