package org.mpushframe.xmpp.node;

/**
 * Xmpp中的presence原语
 * 使用builder模式进行构建
 * @author Mr.Chen
 * date: 2018年4月29日 下午12:54:07
 */
public class PresenceNode extends XmppNode{
	/**
	 * subscribe：订阅其他用户的状态
	 * probe：请求获取其他用户的状态
	 * unavailable：不可用，离线（offline）状态
	 */
	String type;
	
	/**
	 * 载荷
	 */
	/**
	 * chat：聊天中
	 * away：暂时离开
	 * xa：eXtend Away，长时间离开
	 * dnd：勿打扰
	 */
	String show;
	/**
	 * 格式自由，可阅读的文本，表示用户当前的具体状态
	 */
	String status;
	/**
	 * priority：范围-128~127
	 * 高优先级的resource能接受发送到bare JID的消息，低优先级的resource不能。
	 * 优先级为负数的resource不能收到发送到bare JID的消息。
	 */
	int priority;
	
	
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	private PresenceNode(Builder builder){
		super(builder.from, builder.to);
		this.type=builder.type;
		this.show=builder.show;
		this.status=builder.status;
		this.priority=builder.priority;
	}
	
	public static class Builder{
		JID from;
		JID to;
		String type;
		//载荷元素
		String show;
		String status;
		int priority;
		
		public Builder(JID from,JID to,String type){
			this.from=from;
			this.to=to;
			this.type=type;
		}
		
		public Builder show(String show){
			this.show=show;
			return this;
		}
		
		public Builder status(String status){
			this.status=status;
			return this;
		}
		
		public Builder priority(int priority){
			this.priority=priority;
			return this;
		}
		
		public PresenceNode build(){
			return new PresenceNode(this);
		}
	}
	
	@Override
	public String toString(){
		StringBuilder sb=new StringBuilder();
		if(from!=null){
			sb.append("from:"+from.toString()+"\n");
		}
		if(to!=null){
			sb.append("to:"+to.toString()+"\n");
		}
		sb.append("type:"+type+"\n");
		sb.append("show:"+show+"\n");
		sb.append("status:"+status+"\n");
		sb.append("priority:"+priority+"\n");
		
		return sb.toString();
	}

}
