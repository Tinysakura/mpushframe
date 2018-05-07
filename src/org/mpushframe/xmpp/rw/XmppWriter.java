package org.mpushframe.xmpp.rw;

import org.mpushframe.xmpp.node.IqNode;
import org.mpushframe.xmpp.node.ItemNode;
import org.mpushframe.xmpp.node.MessageNode;
import org.mpushframe.xmpp.node.PresenceNode;
import org.mpushframe.xmpp.node.QueryNode;
import org.mpushframe.xmpp.node.XmppNode;

/**
 * 将XmppNode转换为对应的XmppStanzar的Writer类
 * @author Mr.Chen
 * date: 2018年4月28日 下午9:41:33
 */
public class XmppWriter {
	/**
	 * 根据xmppNode的实际类型对处理逻辑进行分发
	 * @param xmppNode
	 */
	public static String write(XmppNode xmppNode){
		String result;
		
		if(xmppNode instanceof MessageNode){
			result=writeMessage((MessageNode) xmppNode);
		}else if(xmppNode instanceof PresenceNode){
			result=writePresence((PresenceNode)xmppNode);
		}else{
			result=writeIq((IqNode)xmppNode);
		}
		
		return result;
	}
	
	public static String writeMessage(MessageNode messageNode){
		StringBuilder sb=new StringBuilder();
		sb.append("<message");
		
		/**
		 * 构建原语
		 */
		if(messageNode.getFrom()!=null){
			sb.append(" from=\""+messageNode.getFrom().toString()+"\"");
		}
		if(messageNode.getTo()!=null){
			sb.append(" to=\""+messageNode.getTo().toString()+"\"");
		}
		if(messageNode.getType()!=null){
			sb.append(" type=\""+messageNode.getType()+"\"");
		}
		sb.append(">");
		
		/**
		 * 构建载荷
		 */
		if(messageNode.getBody()!=null){
			sb.append("<body>"+messageNode.getBody()+"</body>");
		}
		if(messageNode.getSubject()!=null){
			sb.append("<subject>"+messageNode.getSubject()+"</subject>");
		}
		sb.append("</message>");
		sb.append("\n");
		
		return sb.toString();
	}
	
	public static String writePresence(PresenceNode presenceNode){
		StringBuilder sb=new StringBuilder();
		sb.append("<presence");
		
		/**
		 * 构建原语
		 */
		if(presenceNode.getFrom()!=null){
			sb.append(" from=\""+presenceNode.getFrom().toString()+"\"");
		}
		if(presenceNode.getTo()!=null){
			sb.append(" to=\""+presenceNode.getTo().toString()+"\"");
		}
		if(presenceNode.getType()!=null){
			sb.append(" type=\""+presenceNode.getType()+"\"");
		}
		sb.append(">");
		
		/**
		 * 构建载荷
		 */
		if(presenceNode.getShow()!=null){
			sb.append("<show>"+presenceNode.getShow()+"</show>");
		}
		if(presenceNode.getStatus()!=null){
			sb.append("<status>"+presenceNode.getStatus()+"</status>");
		}
		if(presenceNode.getPriority()!=0){
			sb.append("<priority>"+presenceNode.getPriority()+"</priority>");
		}
		
		sb.append("</presence>");
		sb.append("\n");
		
		return sb.toString();
	}
	
	public static String writeIq(IqNode iqNode){
		StringBuilder sb=new StringBuilder();
		sb.append("<iq");
		
		/**
		 * 构建原语
		 */
		if(iqNode.getFrom()!=null){
			sb.append(" from=\""+iqNode.getFrom().toString()+"\"");
		}
		if(iqNode.getTo()!=null){
			sb.append(" to=\""+iqNode.getTo().toString()+"\"");
		}
		if(iqNode.getId()!=null){
			sb.append(" id=\""+iqNode.getId()+"\"");
		}
		if(iqNode.getType()!=null){
			sb.append(" type=\""+iqNode.getType()+"\"");
		}
		sb.append(">");
		
		/**
		 * 构建载荷
		 */
		if(iqNode.getQuery()!=null){
			QueryNode queryNode=iqNode.getQuery();
			sb.append("<query xmlns=\""+queryNode.getQueryContext()+"\">"+
			writeQuery(queryNode)+"</query>");
		}
		sb.append("</iq>");
		sb.append("\n");
		return sb.toString();
	}
	
	public static String writeQuery(QueryNode queryNode){
		StringBuilder sb=new StringBuilder();
		
		for(ItemNode item:queryNode.getItems()){
			sb.append("<item");
			for(String key:item.getMap().keySet()){
				sb.append(" "+key+"="+"\""+item.getMap().get(key)+"\"");
			}
			sb.append("/>");
		}
		
		return sb.toString();
	}

}
