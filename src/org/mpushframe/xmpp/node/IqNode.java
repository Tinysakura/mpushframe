package org.mpushframe.xmpp.node;

/**
 * Xmpp协议的Iq(information query)原语
 * 使用builder模式构建
 * @author Mr.Chen
 * date: 2018年4月29日 下午1:18:53
 */
public class IqNode extends XmppNode{
	/**
	 * Get :获取当前域值。类似于http get方法。
	 * Set :设置或替换get查询的值。类似于http put方法。
	 * Result :说明成功的响应了先前的查询。类似于http状态码200。
	 * Error: 查询和响应中出现的错误。
	 */
	String type;
	/**
	 * 用来区分响应对应的是哪一个请求的字段
	 * 请求与对应的响应拥有相同的id
	 * id是由系统随机生成的一串由字母与小写数字组成的随机序列如“rr82a1z7”
	 */
	String id;
	/**
	 * 可扩展的请求服务
	 */
	QueryNode query;
	
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public QueryNode getQuery() {
		return query;
	}

	public void setQuery(QueryNode query) {
		this.query = query;
	}

	private IqNode(Builder builder){
		super(builder.from, builder.to);
		this.type=builder.type;
		this.id=builder.id;
		this.query=builder.query;
	}
	
	public static class Builder{
		JID from;
		JID to;
		String type;
		String id;
		QueryNode query;
		
		public Builder(JID from,JID to,String type,String id){
			this.from=from;
			this.to=to;
			this.type=type;
			this.id=id;
		}
		
		public Builder query(QueryNode query){
			this.query=query;
			return this;
		}
		
		public IqNode build(){
			return new IqNode(this);
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
		sb.append("id:"+id+"\n");
		if(query!=null){
			sb.append("query:"+query.toString());
		}
		
		return sb.toString();
	}

}
