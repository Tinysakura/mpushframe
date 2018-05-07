package org.mpushframe.xmpp.rw;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.mpushframe.xmpp.node.IqNode;
import org.mpushframe.xmpp.node.ItemNode;
import org.mpushframe.xmpp.node.JID;
import org.mpushframe.xmpp.node.MessageNode;
import org.mpushframe.xmpp.node.PresenceNode;
import org.mpushframe.xmpp.node.QueryNode;
import org.mpushframe.xmpp.node.XmppNode;

/**
 * 将XmppStanzar转化为面向对象的XmppNode的Reader类
 * 使用dom4j解析
 * @author Mr.Chen
 * date: 2018年4月28日 下午9:38:22
 */
public class XmppReader {
	/**
	 * 根据Stanzar的头对处理逻辑进行分发
	 */
	public static XmppNode read(String stanzar){
		XmppNode xmppNode=null;
		Document document=null;
		
		/**
		 * 将stanzar转换为dom4j可以处理的documen
		 */
		try {
			document=DocumentHelper.parseText(stanzar);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		if(document!=null){
			Element root=document.getRootElement();
			
			switch (root.getName()) {
			case "message":
				xmppNode=readMessage(document);
				break;
				
			case "presence":
				xmppNode=readPresence(document);
			    break;
				
			case "iq":
				xmppNode=readIq(document);
			    break;
			}
		}
		
		return xmppNode;
	}
	
	/**
	 * 读取messageStanzar
	 * @param document
	 * @return
	 */
	public static MessageNode readMessage(Document document){
		MessageNode messageNode=null;
		MessageNode.Builder messageBuilder=null;
		
		Element root=document.getRootElement();
		JID from=null;
		JID to=null;
		String type=root.attributeValue("type");
		if(root.attributeValue("from")!=null){
			from=readJID(root.attributeValue("from"));
		}
		if(root.attributeValue("to")!=null){
			to=readJID(root.attributeValue("to"));
		}
		messageBuilder=new MessageNode.Builder(from, to, type);
		
		/**
		 * 读取载荷元素
		 */
		for(Element child:root.elements()){
			switch (child.getName()) {
			case "body":
				messageBuilder.body(child.getText());
				break;
			case "subject":
				messageBuilder.subject(child.getText());
				break;
			}
		}
		
		messageNode=messageBuilder.build();
		return messageNode;
	}
	
	/**
	 * 读取presenceStanzar
	 * @param document
	 * @return
	 */
	public static PresenceNode readPresence(Document document){
		PresenceNode presenceNode=null;
		PresenceNode.Builder presenceBuilder=null;
		
		Element root=document.getRootElement();
		JID from=null;
		JID to=null;
		String type=root.attributeValue("type");
		if(root.attributeValue("from")!=null){
			from=readJID(root.attributeValue("from"));
		}
		if(root.attributeValue("to")!=null){
			to=readJID(root.attributeValue("to"));
		}
		presenceBuilder=new PresenceNode.Builder(from, to, type);
		
		/**
		 * 读取载荷元素
		 */
		for(Element child:root.elements()){
			switch (child.getName()) {
			case "show":
				presenceBuilder.show(child.getText());
				break;
			case "status":
				presenceBuilder.status(child.getText());
				break;
			case "priority":
				presenceBuilder.priority(new Integer(child.getText()));
				break;
			}
		}
		
		presenceNode=presenceBuilder.build();
		return presenceNode;
	}
	
	/**
	 * 读取IqStanzar
	 * @param document
	 * @return
	 */
	public static IqNode readIq(Document document){
		IqNode iqNode=null;
		IqNode.Builder iqBuilder=null;
		
		Element root=document.getRootElement();
		JID from=null;
		JID to=null;
		String type=root.attributeValue("type");
		String id=root.attributeValue("id");
		if(root.attributeValue("from")!=null){
			from=readJID(root.attributeValue("from"));
		}
		if(root.attributeValue("to")!=null){
			to=readJID(root.attributeValue("to"));
		}
		iqBuilder=new IqNode.Builder(from, to, type,id);
		
		/**
		 * 读取载荷元素
		 */
		for(Element child:root.elements()){
			iqBuilder.query(readQuery(child));
		}
		
		iqNode=iqBuilder.build();
		return iqNode;
	}
	
	/**
	 * 读取queryNode
	 */
	public static QueryNode readQuery(Element queryElement){
		System.out.println(queryElement.getName());
		QueryNode queryNode=new QueryNode(queryElement.getNamespaceURI());
		System.out.println(queryElement.getNamespaceURI());
		List<ItemNode> itemNodes=new ArrayList<ItemNode>();
		
		for(Element childElement:queryElement.elements()){
			ItemNode itemNode=new ItemNode();
			for(Attribute attribute:childElement.attributes()){
				System.out.println(attribute.getName()+":"+attribute.getText());
				itemNode.insert(attribute.getName(), attribute.getText());
			}
			
			itemNodes.add(itemNode);
		}
		
		queryNode.addItems(itemNodes);
		
		return queryNode;
	}
	
	/**
	 * 将JID字段转换成JID对象的方法
	 */
	public static JID readJID(String JID){
		//System.out.println("JID:"+JID);
		String dominIdentifier= null;
		String nodeIdentifier = null;
		String resourceIdentifier = null;
		String[] temp1 = null;
		
		if(JID.indexOf('@')!=-1){
			temp1=JID.split("@");
			nodeIdentifier=temp1[0];
			//System.out.println(temp1[0]);
		}else{
			dominIdentifier=JID;
		}
		
		if (temp1!=null) {
			String[] temp2 = temp1[1].split("/");
			if (temp2.length == 2) {
				dominIdentifier= temp2[0];
				resourceIdentifier = temp2[1];
			} else if (temp2.length == 1) {
				dominIdentifier = temp2[0];
			}
		}
		
		//System.out.println(dominIdentifier+"@"+nodeIdentifier+"/"+resourceIdentifier);
		
		return new JID(dominIdentifier, nodeIdentifier, resourceIdentifier);
	}
	
}
