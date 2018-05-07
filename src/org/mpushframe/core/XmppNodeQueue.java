package org.mpushframe.core;

import java.util.concurrent.LinkedBlockingQueue;

import org.mpushframe.exception.IllegalDomainExcetion;
import org.mpushframe.exception.MessageFromBlackListException;
import org.mpushframe.xmpp.filter.AbstractXmppFilter;
import org.mpushframe.xmpp.listener.IqNodeReadListener;
import org.mpushframe.xmpp.listener.MessageNodeReadListener;
import org.mpushframe.xmpp.listener.PrecenseNodeReadListener;
import org.mpushframe.xmpp.node.MessageNode;
import org.mpushframe.xmpp.node.PresenceNode;
import org.mpushframe.xmpp.node.XmppNode;

/**
 * 一个维护单个用户对Xmpp协议收发的服务类
 * 维护两个队列，一个发送队列一个接收队列
 * 使用阻塞的LinkedBlockingQueue作为底层实现的数据结构
 * 不同用户对于消息队列的处理在不同子线程中完成
 * 不同的队列对相同类型的XmppNode使用不同的处理逻辑
 * 对于XmppNode的处理逻辑交由实现了XmppNodeReaderListener接口的代理类完成
 * 抛弃了状态模式使用更灵活的代理模式响应不同权限(状态)的用户
 * @author Mr.Chen
 * date: 2018年4月28日 下午9:20:12
 */
public class XmppNodeQueue implements Runnable,MessageNodeReadListener,
PrecenseNodeReadListener,IqNodeReadListener{
	/**
	 * 使用一个阻塞式的LinkedBlockingQueue作为实现生产消费者模型的底层数据结构
	 */
	private LinkedBlockingQueue<XmppNode> nodeQueue;
	/**
	 * 标识队列所属用户的id,与用户与服务端session的id保持一致
	 * id是由服务端生成的一串包含数字和小写字母的16位随机字符串
	 */
	private String id;
	/**
	 * 标识线程是否可以结束的标志
	 */
	private volatile boolean destory=false;
	private volatile int task=0;
	/**
	 * 过滤器
	 */
	private AbstractXmppFilter filterChain;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 分别完成对message,precense与iq原语具体操作的代理
	 */
	private MessageNodeReadListener messageHandler;
	private PrecenseNodeReadListener precenseHandler;
	private IqNodeReadListener iqHandler;
	
	public XmppNodeQueue(String id){
		this.id=id;
		nodeQueue=new LinkedBlockingQueue<XmppNode>();
	}
	
	public XmppNodeQueue(String id,MessageNodeReadListener messageHandler,
			PrecenseNodeReadListener precenseHandler,IqNodeReadListener iqHandler){
		this.id=id;
		this.messageHandler=messageHandler;
		this.precenseHandler=precenseHandler;
		this.iqHandler=iqHandler;
		nodeQueue=new LinkedBlockingQueue<XmppNode>();
	}
	
	public void setMessageHandler(MessageNodeReadListener messageHandler) {
		this.messageHandler = messageHandler;
	}

	public void setPrecenseHandler(PrecenseNodeReadListener precenseHandler) {
		this.precenseHandler = precenseHandler;
	}

	public void setIqHandler(IqNodeReadListener iqHandler) {
		this.iqHandler = iqHandler;
	}
	
	/**
	 * 按消息进入队列的顺序取出消息进行相关处理
	 * 若队列为空则阻塞等待新元素入队唤醒队列
	 * 使用filter过滤不需要处理的接口
	 */
	public void run() {
		while(true){
			XmppNode handleNode=null;
			try {
				synchronized (nodeQueue) {
					if(task==0 && destory){
						System.out.println("队列中已没有任务且可以销毁");
						break;
					}
				}
				handleNode=nodeQueue.take();
				
				if(handleNode!=null){
					System.out.println("处理任务");
					try {
						filterChain.Filter(handleNode);
						readXmpp(handleNode);
					} catch (IllegalDomainExcetion e) {
						//做非法域名的相关异常处理
						e.printStackTrace();
					} catch (MessageFromBlackListException e) {
						//做接收到黑名单发来的消息的相关异常处理
						e.printStackTrace();
					}
					
					--task;
				}
			} catch (InterruptedException e) {
				//通过抛出异常来结束线程
				//throw new RuntimeException();
				//这里应该在检查任务队列为空后安全的退出线程
				System.out.println("收到中断请求开始检查队列中是否有任务");
				Thread.currentThread().interrupt();
			}
		}
	}
	
	/**
	 * 向队列中添加新消息的方法
	 */
	public void put(XmppNode xmppNode){
		try {
			nodeQueue.put(xmppNode);
			task++;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 判断队列是否为空
	 */
	public boolean isEmpty(){
		return nodeQueue.isEmpty();
	}
	
	/**
	 * 改变线程状态使线程可以结束
	 */
	public void destory(){
		this.destory=true;
	}

	@Override
	public void readIq(XmppNode xmppNode) {
		iqHandler.readIq(xmppNode);
	}

	@Override
	public void readMessage(XmppNode xmppNode) {
		messageHandler.readMessage(xmppNode);
	}

	@Override
	public void readPrecense(XmppNode xmppNode) {
		precenseHandler.readPrecense(xmppNode);
	}

	@Override
	public void readXmpp(XmppNode xmppNode) {
		if(xmppNode instanceof MessageNode){
			readMessage(xmppNode);
		}else if(xmppNode instanceof PresenceNode){
			readPrecense(xmppNode);
		}else{
			readIq(xmppNode);
		}
	}

	public AbstractXmppFilter getFilterChain() {
		return filterChain;
	}

	public void setFilterChain(AbstractXmppFilter filterChain) {
		this.filterChain = filterChain;
	}

}
