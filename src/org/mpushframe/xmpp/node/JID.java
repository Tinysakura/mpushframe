package org.mpushframe.xmpp.node;

/**
 * 标识通讯中的唯一用户
 * node@domin@resource
 * @author Mr.Chen
 * date: 2018年4月29日 下午12:15:02
 */
public class JID {
	
	private String nodeIdentifier;
	private String dominIdentifier;
	private String resourceIdentifier;

	public JID(String dominIdentifier,String nodeIdentifier,String resourceIdentifier){
		this.setDominIdentifier(dominIdentifier);
		this.setNodeIdentifier(nodeIdentifier);
		this.setResourceIdentifier(resourceIdentifier);
	}

	public String getDominIdentifier() {
		return dominIdentifier;
	}

	public void setDominIdentifier(String dominIdentifier) {
		this.dominIdentifier = dominIdentifier;
	}

	public String getNodeIdentifier() {
		return nodeIdentifier;
	}

	public void setNodeIdentifier(String nodeIdentifier) {
		this.nodeIdentifier = nodeIdentifier;
	}

	public String getResourceIdentifier() {
		return resourceIdentifier;
	}

	public void setResourceIdentifier(String resourceIdentifier) {
		this.resourceIdentifier = resourceIdentifier;
	}
	
	@Override
	public String toString(){
		StringBuilder sb=new StringBuilder();
		if(nodeIdentifier!=null){
			sb.append(nodeIdentifier+"@");
		}
		sb.append(dominIdentifier);
		if(resourceIdentifier!=null){
			sb.append("/"+resourceIdentifier);
		}
		
		return sb.toString();
	}
	
	/**
	 * 判断自身与另一个JID是否匹配的方法
	 */
	public boolean equal(JID jid){
		boolean isEqual=true;
		
		if(jid.getDominIdentifier().equals(dominIdentifier)){
			isEqual=false;
		}
		
		if(jid.getNodeIdentifier()!=null){
			if(jid.getNodeIdentifier().equals(nodeIdentifier)){
				isEqual=false;
			}
		}
		
		if (jid.getResourceIdentifier()!=null) {
			if (jid.getResourceIdentifier().equals(resourceIdentifier)) {
				isEqual = false;
			}
		}
		
		return isEqual;
	}
	
}
