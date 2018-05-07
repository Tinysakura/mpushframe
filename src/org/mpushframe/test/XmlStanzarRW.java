package org.mpushframe.test;

import org.junit.Test;
import org.mpushframe.xmpp.node.XmppNode;
import org.mpushframe.xmpp.rw.XmppReader;
import org.mpushframe.xmpp.rw.XmppWriter;

public class XmlStanzarRW {
	@Test
	public void readPresence(){
		String stanzar=
			"<presence from=\"ogg@skh.whu.edu.cn\">"+
		    " <show>xa</show>"+
		    "<status>down the rabbit hole!</status>"+
		    "</presence>";
		
		XmppNode xmppNode=XmppReader.read(stanzar);
		System.out.println(xmppNode.toString());
	}
	
	@Test
	public void readIq(){
		String stanzar=
			"<iq from=\"skh.whu.edu.cn\" id=\"rr82a1z7\" to=\"suke@skh.whu.edu.cn\" type=\"result\">"+
			  "<query xmlns=\"jabber:iq:roster\">"+
			      "<item jid=\"suke@skh.whu.edu.cn\"/>"+
			      "<item jid=\"gmz@skh.whu.edu.cn\"/>"+
			      "<item jid=\"beta@skh.whu.edu.cn\"/>"+
			  "</query>"+
			"</iq>";
		
		XmppNode xmppNode=XmppReader.read(stanzar);
		//System.out.println(xmppNode.toString());
		
		String stanzar2=XmppWriter.write(xmppNode);
		System.out.print(stanzar2);
		System.out.print(XmppReader.read(stanzar2).toString());
	}
	
	@Test
	public void readMessage(){
		String stanzar=
		  "<message from=\"suke@skh.whu.edu.cn\" to=\"beta@skh.whu.edu.cn\" type=\"chat\">"+
		    "<body>Who are you?</body>"+
		    "<subject>Query</subject>"+
		  "</message>";
		
		XmppNode xmppNode=XmppReader.read(stanzar);
		System.out.println(xmppNode.toString());
	}
	
	@Test
	public void writeMessage(){
		String stanzar=
				  "<message from=\"suke@skh.whu.edu.cn\" to=\"beta@skh.whu.edu.cn\" type=\"chat\">"+
				    "<body>Who are you?</body>"+
				    "<subject>Query</subject>"+
				  "</message>";
				
		XmppNode xmppNode=XmppReader.read(stanzar);
		
		System.out.print(XmppWriter.write(xmppNode));
		
		XmppNode xmppNode2=XmppReader.read(XmppWriter.write(xmppNode));
		
		System.out.print(xmppNode2.toString());
		
	}
	
	@Test
	public void testInvokeDynamic(){
	}
}
