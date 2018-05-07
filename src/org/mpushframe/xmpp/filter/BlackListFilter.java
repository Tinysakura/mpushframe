package org.mpushframe.xmpp.filter;

import java.util.List;

import org.mpushframe.exception.IllegalDomainExcetion;
import org.mpushframe.exception.MessageFromBlackListException;
import org.mpushframe.xmpp.node.JID;
import org.mpushframe.xmpp.node.XmppNode;

/**
 * 过滤黑名单中用户发来的消息
 * 使用jid区分用户，实际生产情况可能不同
 * @author Mr.Chen
 * date: 2018年5月5日 下午2:04:46
 */
public class BlackListFilter extends AbstractXmppFilter{
	/**
	 * 接收一个黑名单,通常根据设置过滤器的用户的id从数据库中查找后生成
	 * 这里为了方便测试提供了一个仅供测试用的直接添加黑名单的入口
	 */
	private volatile List<JID> blackList;
	
	/**
	 * 根据用户id生成黑名单的构造方法
	 * @param jid
	 */
	public BlackListFilter(JID jid){
		
	}
	
	/**
	 * 用于测试的构造方法
	 */
	public BlackListFilter(List<JID> blackList){
		this.blackList=blackList;
	}
	
	/**
	 * 运行时动态添加黑名单中用户
	 * 注意线程同步
	 */
	public synchronized void enlargeBlackList(JID jid){
		this.blackList.add(jid);
	}

	@Override
	public void Filter(XmppNode xmppNode) throws MessageFromBlackListException,IllegalDomainExcetion{
		// TODO Auto-generated method stub
		//System.out.println("blacklist filter");
		if(contains(xmppNode.getFrom())){
			if(super.getNextFilter()!=null){
				super.getNextFilter().Filter(xmppNode);
			}
		}else{
			throw new MessageFromBlackListException("message from blacklist");
		}
	}
	
	public boolean contains(JID jid){
		boolean isContains=false;
		
		for(JID black:blackList){
			if(black.equal(jid)){
				isContains=true;	
				break;
			}
		}
		
		return isContains;
	}

}
