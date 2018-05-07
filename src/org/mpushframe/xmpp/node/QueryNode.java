package org.mpushframe.xmpp.node;

import java.util.List;

/**
 * Iq原语中的query节点
 * @author Mr.Chen
 * date: 2018年4月29日 下午1:29:14
 */
public class QueryNode {
	/**
	 * 执行query操作的环境
	 * 对应query节点中的xmlns字段
	 * 如“jabber:iq:roster”即为对联系人花名册的操作
	 */
	String queryContext;
	/**
	 * 操作的参数或者结果信息
	 */
	List<ItemNode> items;
	
	public String getQueryContext() {
		return queryContext;
	}

	public void setQueryContext(String queryContext) {
		this.queryContext = queryContext;
	}

	public List<ItemNode> getItems() {
		return items;
	}

	public void setItems(List<ItemNode> items) {
		this.items = items;
	}

	public QueryNode(String queryContext){
		this.queryContext=queryContext;
	}
	
	public QueryNode(String queryContext,List<ItemNode> items){
		this.queryContext=queryContext;
		this.items=items;
	}
	
	public void addItems(List<ItemNode> items){
		this.items=items;
	}
	
	@Override
	public String toString(){
		return "queryContext:"+queryContext+"\n"+"itemsSize:"+items.size();
	}
	
	
}