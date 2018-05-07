package org.mpushframe.xmpp.node;

/**
 * Xmpp中的message原语
 * 考虑到原语的不固定性与扩展性使用Builder模式创建对象
 * @author Mr.Chen
 * date: 2018年4月29日 下午12:28:04
 */
public class MessageNode extends XmppNode{
	/**
	 * normal:单个的消息，对应的回应可能会或者可能不会很快到来。
	 * chat:在两个实体间店实时对话中交换
	 * groupchat:多用户聊天室中交换
	 * headline:发送警告和通告，并不期望有回应
	 * error：对先前发送消息发生错误，实体检测这个问题将返回一个类型error的消息。
	 */
	String type;
	//载荷元素
	String body;
	String subject;
	
	/**
	 * 由于构造方法不接收参数而是使用Builder代理来接收因此可见性设置为private
	 */
	private MessageNode(Builder builder){
		super(builder.from, builder.to);
		this.type=builder.type;
		this.body=builder.body;
		this.subject=builder.subject;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public static class Builder{
		private JID from;
		private JID to;
		private String type;
		
		//载荷元素
		private String body;
		private String subject;
		
		/**
		 * from to type是一个MessageNode的必选参数
		 * @param from
		 * @param to
		 * @param type
		 */
		public Builder(JID from,JID to,String type){
			this.from=from;
			this.to=to;
			this.type=type;
		}
		
		public Builder body(String body){
			this.body=body;
			return this;
		}
		
		public Builder subject(String subject){
			this.subject=subject;
			return this;
		}
		
		public MessageNode build(){
			return new MessageNode(this);
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
		sb.append("body:"+body+"\n");
		sb.append("subject:"+subject+"\n");
		
		return sb.toString();
	}
}
