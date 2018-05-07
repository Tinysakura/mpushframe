package org.mpushframe.xmpp.node;

public class XmppNode {
	JID from;
	JID to;

	public XmppNode(JID from,JID to){
		this.from=from;
		this.to=to;
	}
	
	public JID getFrom() {
		return from;
	}

	public void setFrom(JID from) {
		this.from = from;
	}

	public JID getTo() {
		return to;
	}

	public void setTo(JID to) {
		this.to = to;
	}
}
